package com.chisw.dynamicFunctions.service;


import com.chisw.dynamicFunctions.entity.Calculation;
import com.chisw.dynamicFunctions.entity.Function;
import com.chisw.dynamicFunctions.entity.function.Container;
import com.chisw.dynamicFunctions.entity.function.Cos;
import com.chisw.dynamicFunctions.entity.function.Sin;
import com.chisw.dynamicFunctions.persistence.jpa.repository.CalculationRepository;
import com.chisw.dynamicFunctions.persistence.jpa.repository.FunctionRepository;
import com.chisw.dynamicFunctions.service.impl.DynamicFunctionsServiceImpl;
import com.chisw.dynamicFunctions.service.interfaces.DynamicFunctionsService;
import com.chisw.dynamicFunctions.web.dto.ConfigDTO;
import com.chisw.dynamicFunctions.web.dto.ContainerDTO;
import com.chisw.dynamicFunctions.web.dto.FunctionDTO;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DynamicFunctionsServiceImplUnitTest {

    private FunctionRepository functionRepository;
    private CalculationRepository calculationRepository;
    private DynamicFunctionsService dynamicFunctionsService;

    @Before
    public void init(){
        functionRepository = mock(FunctionRepository.class);
        calculationRepository = mock(CalculationRepository.class);
        dynamicFunctionsService = new DynamicFunctionsServiceImpl(functionRepository,calculationRepository);
    }

    @Test
    public void initConfig_success(){

        String name = "container1";
        Function container = new Container(name);
        ContainerDTO containerDTO = new ContainerDTO();
        containerDTO.setName(name);
        ConfigDTO configDTO = new ConfigDTO();
        configDTO.getContainers().add(containerDTO);
        when(functionRepository.save(container)).thenReturn(container);

        boolean result = dynamicFunctionsService.initConfig(configDTO);
        assertTrue(result);

    }

    @Test
    public void initConfig_fail(){

        String name = "container1";
        Function container = new Container(name);
        when(functionRepository.save(container)).thenReturn(container);

        boolean result = dynamicFunctionsService.initConfig(null);
        assertFalse(result);

    }

    @Test
    public void getAvailableFunctions_success() throws Exception{
        boolean available = true;
        Long containerId = null;
        List<Function> functions = getFunctions();
        when(functionRepository.findAllByAvailableAndContainerId(available, containerId)).thenReturn(functions);

        List<FunctionDTO> result = dynamicFunctionsService.getAvailableFunctions();
        assertNotNull(result);
        assertEquals(result.size(), 2);
        assertEquals(result.get(0).getName(), "Cos");
        assertEquals(result.get(1).getName(), "Sin");
    }

    private List<Function> getFunctions()  {
        Float a = 0F;
        Float b = 0F;
        Cos cos = new Cos("Cos");
        Sin sin = new Sin("Sin");
        cos.setA(a);
        cos.setB(b);
        sin.setA(a);
        sin.setB(b);
        List<Function> functions = new ArrayList<>();
        functions.add(cos);
        functions.add(sin);
        return functions;
    }

    @Test
    public void getAvailableFunctions_absent() throws Exception{

        boolean available = true;
        Long containerId = null;
        List<Function> functions = new ArrayList<>();
        when(functionRepository.findAllByAvailableAndContainerId(available, containerId)).thenReturn(functions);

        List<FunctionDTO> result = dynamicFunctionsService.getAvailableFunctions();
        assertNotNull(result);
        assertEquals(result.size(), 0);
    }

    @Test
    public void connectFunction_success(){

        String name = "Cos";
        Cos cos = new Cos(name);
        boolean switchedOn = false;
        Long containerId = null;
        when(functionRepository.findFirstByNameAndSwitchedOnAndContainerId(name,switchedOn,containerId)).thenReturn(cos);

        boolean result = dynamicFunctionsService.connectFunction(name,0F,0F);
        assertTrue(result);
    }

    @Test
    public void connectFunction_failIfAlreadySwitchOn(){

        String name = "Cos";
        Cos cos = new Cos(name);
        boolean switchedOn = true;
        Long containerId = null;
        when(functionRepository.findFirstByNameAndSwitchedOnAndContainerId(name,switchedOn,containerId)).thenReturn(cos);

        boolean result = dynamicFunctionsService.connectFunction(name,0F,0F);
        assertFalse(result);
    }

    @Test
    public void connectFunction_failIfNotFound(){

        String name = "Cos";
        boolean switchedOn = false;
        Long containerId = null;
        when(functionRepository.findFirstByNameAndSwitchedOnAndContainerId(name,switchedOn,containerId)).thenReturn(null);

        boolean result = dynamicFunctionsService.connectFunction(name,0F,0F);
        assertFalse(result);
    }

    @Test
    public void disconnectFunction_success(){

        String name = "Sin";
        Sin sin = new Sin(name);
        boolean switchedOn = true;
        Long containerId = null;
        when(functionRepository.findFirstByNameAndSwitchedOnAndContainerId(name,switchedOn,containerId)).thenReturn(sin);

        boolean result = dynamicFunctionsService.disconnectFunction(name);
        assertTrue(result);
    }

    @Test
    public void disconnectFunction_failIfAlreadySwitchOff(){

        String name = "Sin";
        Sin sin = new Sin(name);
        boolean switchedOn = false;
        Long containerId = null;
        when(functionRepository.findFirstByNameAndSwitchedOnAndContainerId(name,switchedOn,containerId)).thenReturn(sin);

        boolean result = dynamicFunctionsService.disconnectFunction(name);
        assertFalse(result);
    }

    @Test
    public void disconnectFunction_failIfNotFound(){

        String name = "Sin";
        boolean switchedOn = true;
        Long containerId = null;
        when(functionRepository.findFirstByNameAndSwitchedOnAndContainerId(name,switchedOn,containerId)).thenReturn(null);

        boolean result = dynamicFunctionsService.disconnectFunction(name);
        assertFalse(result);
    }

    @Test
    public void getConfig_success(){

        boolean avialable = true;
        boolean switchedOn = true;
        Long containerId = null;
        List<Function> functions = getFunctions();
        when(functionRepository.findAllByAvailableAndSwitchedOnAndContainerId(avialable,switchedOn,containerId))
                .thenReturn(functions);

        List<FunctionDTO> result = dynamicFunctionsService.getConfig();
        assertNotNull(result);
        assertEquals(result.size(), 2);
        assertEquals(result.get(0).getName(),"Cos");
        assertEquals(result.get(1).getName(),"Sin");
    }

    @Test
    public void getConfig_notFound(){

        boolean avialable = true;
        boolean switchedOn = true;
        Long containerId = null;
        List<Function> functions = new ArrayList<>();
        when(functionRepository.findAllByAvailableAndSwitchedOnAndContainerId(avialable,switchedOn,containerId))
                .thenReturn(functions);

        List<FunctionDTO> result = dynamicFunctionsService.getConfig();
        assertNotNull(result);
        assertEquals(result.size(), 0);
    }

    @Test
    public void evaluateFunctions_success(){

        String userName = "Ivan";
        float res = 0f;
        float x = 1f;
        boolean avialable = true;
        boolean switchedOn = true;
        Long containerId = null;
        List<Function> functions = getFunctions();
        List<Calculation> calculationsCos = new ArrayList<>();
        List<Calculation> calculationsSin = new ArrayList<>();
        Calculation calculCos = new Calculation(functions.get(0),userName,res,x);
        Calculation calculSin = new Calculation(functions.get(1),userName,res,x);
        calculationsCos.add(calculCos);
        calculationsSin.add(calculSin);
        when(functionRepository.findAllByAvailableAndSwitchedOnAndContainerId(avialable,switchedOn,containerId))
                .thenReturn(functions);
        when(calculationRepository.save(calculationsCos)).thenReturn(calculationsCos);
        when(calculationRepository.save(calculationsSin)).thenReturn(calculationsSin);

        boolean result = dynamicFunctionsService.evaluateFunctions(userName,x);
        assertTrue(result);
    }

    @Test
    public void evaluateFunctions_fail(){

        String userName = "Ivan";
        float x = 1f;
        boolean avialable = true;
        boolean switchedOn = true;
        Long containerId = null;
        List<Function> functions = getFunctions();
        List<Calculation> calculationsCos = new ArrayList<>();
        List<Calculation> calculationsSin = new ArrayList<>();
        when(functionRepository.findAllByAvailableAndSwitchedOnAndContainerId(avialable,switchedOn,containerId))
                .thenReturn(functions);
        when(calculationRepository.save(calculationsCos)).thenReturn(calculationsCos);
        when(calculationRepository.save(calculationsSin)).thenReturn(calculationsSin);

        boolean result = dynamicFunctionsService.evaluateFunctions(userName,x);
        assertFalse(result);
    }
    @Test
    public void getDisconnected(){
        boolean avialable = true;
        boolean switchedOn = false;
        List<Function> functions = getFunctions();
        when(functionRepository.findAllByAvailableAndSwitchedOn(avialable, switchedOn)).thenReturn(functions);
        when(calculationRepository.findOneByDisconnect("Cos"))
                .thenReturn(new Calculation(functions.get(0),"Ivan",0f,0f));

        List<FunctionDTO> result = dynamicFunctionsService.getDisconnected();
        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getName(), "Cos");
    }
}
