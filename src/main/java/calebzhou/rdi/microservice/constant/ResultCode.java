package calebzhou.rdi.microservice.constant;

import calebzhou.rdi.microservice.model.dto.ResultData;

public enum ResultCode {
    success(200,"success"),
    notFound(-404,"not found"),
    accessDenied(-403,"access denied"),
    internalError(-500,"server error"),


    //
    sourceAlreadyOwnIsland (-100,""),
    sourceAlreadyJoinAnyIsland(-101,""),
    sourceNotOwnIsland (-102,""),
    sourceNotJoinAnyIsland(-103,""),
    sourceNotInsideOwnIsland(-104,""),

    targetAlreadyOwnIsland(-110,""),
    targetAlreadyJoinAnyIsland(-111,""),
    targetNotOwnIsland(-112,""),
    targetNotJoinAnyIsland(-113,""),

    sourceEqualsTarget(-120,""),

    ;



    private final int code;
    /**自定义描述**/
    private final String message;

    ResultCode(int code, String message){
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
    public ResultData toResultData(){
        return new ResultData(this.code,this.message);
    }
}
