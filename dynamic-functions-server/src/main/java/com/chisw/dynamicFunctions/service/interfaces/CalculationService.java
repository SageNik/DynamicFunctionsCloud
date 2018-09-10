package com.chisw.dynamicFunctions.service.interfaces;

import com.chisw.dynamicFunctions.web.dto.CalculationDTO;
import com.chisw.dynamicFunctions.web.dto.UsageBodyDTO;

import java.util.List;

/**
 * This is class is used for working with calculations ({@link com.chisw.dynamicFunctions.entity.Calculation})
 */
public interface CalculationService {

    /**
     * This method get all functions by user name which was evaluated according parameters x1 <= x <= x2
     * @param dto consists required request data
     * @return list of found CalculationDTO as a result
     */
    List<CalculationDTO> getUsage(UsageBodyDTO dto);

    /**
     * This method get user name who was evaluated max times current function according parameters x1 <= x <= x2
     * @param dto consists required request data
     * @return name of user as a result
     */
    String getMaxUser(UsageBodyDTO dto);
}
