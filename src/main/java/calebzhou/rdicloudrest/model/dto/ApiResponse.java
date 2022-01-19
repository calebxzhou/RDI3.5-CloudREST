package calebzhou.rdicloudrest.model.dto;

import com.google.gson.Gson;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.Serializable;

public class ApiResponse implements Serializable {
    private String type;
    private String message;
    private Serializable data;

    public ApiResponse() {
    }

    public ApiResponse(String type, String message, @Nullable Serializable data) {
        this.type = type;
        this.message = message;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Nullable
    public Serializable getData() {
        return data;
    }

    public void setData(Serializable data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
