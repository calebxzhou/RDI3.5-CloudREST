package calebzhou.rdicloudrest.ctrler;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
@Transactional
@RestController
@RequestMapping("/record")
public class RecordCtrler {

    @PersistenceContext
    EntityManager entityManager;

    //记录说话、指令
    @RequestMapping(value = "/chat",method = RequestMethod.POST)
    public void recordChat(@RequestParam String pid, @RequestParam String cont){
        entityManager.createNativeQuery("insert into RecordChat values (?,?,?)")
                .setParameter(1,pid)
                .setParameter(2,cont)
                .executeUpdate();
    }

    //记录登入
/*
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public void recordLogIn(@RequestParam String pid,@RequestParam String pid){
        entityManager.createNativeQuery("insert into RecordLogin values (?,?,?)")
                .setParameter(1,pid)
                .setParameter(2,cont)
                .executeUpdate();
    }*/

    //记录死亡
    @RequestMapping(value = "/death",method = RequestMethod.POST)
    public void recordDeath(@RequestParam String pid, @RequestParam String src){
        entityManager.createNativeQuery("insert into RecordDeath values (?,?,?)")
                .setParameter(1,pid)
                .setParameter(2,src)
                .executeUpdate();

    }
    //记录玩家的uuid和昵称
    @RequestMapping(value = "/idname",method = RequestMethod.POST)
    public void recordIdName(@RequestParam String pid, @RequestParam String pname){
       entityManager.createNativeQuery("insert into RecordIdName values (?,?)")
                       .setParameter(1,pid)
                       .setParameter(2,pname)
                       .executeUpdate();
    }
    @RequestMapping(value = "/hwinfo/{pid}",method = RequestMethod.POST)
    public void recordHardwareInfo(@PathVariable String pid){

    }

}
