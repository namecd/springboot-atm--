package co.demo.common.exception;

import co.demo.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AtmException extends RuntimeException {
    private ExceptionEnum exceptionEnum;
}
