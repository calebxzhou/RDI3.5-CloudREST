package calebzhou.rdicloudrest.http.ingame;

import calebzhou.rdicloudrest.DaoFactory;
import calebzhou.rdicloudrest.http.BasicServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/refresh/cache")
public class RefreshServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        super.doGet(req, resp);
        DaoFactory.initCaches();
        responseSuccess(resp,"1");
    }
}
