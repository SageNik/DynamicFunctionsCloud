package com.chisw.dynamicFunctions.service.impl;

import com.chisw.dynamicFunctions.entity.Calculation;
import com.chisw.dynamicFunctions.entity.Function;
import com.chisw.dynamicFunctions.exception.FunctionNotFoundException;
import com.chisw.dynamicFunctions.entity.function.Container;
import com.chisw.dynamicFunctions.entity.function.PrimitiveFunction;
import com.chisw.dynamicFunctions.persistence.jpa.repository.CalculationRepository;
import com.chisw.dynamicFunctions.persistence.jpa.repository.FunctionRepository;
import com.chisw.dynamicFunctions.service.interfaces.DynamicFunctionsService;
import com.chisw.dynamicFunctions.util.FunctionWebResourceUtil;
import com.chisw.dynamicFunctions.web.dto.ConfigDTO;
import com.chisw.dynamicFunctions.web.dto.ContainerDTO;
import com.chisw.dynamicFunctions.web.dto.FunctionDTO;
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
public class DynamicFunctionsServiceImpl implements DynamicFunctionsService {

    private final FunctionRepository functionRepository;
    private final CalculationRepository calculationRepository;

    @Autowired
    public DynamicFunctionsServiceImpl(FunctionRepository functionRepository, CalculationRepository calculationRepository) {
        this.functionRepository = functionRepository;
        this.calculationRepository = calculationRepository;
    }

    @Override
    public boolean initConfig(ConfigDTO configDTO) {

        if(configDTO != null){
            createPrimitiveFunctions(configDTO.getPrimitives(), null);
            for(ContainerDTO containerDTO : configDTO.getContainers()){
                Container container = new Container(containerDTO.getName());
                functionRepository.save(container);
                createPrimitiveFunctions(containerDTO.getFunctions(), container);
            }
            return true;
        }
        return false;
    }

    @Override
    public List<FunctionDTO> getAvailableFunctions() {
        List<FunctionDTO> functionDTOList = new ArrayList<>();
            List<Function> functions = functionRepository.findAllByAvailableAndContainerId(true, null);
            functionDTOList.addAll(functions.stream().map(FunctionWebResourceUtil::toFunctionDTO).collect(Collectors.toList()));
        return functionDTOList;
    }

    @Override
    public boolean connectFunction(String functionName, Float a, Float b) {
         Function function = functionRepository.findFirstByNameAndSwitchedOnAndContainerId(functionName, false, null);
        if(function == null) return false;
        else {
            function.switchOn(a,b);
            return true;
        }
    }

    @Override
    public boolean disconnectFunction(String functionName) {
        Function function = functionRepository.findFirstByNameAndSwitchedOnAndContainerId(functionName, true, null);
        if(function == null) return false;
        else {
            function.switchOff();
        return true;
        }
    }

    @Override
    public List<FunctionDTO> getConfig() {
        List<FunctionDTO> functionDTOList = new ArrayList<>();
        List<Function> functions = functionRepository.findAllByAvailableAndSwitchedOnAndContainerId(true, true, null);
        functionDTOList.addAll(functions.stream().map(FunctionWebResourceUtil::toFunctionDTO).collect(Collectors.toList()));
        return functionDTOList;
    }

    @Override
    public boolean evaluateFunctions(String userName, Float x) {
        List<Function> switchedOnFunctions = functionRepository.findAllByAvailableAndSwitchedOnAndContainerId(true, true, null);
        for(Function function : switchedOnFunctions){
            List<Calculation> calculations = function.evaluate(x, userName);
           if(calculationRepository.save(calculations).isEmpty()) return false;
        }
        return true;
    }

    @Override
    public List<FunctionDTO> getDisconnected() {
        List<FunctionDTO> functionDTOList = new ArrayList<>();
        List<Function> functions = functionRepository.findAllByAvailableAndSwitchedOn(true, false);
        if(functions != null){
            List<Function> primitiveFunc = functions.stream().filter(it -> it.getContainer() == null).collect(Collectors.toList());
            for(Function func :primitiveFunc){
                Calculation calc = calculationRepository.findOneByDisconnect(func.getName());
                if(calc != null) functionDTOList.add(FunctionWebResourceUtil.toFunctionDTO(func));
            }
        }
        return functionDTOList;
    }

    private void createPrimitiveFunctions(List<String> functionNames, Container container) {
        for(String functionName :functionNames){
            saveFunction(functionName, container);
        }
    }

    private void saveFunction(String functionName, Container container) {
        try {
            Function function = PrimitiveFunction.getFunction( functionName);
            function.setContainer(container);
            functionRepository.save(function);
        } catch (FunctionNotFoundException e) {
            e.printStackTrace();
        }
    }


}
