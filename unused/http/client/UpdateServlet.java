package calebzhou.rdicloudrest.http.client;

import calebzhou.rdicloudrest.http.BasicServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/public/update")
public class UpdateServlet extends BasicServlet {
    public static final int VERSION = 0x3B5;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        super.doGet(req, resp);
        responseSuccess(resp,String.valueOf(VERSION));
    }
}
