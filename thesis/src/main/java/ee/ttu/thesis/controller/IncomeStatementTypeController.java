package ee.ttu.thesis.controller;

import ee.ttu.thesis.domain.IncomeStatementType;
import ee.ttu.thesis.service.IncomeStatementTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/incomeStatements")
@RequiredArgsConstructor
public class IncomeStatementTypeController {

    private final IncomeStatementTypeService incomeStatementTypeService;

    @GetMapping("all")
    public List<IncomeStatementType> getIncomeStatementTypes(){
        return incomeStatementTypeService.getIncomeStatementTypes();
    }
}
