package com.N26.statistics.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
public class TransactionTest {

    private Transaction transaction;
    private Long timestamp = Instant.now().getEpochSecond();

    @Before
    public void init() {
        transaction = new Transaction();
        transaction.setAmount(50.0);
        transaction.setTimestamp(timestamp);
    }

    @Test
    public void assertTransactionProperties() {
        assertThat(transaction.getAmount()).isEqualTo(50.0);
        assertThat(transaction.getTimestamp()).isEqualTo(timestamp);
    }
}
