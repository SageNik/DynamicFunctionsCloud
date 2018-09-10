package com.chisw.dynamicFunctions.web.controller;

import com.chisw.dynamicFunctions.service.interfaces.CalculationService;
import com.chisw.dynamicFunctions.service.interfaces.DynamicFunctionsService;
import com.chisw.dynamicFunctions.web.dto.ConfigDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static com.chisw.dynamicFunctions.testUtil.RestControllerTestHelper.mockMvc;
import static com.chisw.dynamicFunctions.testUtil.TestUtils.json;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DynamicFunctionsControllerUnitTest {

    @Mock
    private DynamicFunctionsService dynamicFunctionsService;
    @Mock
    private CalculationService calculationService;
    private MockMvc mvc;

    @Before
    public void setUp(){

        dynamicFunctionsService = mock(DynamicFunctionsService.class);
        calculationService = mock(CalculationService.class);
        mvc = mockMvc(MockMvcBuilders.standaloneSetup(new DynamicFunctionsController(dynamicFunctionsService,calculationService)));
    }

    @Test
    public void initial_success() throws Exception{
        ConfigDTO configDTO = new ConfigDTO();
        configDTO.getPrimitives().add("Cos");
        configDTO.getPrimitives().add("Sin");
        when(dynamicFunctionsService.initConfig(configDTO)).thenReturn(true);

        mvc.perform(post("/initialConfig").content(
            json("{'primitives': ['Cos', 'Sin'],'containers': []}"))
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk());

        verify(dynamicFunctionsService, times(1)).initConfig(configDTO);
    }

    @Test
    public void initial_fail() throws Exception{
        ConfigDTO configDTO = new ConfigDTO();
        configDTO.getPrimitives().add("Cos");
        configDTO.getPrimitives().add("Sin");
        when(dynamicFunctionsService.initConfig(configDTO)).thenReturn(false);

        mvc.perform(post("/initialConfig").content(
                json("{'primitives': ['Cos', 'Sin'],'containers': []}"))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isInternalServerError());

        verify(dynamicFunctionsService, times(1)).initConfig(configDTO);
    }
}
