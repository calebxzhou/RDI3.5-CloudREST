package calebzhou.rdicloudrest.module.qqbind;

import calebzhou.rdicloudrest.module.wrapper.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qbind")
public class QBindController {
    @Autowired
    QBindService service;

    @RequestMapping(value = "/{qq}",method = RequestMethod.GET)
    public SuccessResponse<Void> getBindNumberFromQq(@PathVariable(value = "qq") String qq){
        return new SuccessResponse<>(null,"您成功提交了绑定请求。请在服务器执行以下指令，来进行下一步：/bind "+service.getQBindNumber(qq));
    }
    @RequestMapping(value = "/{number}/{pid}",method = RequestMethod.POST)
    public SuccessResponse<Void> postBindQq(@PathVariable(value = "number") String number,@PathVariable(value = "pid") String pid){
        String qq=service.bindNumberAndQq(number,pid);
        return new SuccessResponse<>(null,"您成功绑定了QQ号"+qq);
    }

    @RequestMapping(value = "/{pid}",method = RequestMethod.DELETE)
    public SuccessResponse<Void> delete(@PathVariable(value = "pid") String pid){
        service.delete(pid);
        return new SuccessResponse<Void>(null,"删除成功");
    }

}
