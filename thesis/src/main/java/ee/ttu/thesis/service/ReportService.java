package ee.ttu.thesis.service;

import ee.ttu.thesis.domain.Entry;
import ee.ttu.thesis.domain.IncomeStatementType;
import ee.ttu.thesis.domain.Report;
import ee.ttu.thesis.domain.ReportRow;
import ee.ttu.thesis.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository repo;
    private final TransactionService transactionService;

    public Report createReportByUser(Long userId){
        Report r = new Report();
        r.setDateMade(LocalDate.now());

        List<ReportRow> rows = createReportRows();
        rows = removeZeroSums(rows);
        r.setRows(rows);

        //repo.save(r);

        return r;
    }

    private List<ReportRow> removeZeroSums(List<ReportRow> rows) {
        List<ReportRow> rowsWithoutZeroSum = new ArrayList<>();
        for (ReportRow row : rows) {
            if(row.getSum() != BigDecimal.ZERO) rowsWithoutZeroSum.add(row);
        }
        return rowsWithoutZeroSum;
    }

    private List<ReportRow>  createReportRows() {
        HashMap<IncomeStatementType, Entry> groupedList = transactionService.getTransactionsGroupedByIncomeStatementType();
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

}
