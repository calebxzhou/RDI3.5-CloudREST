package calebzhou.rdi.microservice.component;

import calebzhou.rdi.microservice.constant.ResultCode;
import calebzhou.rdi.microservice.model.dto.ResultData;
import calebzhou.rdi.microservice.utils.RdiSerializer;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@RestControllerAdvice
public class RdiResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(body instanceof String st)
            return ResultData.success(st);
        if(body instanceof ResultData<?>)
            return body;
        if(body instanceof ResultCode resultCode)
            return resultCode.toResultData();
        /*if(String.valueOf(body).contains("Not Found") && String.valueOf(body).contains("404"))
            return ResultCode.notFound.toResultData();*/
        return ResultData.success(body);
    }
}
