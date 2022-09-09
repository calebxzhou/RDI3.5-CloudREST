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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v37/account")
public class AccountCtrler {

    final AccountMapper mapper;
    public AccountCtrler(AccountMapper mapper) {
        this.mapper = mapper;
    }

    //客户端发起注册请求
    @PassToken
    @PostMapping("/register")
    public ResultCode register(@RequestParam String id, @RequestParam String pwd, @RequestParam String mac, HttpServletRequest request){
        String ip = request.getRemoteAddr();
        //看mac地址有没有注册过
        if (mapper.isMacAddressAlreadyRegistered(mac)) {
            return ResultCode.sourceAlreadyRegistered;
        }
        if(mapper.isIdAlreadyRegistered(id)){
            return ResultCode.sourceAlreadyRegistered;
        }
        mapper.insertAccount(new Account(id,pwd,mac,ip, TimeUtils.getNow()));
        return ResultCode.success;
    }
    @PassToken
    @PostMapping("/login")
    public Object login(@RequestParam String id, @RequestParam String pname,@RequestParam String pwd){
        //看id注册过吗
        if (!mapper.isIdAlreadyRegistered(id)) {
            return ResultCode.sourceNotRegistered;
        }
        if (mapper.isIdMatchesPwd(id,pwd)) {
            //登录成功，返回token
            return JwtUtils.createToken(id,pname);
        }else{
            return ResultCode.sourceIdNotMatchPassword;
        }

    }

}
