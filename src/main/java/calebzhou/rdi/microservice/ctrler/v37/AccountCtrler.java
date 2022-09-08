package calebzhou.rdi.microservice.ctrler.v37;

import calebzhou.rdi.microservice.model.dto.ResultData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v37/account")
public class AccountCtrler {

    //客户端发起注册请求
    @PostMapping("/register-step-1")
    public ResultData register(@RequestParam String id, @RequestParam String pwd, @RequestParam String qq, HttpServletRequest request){
        //TODO 数据库里qq有没有注册过，然后看ip有没有注册过，如果是中国ip，则生成6位数验证码，要求使用qq发送验证
    }

}
