package calebzhou.rdicloudrest.module.account;

import calebzhou.rdicloudrest.http.BasicServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/account_utils")
public class FastAccountUtilsServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        super.doGet(req, resp);
        String method = req.getParameter("method");
        String param = req.getParameter("param");
        String result = switch (method){
            case "qq2rdi" -> AccountUtils.qq2rdi(Long.parseLong(param));
            case "qq2hex" -> AccountUtils.qq2hex(Long.parseLong(param));
            case "hex2rdi" -> AccountUtils.hex2rdi((param));
            case "rdi2hex" -> AccountUtils.rdi2hex((param));
            case "rdi2qq" -> String.valueOf(AccountUtils.rdi2qq((param)));
            case "hex2qq" -> String.valueOf(AccountUtils.hex2qq((param)));
            default -> "?";
        };
        write(resp,result);
    }
}
