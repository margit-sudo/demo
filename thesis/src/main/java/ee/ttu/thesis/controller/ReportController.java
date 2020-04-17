package ee.ttu.thesis.controller;

import ee.ttu.thesis.domain.Report;
import ee.ttu.thesis.service.ReportService;
import ee.ttu.thesis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final UserService userService;

 /*   @GetMapping("/new")
    @ResponseBody
    public Report getReport() {
        return reportService.createReportByUser();
    }*/

    @GetMapping("/get")
    @ResponseBody
    public Report getReportByUser(@RequestHeader(name = "Authorization") String token) {
        return reportService.createReportByUser(userService.getUserIdFromToken(token));
    }
}
