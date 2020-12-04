package ee.ttu.thesis;

import ee.ttu.thesis.domain.IncomeStatementType;
import ee.ttu.thesis.domain.ReportRow;
import ee.ttu.thesis.service.ReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @InjectMocks
    private ReportService service;

    @Test
    public void testRemoveZeroSums() {
        List<ReportRow> testRows = service.removeZeroSums(getRows());
        assertThat(testRows.size()).isEqualTo(1);
    }

    @Test
    public void testRemoveZeroSumsFails() {
        List<ReportRow> testRows = service.removeZeroSums(getRows());
        assertThat(testRows.size()).isEqualTo(2);
    }

    private List<ReportRow> getRows() {
        ReportRow r1 = new ReportRow(1L, IncomeStatementType.INTRESSIKULUD, BigDecimal.valueOf(100), null);
        ReportRow r2 = new ReportRow(2L, IncomeStatementType.EMAETTEVÕTJA_AKTSIONÄRIDE_VÕI_OSANIKE_OSA_KASUMIST, BigDecimal.ZERO, null);
        //return List.of(r1, r2);
        return Arrays.asList(r1, r2);
    }
}
