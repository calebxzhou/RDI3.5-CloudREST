package calebzhou.rdicloudrest.controller;

import calebzhou.rdicloudrest.dao.IslandDao;
import calebzhou.rdicloudrest.model.CoordLocation;
import calebzhou.rdicloudrest.model.Island;
import calebzhou.rdicloudrest.utils.RequestUtils;
import calebzhou.rdicloudrest.utils.ResponseUtils;
import calebzhou.rdicloudrest.utils.SqlUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/island")
public class IslandController extends HttpServlet {
    HttpServletResponse response;
    HttpServletRequest request;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        this.request =req;
        this.response = resp;
        IslandAction action = null;
        try {
            action = Enum.valueOf(IslandAction.class,req.getParameter("action"));
            switch (action){
                case HAS -> has();
                case DELETE -> delete();
                case JOIN -> join();
                case CREATE -> create();
                case JOINED -> joined();
                case LOCATE -> locate();
                case GETID -> getIslandId();
                case GETID_JOINED -> getIslandIdJoined();
                case QUIT -> quit();
                case GET -> get();
            }
        }catch (RuntimeException | SQLException | IllegalAccessException exception){
            ResponseUtils.write(resp,exception.getMessage());
            exception.printStackTrace();
        }

    }
    private void get() throws SQLException, IllegalAccessException {
            ResponseUtils.write(response, new Gson().toJson(IslandDao.get(request.getParameter("islandId"))));
    }
    private void getIslandId() throws SQLException, IllegalAccessException {
         
            ResponseUtils.write(response,""+IslandDao.getIslandId(request.getParameter("uuid")));
         
    }
    private void getIslandIdJoined() throws SQLException {
         
            ResponseUtils.write(response,""+IslandDao.getIslandIdJoined(request.getParameter("uuid")));
        
    }
    private void has() throws SQLException, IllegalAccessException {
        
            ResponseUtils.write(response,""+IslandDao.has(request.getParameter("uuid")));
         
    }
    private void joined() throws SQLException, IllegalAccessException {
        
            ResponseUtils.write(response,""+IslandDao.joined(request.getParameter("uuid")));
        
    }
    private void join() throws SQLException, IllegalAccessException {
        
            ResponseUtils.write(response,""+IslandDao.join(request.getParameter("uuid"),request.getParameter("islandId")));
        
    }
    private void create() throws SQLException, IllegalAccessException {
        
            ResponseUtils.write(response,""+IslandDao.create(RequestUtils.parseRequstJsonToObject(Island.class,request)));

    }
    private void delete() throws SQLException, IllegalAccessException {
        
            ResponseUtils.write(response,""+IslandDao.delete(request.getParameter("islandId")));
        
    }
    private void quit() throws SQLException, IllegalAccessException {
        
            ResponseUtils.write(response,""+IslandDao.quit(request.getParameter("islandId"),request.getParameter("uuid")));
        
    }
    private void locate() throws SQLException, IllegalAccessException {
        
            ResponseUtils.write(response,""+IslandDao.locate(request.getParameter("islandId"), CoordLocation.fromString(request.getParameter("location"))));
        
    }
}
enum IslandAction{
    //是否拥有空岛
    HAS,
    //是否加入他人空岛
    JOINED,
    //主动加入他人空岛
    JOIN,
    //邀请他人加入我的空岛
    INVITE,
    //创建空岛
    CREATE,
    //删除空岛
    DELETE,
    //改变空岛传送点
    LOCATE,
    //获取空岛ID
    GETID,
    //获取加入别人的空岛ID
    GETID_JOINED,
    //获取空岛信息
    GET,
    //退出他人空岛
    QUIT,
}