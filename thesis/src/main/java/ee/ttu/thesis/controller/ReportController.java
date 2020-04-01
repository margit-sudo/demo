package ee.ttu.thesis.controller;

import ee.ttu.thesis.domain.Report;
import ee.ttu.thesis.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
