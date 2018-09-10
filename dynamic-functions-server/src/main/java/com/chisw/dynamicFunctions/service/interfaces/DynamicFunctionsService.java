package com.chisw.dynamicFunctions.service.interfaces;

import com.chisw.dynamicFunctions.web.dto.ConfigDTO;
import com.chisw.dynamicFunctions.web.dto.FunctionDTO;

import java.util.List;

/**
 * This is class is used for realization business logic of that application
 */
public interface DynamicFunctionsService {

    /**
     * This method set initial data for using functions
     * @param configDTO incoming data from rest controller
     * @return "true" if initialization finished successful or "false" if not.
     */
    boolean initConfig(ConfigDTO configDTO);

    /**
     * This method get all available functions
     * @return List of found functions and containers
     */
    List<FunctionDTO> getAvailableFunctions();

    /**
     * This method connect function for use
     * @param functionName name of connecting function
     * @param a argument
     * @param b argument
     * @return "true" if function connected successful or "false" if not.
     */
    boolean connectFunction(String functionName, Float a, Float b);

    /**
     * This method disconnect function from use
     * @param functionName name of disconnecting function
     * @return "true" if function disconnected successful or "false" if not.
     */
    boolean disconnectFunction(String functionName);

    /**
     * This method get all available and switched on functions
     * @return List of found functions and containers
     */
    List<FunctionDTO> getConfig();

    /**
     * This method starts evaluate in all available and switched on functions and save the results to database
     * @param userName name of user for who do this evaluations
     * @param x arguments for evaluation
     * @return "true" if evaluation done successful or "false" if not.
     */
    boolean evaluateFunctions(String userName, Float x);

    /**
     * This method get all available and switched off primitive functions which are like a primitive functions in any
     * available and switched on container and which are was used at least one's times
     * @return List of found functions
     */
    List<FunctionDTO> getDisconnected();
}
