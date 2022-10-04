package calebzhou.rdi.microservice.component;

import calebzhou.rdi.microservice.constant.ResponseCode;
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
            return ResponseData.success(st);
        if(body instanceof ResponseData<?>)
            return body;
        if(body instanceof ResponseCode responseCode)
            return responseCode.toResultData();
        if(body instanceof Enum e)
            return ResponseData.success(e.toString());
        /*if(String.valueOf(body).contains("Not Found") && String.valueOf(body).contains("404"))
            return ResultCode.notFound.toResultData();*/
        return ResponseData.success(body);
    }
}
