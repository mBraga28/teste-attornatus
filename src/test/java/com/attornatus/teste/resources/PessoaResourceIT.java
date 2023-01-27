package com.attornatus.teste.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.attornatus.teste.dto.PersonDTO;
import com.attornatus.teste.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PessoaResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private long existingId;
    private long nonExistingId;
    private long countTotalPersons;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalPersons = 25L;
    }

    @Test
    void findAllShouldReturnSortedPageWhenSortByName() throws Exception {

        ResultActions result =
                mockMvc.perform(get("/persons?page=0&size=12&sort=nome,asc")
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.totalElements").value(countTotalPersons));
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].nome").value("Macbook Pro"));
        result.andExpect(jsonPath("$.content[1].nome").value("PC Gamer"));
        result.andExpect(jsonPath("$.content[2].nome").value("PC Gamer Alfa"));

    }

    @Test
    void updatedShouldReturnPersonDtoWhenIdExists() throws Exception {

        PersonDTO personDto = Factory.createPersonDTO();
        String jsonBody = objectMapper.writeValueAsString(personDto);

        String expectedName = personDto.getName();
        LocalDate expectedBirthDate = personDto.getBirthDate();

        ResultActions result =
                mockMvc.perform(put("/pessoas/{id}", existingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").value(expectedName));
        result.andExpect(jsonPath("$.birthDate").value(expectedBirthDate));

    }

    @Test
    void updatedShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {

        PersonDTO personDto = Factory.createPersonDTO();
        String jsonBody = objectMapper.writeValueAsString(personDto);

        ResultActions result =
                mockMvc.perform(put("/persons/{id}", nonExistingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }
}
