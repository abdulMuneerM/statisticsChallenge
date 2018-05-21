package com.N26.statistics.service;

import com.N26.statistics.config.StatisticsConfig;
import com.N26.statistics.model.Statistics;
import com.N26.statistics.model.Transaction;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final List<List<Transaction>> recentTransactions;
    private final Statistics statistics;
    private int STATISTICS_TIME;
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock writeLock = lock.writeLock();

    public StatisticsServiceImpl(StatisticsConfig statisticsConfig) {
        this.recentTransactions = new LinkedList<>();
        this.statistics = new Statistics();
        this.STATISTICS_TIME = statisticsConfig.getTime();

        for (int i = 0; i <= STATISTICS_TIME; i++) {
            recentTransactions.add(i, new ArrayList<>());
        }

        resetStatistics();
        updateStatistics();
    }

    private void resetStatistics() {
        try {
            writeLock.lock();
            statistics.setSum(0.0);
            statistics.setMin(0.0);
            statistics.setMax(0.0);
            statistics.setCount(0L);
            statistics.setAvg(0.0);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean addTransaction(Transaction transaction) {
        Long sec = Instant.now().getEpochSecond() - transaction.getTimestamp();
        if (sec < STATISTICS_TIME && sec >= 0) {
            recentTransactions.get(Math.toIntExact(sec)).add(transaction);
            return true;
        }

        return false;
    }

    @Override
    public Statistics getStatistics() {
        return statistics;
    }

    private void removeNonRecentTransactions() {
        recentTransactions.remove(STATISTICS_TIME);
        recentTransactions.add(0, new ArrayList<>());
        resetStatistics();
    }

    @Scheduled(fixedRate = 1000)
    public void statisticsScheduler() {
        removeNonRecentTransactions();
        updateStatistics();
    }

    @Override
    public void updateStatistics() {
        final Double[] sum = {0.0};
        final Double[] min = {Double.MAX_VALUE};
        final Double[] max = {0.0};
        final Long[] count = {0L};

        recentTransactions.forEach(transactions -> {
            transactions.forEach(transaction -> {
                Double transactionAmount = transaction.getAmount();
                if (transactionAmount != null) {
                    sum[0] += transactionAmount;

                    if (transactionAmount < min[0])
                        min[0] = transactionAmount;

                    if (transactionAmount > max[0])
                        max[0] = transactionAmount;
                }
            });

            count[0] += transactions.size();
        });

        if (min[0] == Double.MAX_VALUE)
            min[0] = 0.0;

        try {
            writeLock.lock();
            statistics.setSum(sum[0]);
            statistics.setMin(min[0]);
            statistics.setMax(max[0]);
            statistics.setCount(count[0]);
            Double avg = count[0] != 0.0 ? sum[0] / count[0] : 0.0;
            statistics.setAvg(avg);
        } finally {
            writeLock.unlock();
        }
    }
}
