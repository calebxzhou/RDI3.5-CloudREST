package calebzhou.rdi.microservice.component

import calebzhou.rdi.microservice.constant.ResponseCode
import calebzhou.rdi.microservice.model.ResponseData
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

/**
 * Created by calebzhou on 2022-10-05,7:24.
 */
@RestControllerAdvice
class RdiResponseAdvice : ResponseBodyAdvice<Any> {
    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {
        return true
    }

    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Any {
        return when (body) {
            is ResponseData<*> -> body
            is ResponseCode -> ResponseData(body,null)
            else -> ResponseData(ResponseCode.Success,body)
        }

    }
}