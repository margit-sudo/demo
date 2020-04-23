package ee.ttu.thesis.service;

import ee.ttu.thesis.domain.Bank;
import ee.ttu.thesis.domain.IncomeStatementType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EnumService {

    public List<IncomeStatementType> getIncomeStatementTypes(){
        return new ArrayList<>(EnumSet.allOf(IncomeStatementType.class));
    }

    public List<Bank> getBanks(){
        return new ArrayList<>(EnumSet.allOf(Bank.class));
    }
}
