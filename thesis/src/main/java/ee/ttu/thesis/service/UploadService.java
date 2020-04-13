package ee.ttu.thesis.service;

import ee.ttu.thesis.domain.File;
import ee.ttu.thesis.domain.Transaction;
import ee.ttu.thesis.parser.SwedbankCsvFileParser;
import ee.ttu.thesis.parser.XmlParser;
import ee.ttu.thesis.repository.UploadRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UploadService {
    @Autowired
    private UploadRepository repo;
    @Autowired
    private TransactionService transactionService;

    public List<File> getFilesList() {
        return repo.findAll();
    }

    public void saveFile(File file) {
        repo.save(file);
    }

    public void parseAndSaveMultipartFile(MultipartFile file) throws IOException {
        List<Transaction> transactionList = parseTransactions(file);
        transactionList = transactionService.saveAll(transactionList);
        saveFile(File.builder().name(file.getName()).uploadDate(LocalDate.now()).transactions(transactionList).build());
    }

    private List<Transaction> parseTransactions(MultipartFile file) throws IOException {
        List<Transaction> list = new ArrayList<>();
        String fileName = file.getOriginalFilename();

        if(FilenameUtils.getExtension(fileName).equals("xml")){
            XmlParser p = new XmlParser();
            list = p.parseFile();
        }
        else if(FilenameUtils.getExtension(fileName).equals("csv")){
            SwedbankCsvFileParser parser = new SwedbankCsvFileParser();
            list =  parser.parseCsvFile(file);
        }
        return list;
    }
}
