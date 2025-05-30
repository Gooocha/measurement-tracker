package org.example.measurementtracker1.controller;

import org.example.measurementtracker1.model.Measurement;
import org.example.measurementtracker1.repository.MeasurementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;





@SpringBootTest
@AutoConfigureMockMvc
public class MeasurementControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private MeasurementRepository measurementRepository;


    @Test
    void createMeasurement_shouldReturnBadRequest_onInvalidInput() throws Exception {

        List<String> invalidJsonInputs = List.of(
                // Без userId
                """
                {
                  "timestamp": "2025-05-28T10:00:00",
                  "gas": 12.5,
                  "coldWater": 12.0,
                  "hotWater": 14.2
                }
                """,
                // Без timestamp
                """
                {
                  "userId": 1,
                  "gas": 12.5,
                  "coldWater": 12.0,
                  "hotWater": 14.2
                }
                """,
                // Без gas
                """
                {
                  "userId": 1,
                  "timestamp": "2025-05-28T10:00:00",
                  "coldWater": 12.0,
                  "hotWater": 14.2
                }
                """
        );

        for (String json : invalidJsonInputs) {
            mockMvc.perform(post("/measurements")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest());
        }

    }
    @BeforeEach
    void setUp() {
        measurementRepository.deleteAll();

        measurementRepository.saveAll(List.of(
                new Measurement(1L, LocalDateTime.of(2025, 5, 28, 10, 0), 10.0, 15.0, 5.0),
                new Measurement(1L, LocalDateTime.of(2025, 5, 29, 11, 30), 12.5, 14.0, 6.2)
        ));
    }
    @Test
    void getMeasurements_shouldReturnDataFromDatabase() throws Exception {

        mockMvc.perform(get("/measurements/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].gas", is(10.0)))
                .andExpect(jsonPath("$[1].hotWater", is(14.0)));
    }


}
