package ee.ttu.thesis.parser;

import ee.ttu.thesis.domain.Transaction;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ICsvFileParser {
     List<Transaction> parseCsvFile(MultipartFile file) throws IOException;
}
