package calebzhou.rdicloudrest.http.ingame.v2;

import calebzhou.rdicloudrest.http.BasicServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProtectServlet extends BasicServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        super.doPost(req, resp);
        String area = getPath(req);
        String pid = req.getParameter("pid");

    }
}
