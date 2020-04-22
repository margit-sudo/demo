package ee.ttu.thesis.service;

import ee.ttu.thesis.domain.*;
import ee.ttu.thesis.repository.ReportRepository;
import ee.ttu.thesis.repository.RowRepository;
import ee.ttu.thesis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository repo;
    private final TransactionService transactionService;
    private final UserRepository userRepo;
    private final RowRepository rowRepo;
    private Clock clock = Clock.system(ZoneId.of("Europe/Helsinki"));

    public Report createReportByUser(Long userId, Report reportFromFront){
        LocalDate startDate = reportFromFront.getStartDate();
        LocalDate endDate = reportFromFront.getEndDate();

        List<ReportRow> rows = createReportRows(userId, startDate, endDate, null);
        rows = removeZeroSums(rows);

        Report r = Report.builder().
                user(userRepo.findById(userId).orElse(null)).
                startDate(startDate).
                endDate(endDate).
                dateMade(LocalDate.now(clock)).
                rows(rows).build();

        rowRepo.saveAll(rows);
        repo.save(r);
        return r;
    }

    private List<ReportRow> removeZeroSums(List<ReportRow> rows) {
        List<ReportRow> rowsWithoutZeroSum = new ArrayList<>();
        for (ReportRow row : rows) {
            if(row.getSum() != BigDecimal.ZERO) rowsWithoutZeroSum.add(row);
        }
        return rowsWithoutZeroSum;
    }

    private List<ReportRow> createReportRows(Long userId, LocalDate startDate, LocalDate endDate, List<Transaction> transactionsFromAnon) {
        HashMap<IncomeStatementType, Entry> groupedList = transactionService.getGroupedTransactions(userId, startDate, endDate, transactionsFromAnon);
        List<ReportRow> rows = new ArrayList<>();

        for (IncomeStatementType incomeStatementType : groupedList.keySet()) {
            ReportRow r = new ReportRow();
            r.setIncomeStatementType(incomeStatementType);
            r.setTransactions(groupedList.get(incomeStatementType).getTransactions());
            r.setSum(groupedList.get(incomeStatementType).getSum());
            rows.add(r);
        }
        return rows;
    }

    public Report getReportById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Report createReportForAnon(List<Transaction> transactions) {
        List<ReportRow> rows = createReportRows(null, null, null, transactions);
        rows = removeZeroSums(rows);

        Report r = Report.builder().
                dateMade(LocalDate.now()).
                rows(rows).build();

        return r;
    }

    public List<Report> getReportsByUserId(Long userIdFromToken) {
        return repo.findByUserId(userIdFromToken);
    }
}
