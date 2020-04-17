package ee.ttu.thesis.parser;

import ee.ttu.thesis.domain.Transaction;
import ee.ttu.thesis.domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CsvFileParser {
     List<Transaction> parseCsvFile(MultipartFile file, User user) throws IOException;
}
