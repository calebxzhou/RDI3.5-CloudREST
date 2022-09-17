package calebzhou.rdi.microservice.component;

import calebzhou.rdi.microservice.App;
import calebzhou.rdi.microservice.constant.ResultCode;
import calebzhou.rdi.microservice.exception.RdiTokenFailureException;
import calebzhou.rdi.microservice.model.dto.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Created by calebzhou on 2022-09-08,20:36.
 */

@RestControllerAdvice
@Slf4j
public class RdiExceptionHandler {
    /**
     * 默认全局异常处理。
     * @param e the e
     * @return ResultData
     */

    //处理token无效异常
    @ExceptionHandler(RdiTokenFailureException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResultData handleRdiTokenFailure(RdiTokenFailureException exception) {
        if(App.DEBUG){
            exception.printStackTrace();
        }
        return ResultData.fail(ResultCode.accessDenied.getCode(), exception.getMessage());
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData<String> exception(Exception e) {
        log.error("全局异常信息 ex={}", e.getMessage(), e);
        return ResultData.fail(ResultCode.internalError.getCode(),e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResultData handleNoHandlerFound(NoHandlerFoundException e, WebRequest request) {
        return ResultCode.notFound.toResultData();
    }
}
