package calebzhou.rdicloudrest.module.account;

import calebzhou.rdicloudrest.App;
import calebzhou.rdicloudrest.ThreadPool;
import calebzhou.rdicloudrest.http.BasicServlet;
import calebzhou.rdicloudrest.utils.GeographyUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/account_login")
public class AccountLoginServlet extends BasicServlet {
    //允许登录：RDI名 vs IP36
    public static Map<String,String> qqipMap = new HashMap<>();
    //get 是否可以登录，如果允许登录返回true
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        super.doGet(req, resp);
        //RDI名
        String usr = req.getParameter("usr");
        //IP 36进制
        String ip = req.getParameter("ip");
        //IP 10进制
        String ip10 = req.getParameter("ip10");
        boolean loginSuccess = false;
        if(qqipMap.containsKey(usr) && qqipMap.get(usr).equalsIgnoreCase(ip))
            loginSuccess=true;
        else
            loginSuccess=false;
        write(resp,loginSuccess+"");

        long qq = AccountUtils.rdi2qq(usr);
        final boolean finalLoginSuccess = loginSuccess;
        ThreadPool.newThread(()->{
            if(!finalLoginSuccess)
                App.BOT.getBot().getFriend(qq).sendMessage("位于"+GeographyUtils.getSimpleGeoLocation(ip10)+"的一台电脑，想要登录您的RDI账号，允许此电脑登录吗？请回复r#login="+ip);

        });
        }


    //post 写入允许登录

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        super.doPost(req, resp);
        String usr = req.getParameter("usr");
        String ip = req.getParameter("ip");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        super.doDelete(req, resp);
        if(App.DEBUG)
        qqipMap.clear();
    }
}
