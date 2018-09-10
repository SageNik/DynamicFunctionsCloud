package com.chisw.dynamicFunctions.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ContainerDTO extends FunctionDTO{

    private List<String> functions;

    public ContainerDTO() {
        this.functions = new ArrayList<>();
    }
}
