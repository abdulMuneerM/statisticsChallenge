package com.N26.statistics.web;

import com.N26.statistics.model.Statistics;
import com.N26.statistics.model.Transaction;
import com.N26.statistics.service.StatisticsService;
import com.N26.statistics.web.validator.TransactionValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class StatisticsController {

    private final StatisticsService statisticsService;
    private final TransactionValidator transactionValidator;

    public StatisticsController(StatisticsService statisticsService, TransactionValidator transactionValidator) {
        this.statisticsService = statisticsService;
        this.transactionValidator = transactionValidator;
    }

    @InitBinder("transaction")
    protected void initTransactionBinder(WebDataBinder binder) {
        binder.setValidator(transactionValidator);
    }

    @PostMapping(
            value = "/transactions",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity addTransaction(@Valid @RequestBody Transaction transaction) {
        if (statisticsService.addTransaction(transaction)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(
            value = "/statistics",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Statistics> retrieveStatistics() {
        return new ResponseEntity<>(statisticsService.getStatistics(), HttpStatus.OK);
    }
}
