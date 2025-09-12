package com.fiuza.vacinnation_card_validation.application.handlers;

import com.fiuza.vacinnation_card_validation.core.dto.errors.AlreadyExistExceptionDto;
import com.fiuza.vacinnation_card_validation.core.dto.errors.InvalidFileExceptionDto;
import com.fiuza.vacinnation_card_validation.core.dto.errors.NotFoundExceptionDto;
import com.fiuza.vacinnation_card_validation.core.exceptions.AlreadyExistException;
import com.fiuza.vacinnation_card_validation.core.exceptions.InvalidFileException;
import com.fiuza.vacinnation_card_validation.core.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<NotFoundExceptionDto> handlerNotFoundException(
            NotFoundException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status.value())
                .body(new NotFoundExceptionDto(exception.getMessage(), status.value()));
    }

    @ExceptionHandler(InvalidFileException.class)
    public ResponseEntity<InvalidFileExceptionDto> handlerInternalServerErrorException(
            InvalidFileException exception) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status.value())
                .body(new InvalidFileExceptionDto(exception.getMessage(), status.value()));
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<AlreadyExistExceptionDto> handlerBadRequestException(
            AlreadyExistException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status.value())
                .body(new AlreadyExistExceptionDto(exception.getMessage(), status.value()));
    }

}
