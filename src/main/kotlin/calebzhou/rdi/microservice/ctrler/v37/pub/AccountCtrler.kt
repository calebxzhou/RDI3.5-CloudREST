package calebzhou.rdi.microservice.ctrler.v37.pub

import calebzhou.rdi.microservice.component.PassToken
import calebzhou.rdi.microservice.constant.ResponseCode
import calebzhou.rdi.microservice.dao.AccountMapper
import calebzhou.rdi.microservice.model.Account
import calebzhou.rdi.microservice.utils.TimeUtils
import org.springframework.web.bind.annotation.*

/**
 * Created by calebzhou on 2022-10-05,21:05.
 */
@RestController
@RequestMapping("/v37/public/account")
class AccountCtrler(var mapper: AccountMapper){

    //设定密码
    //客户端发起注册请求
    @PassToken
    @PostMapping("/register/{id}")
    fun register(@PathVariable id: String, @RequestParam pwd: String, @RequestParam ip: String): ResponseCode {
        if (mapper.isIdAlreadyRegistered(id)) {
            return ResponseCode.SourceAlreadyRegistered
        }
        mapper.insertAccount(Account(id, pwd, ip, TimeUtils.now))
        return ResponseCode.Success
    }

    @PassToken
    @GetMapping("/isreg/{id}")
    fun isRegistered(@PathVariable id: String): Boolean {
        return mapper.isIdAlreadyRegistered(id)
    }

    @PassToken
    @GetMapping("/login/{id}")
    fun login(@PathVariable id: String, @RequestParam pwd: String): Any {
        //看id注册过吗
        if (!mapper.isIdAlreadyRegistered(id)) {
            return ResponseCode.SourceNotRegistered
        }
        return if (mapper.isIdMatchesPwd(id, pwd)) {
            //登录成功，返回token
            ResponseCode.Success //ResultData.success(/*JwtUtils.createToken(id,pname)*/);
        } else {
            ResponseCode.SourceIdNotMatchPassword
        }
    }
}