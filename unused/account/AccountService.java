package calebzhou.rdicloudrest.module.account;

import calebzhou.rdicloudrest.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    final AccountRepo repo;

    public AccountService(AccountRepo repo) {
        this.repo = repo;
    }

    public String register(long qq){
        Optional<Account> acc = repo.findById(qq);
        if (acc.isPresent()) {
            return "您已经在"+ DateUtil.toReadableChineseDate(acc.get().getRegDate())+"注册过账号了，因此无法再次注册。";
        }
        Account account = new Account(qq);
        repo.save(account);
        return "注册成功。";
    }
    public String getinfo(long qq){
        Optional<Account> acc = repo.findById(qq);
        if(acc.isEmpty()){
            return "您还没注册RDI账号呢，想要现在注册吗？请回复r#reg";
        }
        Account account = acc.get();
        return "RDI账号："+AccountUtils.qq2rdi(account.getQq())+"，注册日期："+account.getRegDate();
    }

    public String login(long id, String ip36x) {
        if(StringUtils.isEmpty(ip36x))
            return "请您在等号右面输入验证码。";
        AccountLoginServlet.qqipMap.put(AccountUtils.qq2rdi(id),ip36x);
        return "验证成功！请登录服务器。";
    }
}
