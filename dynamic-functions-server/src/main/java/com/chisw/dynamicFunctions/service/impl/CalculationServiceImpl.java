package com.chisw.dynamicFunctions.service.impl;

import com.chisw.dynamicFunctions.entity.Calculation;
import com.chisw.dynamicFunctions.persistence.jpa.repository.CalculationRepository;
import com.chisw.dynamicFunctions.service.interfaces.CalculationService;
import com.chisw.dynamicFunctions.util.FunctionWebResourceUtil;
import com.chisw.dynamicFunctions.web.dto.CalculationDTO;
import com.chisw.dynamicFunctions.web.dto.UsageBodyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *{@inheritDoc}
 */
@Service
@Transactional
public class CalculationServiceImpl implements CalculationService {

    private final CalculationRepository calculationRepository;

    @Autowired
    public CalculationServiceImpl(CalculationRepository calculationRepository) {
        this.calculationRepository = calculationRepository;
    }

    @Override
    public List<CalculationDTO> getUsage(UsageBodyDTO dto) {
        List<CalculationDTO> calculations = new ArrayList<>();
        List<Calculation> calc = calculationRepository.findAllbyUsage(dto.getUser(), dto.getFunc(), dto.getX1(), dto.getX2());
        if(calc != null) {
            calculations.addAll(calc.stream().map(FunctionWebResourceUtil::toCalculationDTO).collect(Collectors.toList()));
        }
        return calculations;
    }

    @Override
    public String getMaxUser(UsageBodyDTO dto) {
       Calculation calc = calculationRepository.findFunctionMaxUser(dto.getFunc());
       if(calc != null) return calc.getUserName();
       return null;
    }
}
