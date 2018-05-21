package com.N26.statistics.web.validator;

import com.N26.statistics.model.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.Instant;

@Component
public class TransactionValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Transaction.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Transaction transaction = (Transaction) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors,
                "amount",
                "emptyField.transaction.amount",
                "amount is empty or invalid. Please enter valid amount."
        );
        if (errors.hasErrors()) return;

        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors,
                "timestamp",
                "emptyField.transaction.timestamp",
                "timestamp is empty or invalid. Please enter valid timestamp."
        );
        if (errors.hasErrors()) return;

        if (transaction.getAmount() < 0) {
            errors.rejectValue(
                    "amount",
                    "constraintViolation.transaction.amount",
                    "amount should be greater than or equal to zero."
            );
        }

        if (transaction.getTimestamp() > Instant.now().getEpochSecond()) {
            errors.rejectValue(
                    "timestamp",
                    "constraintViolation.transaction.timestamp",
                    "timestamp should not be greater than current time."
            );
        }
    }
}
