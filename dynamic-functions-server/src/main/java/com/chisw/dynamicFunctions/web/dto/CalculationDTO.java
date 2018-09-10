package com.chisw.dynamicFunctions.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CalculationDTO {

    private String functionName;
    private String userName;
    private Float x,a,b;
    private Float result;
}
