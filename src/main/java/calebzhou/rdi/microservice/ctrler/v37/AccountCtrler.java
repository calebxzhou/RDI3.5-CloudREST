package calebzhou.rdi.microservice.ctrler.v37;

import calebzhou.rdi.microservice.component.PassToken;
import calebzhou.rdi.microservice.constant.ResultCode;
import calebzhou.rdi.microservice.dao.AccountMapper;
import calebzhou.rdi.microservice.model.Account;
import calebzhou.rdi.microservice.model.dto.ResultData;
import calebzhou.rdi.microservice.utils.JwtUtils;
import calebzhou.rdi.microservice.utils.TimeUtils;
import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v37/account")
public class AccountCtrler {

    final AccountMapper mapper;
    public AccountCtrler(AccountMapper mapper) {
        this.mapper = mapper;
    }
    //设定密码


    //客户端发起注册请求
    @PassToken
    @PostMapping("/register/{id}")
    public ResultCode register(@PathVariable String id, @RequestParam String pwd, @RequestParam String ip){
        if(mapper.isIdAlreadyRegistered(id)){
            return ResultCode.sourceAlreadyRegistered;
        }
        mapper.insertAccount(new Account(id,pwd,ip, TimeUtils.getNow()));
        return ResultCode.success;
    }
    @PassToken
    @GetMapping("/isreg/{id}")
    public ResultData isRegistered(@PathVariable String id){
        return ResultData.success(mapper.isIdAlreadyRegistered(id));
    }
    @PassToken
    @GetMapping("/login/{id}")
    public Object login(@PathVariable String id, /*@RequestParam String pname,*/@RequestParam String pwd){
        //看id注册过吗
        if (!mapper.isIdAlreadyRegistered(id)) {
            return ResultCode.sourceNotRegistered;
        }
        if (mapper.isIdMatchesPwd(id,pwd)) {
            //登录成功，返回token
            return ResultCode.success;//ResultData.success(/*JwtUtils.createToken(id,pname)*/);
        }else{
            return ResultCode.sourceIdNotMatchPassword;
        }

    }

}
