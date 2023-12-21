package co.demo.common.advice;

import co.demo.common.exception.ExceptionsForAtm;
import co.demo.common.result.ExceptionResult;
import co.demo.common.enums.ExceptionEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(ExceptionsForAtm.class)
    public ResponseEntity<ExceptionResult> myExceptionHandler(ExceptionsForAtm e) {
        ExceptionEnum myEnum = e.getExceptionEnum();
        return ResponseEntity.status(myEnum.getCode()).body(new ExceptionResult(myEnum));
    }
}
