package calebzhou.rdi.microservice.constant;

import calebzhou.rdi.microservice.model.dto.ResultData;

public enum ResultCode {
    success(200,"成功"),
    notFound(-404,"路径找不到"),
    accessDenied(-403,"access denied"),
    internalError(-500,"server error"),
    tokenInvalid(-401,"登录失效，请重新登录！"),
    //账号系列
    sourceAlreadyRegistered(-10,"请求者已经注册过一个账号了"),
    sourceNotRegistered(-11,"请求者没注册账号"),
    sourceIdNotMatchPassword(-11,"请求者的用户名和密码不匹配"),




    //岛屿系列
    sourceAlreadyOwnIsland (-100,"请求者已经拥有岛屿"),
    sourceAlreadyJoinAnyIsland(-101,"请求者已经加入过了一个岛屿"),
    sourceNotOwnIsland (-102,"请求者未拥有岛屿"),
    sourceNotJoinAnyIsland(-103,"请求者未加入任何一个岛屿"),
    sourceNotInsideOwnIsland(-104,"请求者不在自己的岛屿上"),

    targetAlreadyOwnIsland(-110,"目标已经拥有岛屿"),
    targetAlreadyJoinAnyIsland(-111,"目标已经加入过了一个岛屿"),
    targetNotOwnIsland(-112,"目标未拥有岛屿"),
    targetNotJoinAnyIsland(-113,"目标未加入任何一个岛屿"),

    sourceEqualsTarget(-120,"请求者和目标相同"),

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
