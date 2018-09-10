package com.chisw.dynamicFunctions.util;


import com.chisw.dynamicFunctions.entity.Calculation;
import com.chisw.dynamicFunctions.entity.Function;
import com.chisw.dynamicFunctions.web.dto.CalculationDTO;
import com.chisw.dynamicFunctions.web.dto.FunctionDTO;

public class FunctionWebResourceUtil {

    public static FunctionDTO toFunctionDTO(Function function){

        FunctionDTO functionDTO= new FunctionDTO();
        functionDTO.setName(function.getName());
        functionDTO.setA(function.getA());
        functionDTO.setB(function.getB());
        return functionDTO;
    }

    public static CalculationDTO toCalculationDTO(Calculation calculation){

        CalculationDTO calculationDTO= new CalculationDTO();
        calculationDTO.setA(calculation.getFunction().getA());
        calculationDTO.setB(calculation.getFunction().getB());
        calculationDTO.setFunctionName(calculation.getFunction().getName());
        calculationDTO.setUserName(calculation.getUserName());
        calculationDTO.setResult(calculation.getResult());
        calculationDTO.setX(calculation.getX());
        return calculationDTO;
    }
}
