package ee.ttu.thesis.controller;

import ee.ttu.thesis.domain.Report;
import ee.ttu.thesis.domain.Rule;
import ee.ttu.thesis.service.ReportService;
import ee.ttu.thesis.service.RuleService;
import ee.ttu.thesis.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/new")
    @ResponseBody
    public Report getReport() {
        return reportService.createReport();
    }
}
