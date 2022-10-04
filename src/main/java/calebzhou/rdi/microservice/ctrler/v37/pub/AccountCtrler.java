package calebzhou.rdi.microservice.ctrler.v37.pub;

import calebzhou.rdi.microservice.component.PassToken;
import calebzhou.rdi.microservice.dao.AccountMapper;
import calebzhou.rdi.microservice.utils.TimeUtils;
import com.auth0.jwt.JWT;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v37/public/account")
public class AccountCtrler {

    final AccountMapper mapper;
    public AccountCtrler(AccountMapper mapper) {
        this.mapper = mapper;
    }
    //设定密码


    //客户端发起注册请求
    @PassToken
    @PostMapping("/register/{id}")
    public ResponseCode register(@PathVariable String id, @RequestParam String pwd, @RequestParam String ip){
        if(mapper.isIdAlreadyRegistered(id)){
            return ResponseCode.sourceAlreadyRegistered;
        }
        mapper.insertAccount(new Account(id,pwd,ip, TimeUtils.getNow()));
        return ResponseCode.success;
    }
    @PassToken
    @GetMapping("/isreg/{id}")
    public ResponseData isRegistered(@PathVariable String id){
        return ResponseData.success(mapper.isIdAlreadyRegistered(id));
    }
    @PassToken
    @GetMapping("/login/{id}")
    public Object login(@PathVariable String id, /*@RequestParam String pname,*/@RequestParam String pwd){
        //看id注册过吗
        if (!mapper.isIdAlreadyRegistered(id)) {
            return ResponseCode.sourceNotRegistered;
        }
        if (mapper.isIdMatchesPwd(id,pwd)) {
            //登录成功，返回token
            return ResponseCode.success;//ResultData.success(/*JwtUtils.createToken(id,pname)*/);
        }else{
            return ResponseCode.sourceIdNotMatchPassword;
        }

    }

}
