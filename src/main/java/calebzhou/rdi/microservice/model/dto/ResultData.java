package calebzhou.rdi.microservice.model.dto;

import calebzhou.rdi.microservice.constant.ResultCode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultData<T> {
    private int status;
    private String message;
    private T data;

    public ResultData(int status, String message) {
        this.status=status;
        this.message=message;
    }


    public static <T> ResultData<T> success(T data) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(ResultCode.success.getCode());
        resultData.setMessage(ResultCode.success.getMessage());
        resultData.setData(data);
        return resultData;
    }
    public static <T> ResultData<T> fail(int code, String message) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(code);
        resultData.setMessage(message);
        return resultData;
    }
}
