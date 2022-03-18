package calebzhou.rdicloudrest.module.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired AccountRepo repo;


   /* @RequestMapping(value = "/",method = RequestMethod.POST)
    public String register(@RequestParam("pwd")String pwd){
        int id = RandomUtils.getRandomIdV2();
        Account account = new Account(id,pwd);
        repo.save(account);
        return "200;OK.请牢记RDI账号："+id+"，密码即为您刚刚输入的密码";
    }*/

}

