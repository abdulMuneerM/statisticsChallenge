package com.N26.statistics.service;

import com.N26.statistics.config.StatisticsConfig;
import com.N26.statistics.model.Statistics;
import com.N26.statistics.model.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.linesOf;

@RunWith(SpringRunner.class)
public class StatisticsServiceTest {

    private StatisticsService statisticsService;

    @Before
    public void init() {
        StatisticsConfig statisticsConfig = new StatisticsConfig();
        statisticsConfig.setTime(60);
        statisticsService = new StatisticsServiceImpl(statisticsConfig);
    }

    @Test
    public void addTransactionTest() {
        Transaction transaction = new Transaction();
        transaction.setAmount(50.0);
        transaction.setTimestamp(Instant.now().getEpochSecond() - 1);
        boolean result = statisticsService.addTransaction(transaction);

        assertThat(result).isEqualTo(true);
    }

    @Test
    public void getStatisticsTest() {
        Transaction transaction1 = new Transaction();
        transaction1.setAmount(50.0);
        transaction1.setTimestamp(Instant.now().getEpochSecond() - 1);

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(20.0);
        transaction2.setTimestamp(Instant.now().getEpochSecond());

        statisticsService.addTransaction(transaction1);
        statisticsService.addTransaction(transaction2);

        statisticsService.updateStatistics();
        Statistics statistics = statisticsService.getStatistics();

        assertThat(statistics.getSum()).isEqualTo(70.0);
        assertThat(statistics.getCount()).isEqualTo(2);
        assertThat(statistics.getMax()).isEqualTo(50.0);
        assertThat(statistics.getMin()).isEqualTo(20.0);
    }
}
