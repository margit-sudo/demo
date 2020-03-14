package ee.ttu.thesis;

import ee.ttu.thesis.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.text.ParseException;

@SpringBootTest
class ThesisApplicationTests {

	@Test
	void contextLoads() throws IOException {
		TransactionService s = new TransactionService();
		s.parseCsvFileToTransaction();
	}
}
