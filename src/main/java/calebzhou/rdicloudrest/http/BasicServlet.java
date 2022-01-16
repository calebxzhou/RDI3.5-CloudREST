package calebzhou.rdicloudrest.http;

import calebzhou.rdicloudrest.utils.DataConvertHelper;
import calebzhou.rdicloudrest.utils.IntegerDefaultAdapter;
import calebzhou.rdicloudrest.utils.ServletHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class BasicServlet extends HttpServlet {
    protected Gson gson = new GsonBuilder()
            .registerTypeAdapter(Integer.class, new IntegerDefaultAdapter())
            .registerTypeAdapter(int.class, new IntegerDefaultAdapter()).create();

    public BasicServlet() {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        setEncoding(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        setEncoding(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        setEncoding(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        setEncoding(req, resp);
    }

    protected void setEncoding(HttpServletRequest req, HttpServletResponse resp){
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
    }
    protected String getPath(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();

        String path = requestURI.replaceAll(contextPath, "").replaceAll(servletPath, "");
        return path;
    }
    protected void write(HttpServletResponse resp,Object content){
        try {
            resp.getWriter().write(String.valueOf(content));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            String action = ServletHelper.getPathAction(request);
            ServletHelper.invokeMethod(action, this, request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.toString());
            throw new ServletException(ex.toString());
        }
    }





    protected void responseSuccess(HttpServletResponse response, String message) {
        responseResultJSON(response, "Success", message);
    }
    protected void responseSuccess(HttpServletResponse response) {
        responseResultJSON(response, "Success", "成功");
    }
    protected void responseError(HttpServletResponse response, Exception exception) {
        try {
            throw exception;
        } catch (Exception ex) {
            log.error(ex.toString());
            responseResultJSON(response, "Error", ex.toString());
        }
    }


    private void responseResultJSON(HttpServletResponse response, String result, String message) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", result);
        map.put("message", message);

        responseObjectJSON(response, map);
    }

    protected void responseList(HttpServletRequest request, HttpServletResponse response, List<?> list) {

        int total = list.size();


        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows", list);
        map.put("total", total);

        responseObjectJSON(response, map);
    }

    protected void responseObjectJSON(HttpServletResponse response, Object obj) {
        try {
            response.getWriter().write(gson.toJson(obj));
        } catch (Exception ex) {
            log.error(ex.toString());
            responseError(response, ex);
        }
    }
    public static <T extends Serializable> T parseRequstJsonToObject(Class<T> objClass , HttpServletRequest request) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String json  = request.getParameter("obj");
        log.info("request:"+json);
        return new Gson().fromJson(json,objClass);
    }
}
