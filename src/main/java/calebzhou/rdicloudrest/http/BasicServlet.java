package calebzhou.rdicloudrest.http;

import calebzhou.rdicloudrest.model.dto.ApiAction;
import calebzhou.rdicloudrest.model.dto.ApiResponse;
import calebzhou.rdicloudrest.utils.IntegerDefaultAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

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
    public static String getPath(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String path = requestURI
                .replaceFirst(contextPath, "")
                .replaceFirst(servletPath, "")
                .replaceFirst("/","");
        return path;
    }

   /* protected ApiAction getPathWithAction(HttpServletRequest request){
        String path=getPath(request);
        String[] pathSplit = path.split("/");
        if(pathSplit.length<1){
            return new ApiAction(path,"");
        }else
            return new ApiAction(pathSplit[0],pathSplit[1]);
    }*/
    public static void write(HttpServletResponse resp,Serializable content){
        try {
            resp.setContentType("text/html; charset=UTF-8");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().print(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void responseErrorHasIsland(HttpServletResponse resp,boolean flag){
        responseError(resp,"抱歉,目标玩家"+(flag?"已经拥":"没")+"有空岛.");
    }
    public static void responseErrorJoinedIsland(HttpServletResponse resp, boolean flag){
        responseError(resp,"抱歉,目标玩家"+(flag?"已经":"没")+"加入过空岛.");
    }
    public static void responseErrorParams(HttpServletResponse resp,String param) {
        responseError(resp, "参数" + param + "错误");
    }


    public static void responseSuccess(HttpServletResponse resp, String message){
        responseSuccess(resp, message,null);
    }
    public static void responseError(HttpServletResponse resp, String message){
        responseError(resp, message,null);
    }
    public static void responseInfo(HttpServletResponse resp, String message){
        responseInfo(resp, message,null);
    }


    public static void responseSuccess(HttpServletResponse resp, String message,Serializable data) {
        response(resp,"success",message,data);
    }
    public static void responseError(HttpServletResponse resp, String message,Serializable data)  {
        response(resp,"error",message,data);
    }
    public static void responseInfo(HttpServletResponse resp, String message,Serializable data)  {
        response(resp,"info",message,data);
    }


    public static void response(HttpServletResponse resp,String type,String message,Serializable data){
        write(resp,new ApiResponse(type,message,data));
    }

    protected static <T extends Serializable> T requestToObject(Class<T> objClass , HttpServletRequest request) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String json  = request.getParameter("obj");
        return new Gson().fromJson(json,objClass);
    }
}
