package co.demo.common.advice;

import co.demo.common.enums.ExceptionEnum;
import co.demo.common.exception.AtmException;
import co.demo.common.vo.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(AtmException.class)
    public ResponseEntity<ExceptionResult> myExceptionHandler(AtmException e) {
        ExceptionEnum myEnum = e.getExceptionEnum();
        return ResponseEntity.status(myEnum.getCode()).body(new ExceptionResult(myEnum));
    }
}
