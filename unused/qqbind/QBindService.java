package calebzhou.rdicloudrest.module.qqbind;

import calebzhou.rdicloudrest.utils.RandomUtils;
import com.google.common.collect.HashBiMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QBindService {
    @Autowired
    QBindRepo repo;

    //验证码，qq号
    private final HashBiMap<String,String> qqBindNumberMap = HashBiMap.create();

    @CheckQBindStatus(needQBind = false)
    public String getQBindNumber(String qq){
        String oldId = qqBindNumberMap.inverse().get(qq);
        if(oldId!=null)
            return oldId;
        String randomId = RandomUtils.getRandomId();
        qqBindNumberMap.put(randomId,qq);
        return randomId;
    }
    @CheckQBindStatus(needQBind = false)
    public String bindNumberAndQq(String number,String pid){
        String qq = qqBindNumberMap.get(number);
        if(qq==null)
            throw new QBindException("验证码输入错误！");
        repo.save(new QBind(qq,pid));
        qqBindNumberMap.remove(number);
return  qq;
    }
    @CheckQBindStatus(needQBind = true)
    public void delete(String pid) {
        repo.deleteByPid(pid);
    }
}
