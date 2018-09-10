package com.chisw.dynamicFunctions.service;

import com.chisw.dynamicFunctions.entity.Calculation;
import com.chisw.dynamicFunctions.entity.function.Cos;
import com.chisw.dynamicFunctions.persistence.jpa.repository.CalculationRepository;
import com.chisw.dynamicFunctions.service.impl.CalculationServiceImpl;
import com.chisw.dynamicFunctions.service.interfaces.CalculationService;
import com.chisw.dynamicFunctions.web.dto.CalculationDTO;
import com.chisw.dynamicFunctions.web.dto.UsageBodyDTO;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculationServiceImplUnitTest {

    private CalculationRepository calculationRepository;
    private CalculationService calculationService;
    private String userName = "Ivan";
    private String functionName = "Cos";

    @Before
    public void init(){
        calculationRepository = mock(CalculationRepository.class);
        calculationService = new CalculationServiceImpl(calculationRepository);
    }

    @Test
    public void getUsage_success(){

        List<Calculation> calculations = getCalculations();
        when(calculationRepository.findAllbyUsage(userName, functionName, 0F, 0F )).thenReturn(calculations);

        List<CalculationDTO> result = calculationService.getUsage(new UsageBodyDTO(userName, functionName, 0F, 0F));
        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getFunctionName(), functionName);
        assertEquals(result.get(0).getUserName(), userName);
    }

    private List<Calculation> getCalculations() {
        Cos cos = new Cos(functionName);
        Calculation calc1 = new Calculation(cos, userName, 0F, 0F);
        List<Calculation> calculations = new ArrayList<>();
        calculations.add(calc1);
        return calculations;
    }

    @Test
    public void getUsage_notFound(){
        List<Calculation> calculations = new ArrayList<>();
        when(calculationRepository.findAllbyUsage(userName, functionName, 0F, 0F )).thenReturn(calculations);

        List<CalculationDTO> result = calculationService.getUsage(new UsageBodyDTO(userName, functionName, 0F, 0F));
        assertNotNull(result);
        assertEquals(result.size(), 0);
    }

    @Test
    public void getMaxUser_success(){
        List<Calculation> calculations = getCalculations();
        when(calculationRepository.findFunctionMaxUser(functionName)).thenReturn(calculations.get(0));

        String result = calculationService.getMaxUser(new UsageBodyDTO(userName, functionName, 0F, 0F));
        assertNotNull(result);
        assertEquals(result, userName);
    }

    @Test
    public void getMaxUser_fail(){
        when(calculationRepository.findFunctionMaxUser(functionName)).thenReturn(null);

        String result = calculationService.getMaxUser(new UsageBodyDTO(userName, functionName, 0F, 0F));
        assertNull(result);
    }
}
