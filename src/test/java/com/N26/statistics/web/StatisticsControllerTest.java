package com.N26.statistics.web;

import com.N26.statistics.model.Statistics;
import com.N26.statistics.model.Transaction;
import com.N26.statistics.service.StatisticsService;
import com.N26.statistics.web.validator.TransactionValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
public class StatisticsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StatisticsService statisticsService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        TransactionValidator transactionValidator = new TransactionValidator();

        mockMvc = MockMvcBuilders
                .standaloneSetup(new StatisticsController(statisticsService, transactionValidator))
                .build();
    }

    @Test
    public void addTransaction() throws Exception {
        Transaction transaction = getTransactionObject(Instant.now().getEpochSecond());
        when(statisticsService.addTransaction(transaction)).thenReturn(true);

        ResultActions resultActions = mockMvc
                .perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(transaction)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getStatistics() throws Exception {
        Statistics statistics = getStatisticsObject();
        when(statisticsService.getStatistics()).thenReturn(statistics);

        ResultActions resultActions = mockMvc
                .perform(get("/statistics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.sum", is(100.0)));
    }

    private Statistics getStatisticsObject() {
        Statistics statistics = new Statistics();
        statistics.setSum(100.0);
        statistics.setCount(2L);
        statistics.setAvg(50.0);
        statistics.setMax(70.0);
        statistics.setMin(30.0);
        return statistics;
    }

    private Transaction getTransactionObject(Long timestamp) {
        Transaction transaction = new Transaction();
        transaction.setAmount(70.0);
        transaction.setTimestamp(timestamp);
        return transaction;
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
