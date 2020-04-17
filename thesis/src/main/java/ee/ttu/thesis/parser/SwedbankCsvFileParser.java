package ee.ttu.thesis.parser;

import ee.ttu.thesis.domain.IncomeStatementType;
import ee.ttu.thesis.domain.Transaction;
import ee.ttu.thesis.domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SwedbankCsvFileParser implements CsvFileParser {

    @Override
    public List<Transaction>  parseCsvFile(MultipartFile file, User user) throws IOException {
        String line;
        List<Transaction> transactionList = new ArrayList<>();

        InputStream is = file.getInputStream();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String ignorefirstLine = br.readLine();

            while ((line = br.readLine()) != null) {
                line = line.replace("\"", "");
                String[] currentLine = line.split(";");

                transactionList.add(Transaction.builder().
                        accountNumber(currentLine[0]).
                        date(LocalDate.parse(currentLine[2], DateTimeFormatter.ofPattern("dd-MM-yyyy"))).
                        //dateString(currentLine[2]).
                                beneficiaryOrPayerAccount(currentLine[3]).
                                beneficiaryOrPayerName(currentLine[4]).
                                debitOrCredit(currentLine[7]).
                                amount(new BigDecimal(currentLine[8].replace(",", "."))).
                                details(currentLine[11]).
                                currency(currentLine[13]).
                                incomeStatementType(IncomeStatementType.MÄÄRAMATA).
                                user(user).build());
            }
        } catch (IOException e) {
            throw new IOException("Cannot parse the file!");
        }
        return transactionList;
    }
}
