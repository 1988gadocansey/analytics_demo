package com.gad.mathswork;

 

import com.gad.mathswork.Application.MathsWorkRepository;
import com.gad.mathswork.Domain.MathsWork;
import com.gad.mathswork.Endpoint.MathsWorkEndpoint;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.when;

@WebMvcTest(MathsWorkEndpoint.class)
class MathsWorkEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MathsWorkRepository mathsWorkRepository;

    @Test
    void getAllIntegers() throws Exception {
        // Mock data
        MathsWork mathsWork = new MathsWork();
        mathsWork.setId(1L);
        mathsWork.setData(12);

        Page<MathsWork> page = new org.springframework.data.domain.PageImpl<>(Collections.singletonList(mathsWork));

        // Mock the repository response
        when(mathsWorkRepository.findAll(org.mockito.ArgumentMatchers.any(Pageable.class)))
                .thenReturn(page);

        // Perform the request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/integers/index")
                .param("page", "0")
                .param("size", "3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mathwork[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mathwork[0].data").value(12))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPage").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalItems").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(1));
    }
}
