package ee.ttu.thesis.controller;

import ee.ttu.thesis.domain.Report;
import ee.ttu.thesis.domain.Transaction;
import ee.ttu.thesis.service.ReportService;
import ee.ttu.thesis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final UserService userService;

    @PostMapping("/get")
    @ResponseBody
    public Report getReportByUser(@RequestHeader(name = "Authorization") String token, @RequestBody Report r) {
        return reportService.createReportByUser(userService.getUserIdFromToken(token), r);
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public Report getReportByUser(@PathVariable("id") Long id) {
        return reportService.getReportById(id);
    }

    @PostMapping("/generate/anon")
    public Report getReportByAnon(@RequestBody List<Transaction> transactions) {
        return reportService.createReportForAnon(transactions);
    }
}
