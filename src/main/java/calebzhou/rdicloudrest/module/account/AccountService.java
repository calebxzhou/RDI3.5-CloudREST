package calebzhou.rdicloudrest.module.account;

import calebzhou.rdicloudrest.utils.DateUtil;
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
}
