package com.N26.statistics.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
public class StatisticsTest {

    private Statistics statistics;

    @Before
    public void init() {
        statistics  = new Statistics();
        statistics.setSum(100.0);
        statistics.setCount(2L);
        statistics.setAvg(50.0);
        statistics.setMin(30.0);
        statistics.setMax(70.0);
    }

    @Test
    public void assertStatisticsProperties() {
        assertThat(statistics.getSum()).isEqualTo(100.0);
        assertThat(statistics.getCount()).isEqualTo(2);
        assertThat(statistics.getAvg()).isEqualTo(50.0);
        assertThat(statistics.getMax()).isEqualTo(70.0);
        assertThat(statistics.getMin()).isEqualTo(30.0);
    }
}
