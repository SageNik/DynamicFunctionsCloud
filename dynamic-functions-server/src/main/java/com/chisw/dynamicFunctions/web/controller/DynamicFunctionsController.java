package com.chisw.dynamicFunctions.web.controller;

import com.chisw.dynamicFunctions.service.interfaces.CalculationService;
import com.chisw.dynamicFunctions.service.interfaces.DynamicFunctionsService;
import com.chisw.dynamicFunctions.web.dto.CalculationDTO;
import com.chisw.dynamicFunctions.web.dto.ConfigDTO;
import com.chisw.dynamicFunctions.web.dto.FunctionDTO;
import com.chisw.dynamicFunctions.web.dto.UsageBodyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * A RESTFul web for accessing dynamic function information.
 */
@RestController
@RequestMapping(value = "/")
public class DynamicFunctionsController {

    private final DynamicFunctionsService service;
    private final CalculationService calculationService;

    @Autowired
    public DynamicFunctionsController(DynamicFunctionsService service, CalculationService calculationService) {
        this.service = service;
        this.calculationService = calculationService;
    }

    @RequestMapping(value = "initialConfig",method = RequestMethod.POST)
    public ResponseEntity initial(@RequestBody ConfigDTO configDTO){
        if(service.initConfig(configDTO)) return new ResponseEntity(HttpStatus.OK);
        else return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "getAvail", method = RequestMethod.GET)
    public ResponseEntity getAvailable(){
        List<FunctionDTO> functions = service.getAvailableFunctions();
        return new ResponseEntity<>(functions, HttpStatus.OK);
    }

    @RequestMapping(value = "connect", method = RequestMethod.POST)
    public ResponseEntity connect(@RequestParam("name")String functionName, @RequestParam("a")String a, @RequestParam("b")String b){
        if(functionName == null || a == null || b == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            if(service.connectFunction(functionName, Float.valueOf(a), Float.valueOf(b)))return new ResponseEntity<>("The function with name: [ "+functionName+ " ] has been successfully connected",HttpStatus.OK);
            else return new ResponseEntity<>("Sorry, the available function with name: [ "+functionName+ " ] hasn't been found",HttpStatus.OK);
        }
    }

    @RequestMapping(value = "disconnect", method = RequestMethod.POST)
    public ResponseEntity disconnect(@RequestParam("name")String functionName){
        if(service.disconnectFunction(functionName))return new ResponseEntity<>("The function with name: [ "+functionName+ " ] has been successfully disconnected",HttpStatus.OK);
        else return new ResponseEntity<>("Sorry, the available function with name: [ "+functionName+ " ] hasn't been found",HttpStatus.OK);
    }

    @RequestMapping(value = "getConfig", method = RequestMethod.GET)
    public ResponseEntity getConfig(){
        List<FunctionDTO> functions = service.getConfig();
        return new ResponseEntity<>(functions, HttpStatus.OK);
    }

    @RequestMapping(value = "eval", method = RequestMethod.POST)
    public ResponseEntity evaluate(@RequestParam("user")String userName, @RequestParam("x")Float x){
        if(userName == null || x == null) return new ResponseEntity(HttpStatus.BAD_REQUEST);
     if(service.evaluateFunctions(userName, x)) return new ResponseEntity<>("All available functions was successfully evaluated", HttpStatus.OK);
     else return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "getUsage", method = RequestMethod.GET)
    public ResponseEntity getUsage(UsageBodyDTO dto){
        List<CalculationDTO> calculations = calculationService.getUsage(dto);
        return  new ResponseEntity<>(calculations, HttpStatus.OK);
    }

    @RequestMapping(value = "getDisconnected", method = RequestMethod.GET)
    public ResponseEntity getDisconnected(){
        List<FunctionDTO> functions = service.getDisconnected();
        return new ResponseEntity<>(functions, HttpStatus.OK);
    }

    @RequestMapping(value = "getMaxUser", method = RequestMethod.GET)
    public ResponseEntity getMaxUser(UsageBodyDTO dto){
        String maxUser = calculationService.getMaxUser(dto);
        if (maxUser != null) return new ResponseEntity<>(maxUser, HttpStatus.OK);
        else return new ResponseEntity<>("Sorry any user not found", HttpStatus.OK);
    }
}
