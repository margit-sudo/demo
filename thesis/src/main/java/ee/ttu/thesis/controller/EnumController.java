package ee.ttu.thesis.controller;

import ee.ttu.thesis.domain.Bank;
import ee.ttu.thesis.domain.FileType;
import ee.ttu.thesis.domain.IncomeStatementType;
import ee.ttu.thesis.service.EnumService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class EnumController {

    private final EnumService enumService;

    @GetMapping("incomeStatement/all")
    public List<IncomeStatementType> getIncomeStatementTypes(){
        return enumService.getIncomeStatementTypes();
    }

    @GetMapping("bank/all")
    public List<Bank> getBanks(){
        return enumService.getBanks();
    }

    @GetMapping("filetype/all")
    public List<FileType> getFileTypes(){
        return enumService.getFileTypes();
    }
}
