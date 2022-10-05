package calebzhou.rdi.microservice.component

import calebzhou.rdi.microservice.DEBUG
import calebzhou.rdi.microservice.constant.ResponseCode
import calebzhou.rdi.microservice.exception.RdiTokenFailureException
import calebzhou.rdi.microservice.logger
import calebzhou.rdi.microservice.model.ResponseData
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException

/**
 * Created by calebzhou on 2022-10-04,22:20.
 */
@RestControllerAdvice
class RdiExceptionHandler {

    @ExceptionHandler(RdiTokenFailureException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleRdiTokenFail(exception: RdiTokenFailureException): ResponseData<Nothing?>{
        if (DEBUG) {
            exception.printStackTrace()
        }
        return ResponseData(ResponseCode.AccessDenied)
    }
    @ExceptionHandler(NoHandlerFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNoHandlerFound(e: NoHandlerFoundException?, request: WebRequest?): ResponseData<Nothing?> {
        return ResponseData(ResponseCode.NotFound)
    }
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun exception(e: Exception): ResponseData<Nothing?> {
        logger.error("全局异常信息 ex={}", e.message, e)
        val error = ResponseCode.InternalError
        error.msg+=e.message+e.cause
        return ResponseData(error)
    }


}