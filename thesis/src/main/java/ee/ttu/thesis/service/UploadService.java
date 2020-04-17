package ee.ttu.thesis.service;

import ee.ttu.thesis.domain.File;
import ee.ttu.thesis.domain.FileType;
import ee.ttu.thesis.domain.Transaction;
import ee.ttu.thesis.domain.User;
import ee.ttu.thesis.parser.SwedbankCsvFileParser;
import ee.ttu.thesis.parser.XmlParser;
import ee.ttu.thesis.repository.UploadRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UploadService {

    private final UploadRepository repo;
    private final TransactionService transactionService;

    public List<File> getFilesList() {
        return repo.findAll();
    }

    public List<File> getFilesListByUserId(Long userId) {
        return repo.findByUserId(userId);
    }

    public void saveFile(File file) {
        repo.save(file);
    }

    public void parseAndSaveMultipartFile(MultipartFile file, User user) throws IOException {
        List<Transaction> transactionList = parseTransactions(file, user);
        transactionList = transactionService.saveAll(transactionList);
        saveFile(File.builder().name(file.getName()).uploadDate(LocalDate.now()).transactions(transactionList).build());
    }

    private List<Transaction> parseTransactions(MultipartFile file, User user) throws IOException {
        List<Transaction> list = new ArrayList<>();
        String fileName = file.getOriginalFilename();

        if(FilenameUtils.getExtension(fileName).equals(FileType.XML)){
            XmlParser p = new XmlParser();
            list = p.parseFile(file, user);
        }
        else if(FilenameUtils.getExtension(fileName).equals(FileType.CSV)){
            SwedbankCsvFileParser parser = new SwedbankCsvFileParser();
            list =  parser.parseCsvFile(file, user);
        }
        return list;
    }
}
