package calebzhou.rdicloudrest.model.dto;

public class ApiAction {
    String action;
    String param;

    public ApiAction(String action, String param) {
        this.action = action;
        this.param = param;
    }

    public String getAction() {
        return action;
    }

    public String getParam() {
        return param;
    }
}
