package com.ricardocreates.infra.rest.controller.flight;

import com.ricardocreates.utiltest.HttpUtilTest;
import com.swagger.client.codegen.rest.model.FlightIataInfoDto;
import com.swagger.client.codegen.rest.model.FlightInfoDto;
import com.swagger.client.codegen.rest.model.GenericErrorResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@AutoConfigureMockMvc
public class FlightControllerTest {
    private static final String FLIGHT_INFO_API = "/api/flight-info";
    private static final String FLIGHT_INFO_IATA_API = "/api/flight-info/iata";
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

    }

    @Test
    void should_getFlightInfo() throws Exception {
        //GIVEN
        Integer flightNumber = 7539;
        String date = "2019-02-19T05:40:20-01:00";
        //WHEN
        MvcResult response = this.mockMvc.perform(
                        get(String.format("%s", FLIGHT_INFO_API))
                                .param("flightNumber", String.valueOf(flightNumber))
                                .param("flightDate", date))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        var responseBody = HttpUtilTest.jsonToObject(response.getResponse().getContentAsString(), FlightInfoDto.class);
        //THEN
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getBaggageWeight()).isEqualTo("801333.317");
        assertThat(responseBody.getCargoWeight()).isEqualTo("468309.799");
        assertThat(responseBody.getTotalWeight()).isEqualTo("1269643.116");
    }

    @Test
    void should_not_getFlightInfo_notFound() throws Exception {
        //GIVEN
        Integer flightNumber = -1;
        String date = "2019-02-19T05:40:20-01:00";
        //WHEN
        MvcResult response = this.mockMvc.perform(
                        get(String.format("%s", FLIGHT_INFO_API))
                                .param("flightNumber", String.valueOf(flightNumber))
                                .param("flightDate", date))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andReturn();
        var responseBody = HttpUtilTest.jsonToObject(response.getResponse().getContentAsString(), GenericErrorResponseDto.class);
        //THEN
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getCode()).isEqualTo("404");
    }

    @Test
    void should_not_getFlightInfo_badParameter() throws Exception {
        //GIVEN
        Integer flightNumber = -1;
        String date = "BAD";
        //WHEN
        MvcResult response = this.mockMvc.perform(
                        get(String.format("%s", FLIGHT_INFO_API))
                                .param("flightNumber", String.valueOf(flightNumber))
                                .param("flightDate", date))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andReturn();
        var responseBody = HttpUtilTest.jsonToObject(response.getResponse().getContentAsString(), GenericErrorResponseDto.class);
        //THEN
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getCode()).isEqualTo("400 BAD_REQUEST");
    }

    @Test
    void should_getFlightIataInfo() throws Exception {
        //GIVEN
        String iataCode = "LAX";
        String date = "2019-02-19T05:40:20-01:00";
        //WHEN
        MvcResult response = this.mockMvc.perform(
                        get(String.format("%s", FLIGHT_INFO_IATA_API))
                                .param("iataCode", iataCode)
                                .param("flightDate", date))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        var responseBody = HttpUtilTest.jsonToObject(response.getResponse().getContentAsString(), FlightIataInfoDto.class);
        //THEN
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getNumberFlightDeparting()).isEqualTo(2);
        assertThat(responseBody.getNumberFlightArriving()).isEqualTo(0);
        assertThat(responseBody.getTotalPiecesBaggageDeparting()).isEqualTo(8281);
        assertThat(responseBody.getTotalPiecesBaggageArriving()).isEqualTo(0);
    }

    @Test
    void should_getFlightIataInfo_empty() throws Exception {
        //GIVEN
        String iataCode = "LAX_NOT_FOUND";
        String date = "2019-02-19T05:40:20-01:00";
        //WHEN
        MvcResult response = this.mockMvc.perform(
                        get(String.format("%s", FLIGHT_INFO_IATA_API))
                                .param("iataCode", iataCode)
                                .param("flightDate", date))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        var responseBody = HttpUtilTest.jsonToObject(response.getResponse().getContentAsString(), FlightIataInfoDto.class);
        //THEN
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getNumberFlightDeparting()).isEqualTo(0);
        assertThat(responseBody.getNumberFlightArriving()).isEqualTo(0);
        assertThat(responseBody.getTotalPiecesBaggageDeparting()).isEqualTo(0);
        assertThat(responseBody.getTotalPiecesBaggageArriving()).isEqualTo(0);
    }

    @Test
    void should_not_getFlightIataInfo_badParameter() throws Exception {
        //GIVEN
        String iataCode = "LAX";
        String date = "bad";
        //WHEN
        MvcResult response = this.mockMvc.perform(
                        get(String.format("%s", FLIGHT_INFO_IATA_API))
                                .param("iataCode", iataCode)
                                .param("flightDate", date))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andReturn();
        var responseBody = HttpUtilTest.jsonToObject(response.getResponse().getContentAsString(), GenericErrorResponseDto.class);
        //THEN
        assertThat(responseBody).isNotNull();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getCode()).isEqualTo("400 BAD_REQUEST");
    }
}
