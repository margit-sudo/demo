package ee.ttu.thesis.service;

import ee.ttu.thesis.domain.TransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {
    String csvFile = "C:\\Users\\birgi\\Documents\\Äriinfotehnoloogia\\lõputöö/statement-2019.csv";

    public TransactionDto getTransactionDto(String data) {
        return null;
        //return TransactionDto.builder().data("Hello world! My data is " + data).build();
    }

    public void parseCsvFileToTransaction() throws IOException {
        ArrayList<TransactionDto> transactionList = new ArrayList<>();
        String line = "";

            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                String ignorefirstLine = br.readLine();

                while ((line = br.readLine()) != null) {
                    line = line.replace("\"", "");
                    String[] currentLine = line.split(";");

                    transactionList.add(TransactionDto.builder().
                                    AccountNumber(currentLine[0]).
                                    DocumentNumber(currentLine[1]).
                                    Date(LocalDate.parse(currentLine[2], DateTimeFormatter.ofPattern("dd-MM-yyyy"))).
                                    BeneficiaryOrPayerAccount(currentLine[3]).
                                    BeneficiaryOrPayerName(currentLine[4]).
                                    DebitOrCredit(currentLine[7]).
                                    Amount(new BigDecimal(currentLine[8].replace(",", "."))).
                                    Details(currentLine[10]).
                                    Currency(currentLine[13]).build());
                }
            } catch (IOException e) {
                throw new IOException("Cannot parse the file!");
            }
        }

    }
