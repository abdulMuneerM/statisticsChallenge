package com.N26.statistics.config;

import com.N26.statistics.util.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Instant;

@Component
public class ErrorResponseBuilder {

    public ResponseEntity<ErrorResponseDTO> createErrorResponse(String message, int statusCode, String error) {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO();
        responseDTO.setStatus(statusCode);
        responseDTO.setError(error);
        if (!StringUtils.isEmpty(message)) {
            responseDTO.setMessage(message);
        } else {
            responseDTO.setMessage("Oops! something went wrong, Please try again.");
        }
        responseDTO.setTimestamp(Instant.now().getEpochSecond());
        return new ResponseEntity<>(responseDTO, HttpStatus.valueOf(statusCode));
    }
}

