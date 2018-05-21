package com.N26.statistics.service;

import com.N26.statistics.model.Statistics;
import com.N26.statistics.model.Transaction;

public interface StatisticsService {

    boolean addTransaction(Transaction transaction);

    Statistics getStatistics();

    void updateStatistics();
}
