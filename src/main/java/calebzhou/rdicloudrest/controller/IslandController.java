package calebzhou.rdicloudrest.controller;

import calebzhou.rdicloudrest.dao.IslandDao;
import calebzhou.rdicloudrest.model.CoordLocation;
import calebzhou.rdicloudrest.model.Island;
import calebzhou.rdicloudrest.utils.RequestUtils;
import calebzhou.rdicloudrest.utils.ResponseUtils;

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.request =req;
        this.response = resp;
        IslandAction action = null;
        try {
            action = Enum.valueOf(IslandAction.class,req.getParameter("action"));
        }catch (RuntimeException exception){
            ResponseUtils.write(resp,exception.getMessage());
            exception.printStackTrace();
        }
        switch (action){
            case HAS -> has();
            case DELETE -> delete();
            case JOIN -> join();
            case CREATE -> create();
            case JOINED -> joined();
            case LOCATE -> locate();
            case GETID -> getIslandId();
            case QUIT -> quit();
        }
    }
    private void getIslandId(){
        try {
            ResponseUtils.write(response,""+IslandDao.getIslandId(request.getParameter("uuid")));
        } catch (SQLException e) {
            e.printStackTrace();
            ResponseUtils.write(response,"null");
        }
    }
    private void has(){
        try {
            ResponseUtils.write(response,""+IslandDao.has(request.getParameter("uuid")));
        } catch (SQLException e) {
            e.printStackTrace();
            ResponseUtils.write(response,"false");
        }
    }
    private void joined(){
        try {
            ResponseUtils.write(response,""+IslandDao.joined(request.getParameter("uuid")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void join(){
        try {
            ResponseUtils.write(response,""+IslandDao.join(request.getParameter("uuid"),request.getParameter("islandId")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void create(){
        try {
            ResponseUtils.write(response,""+IslandDao.create(RequestUtils.parseRequstJsonToObject(Island.class,request)));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    private void delete(){
        try {
            ResponseUtils.write(response,""+IslandDao.delete(request.getParameter("islandId")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void quit(){
        try {
            ResponseUtils.write(response,""+IslandDao.quit(request.getParameter("islandId"),request.getParameter("uuid")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void locate(){
        try {
            ResponseUtils.write(response,""+IslandDao.locate(request.getParameter("islandId"), CoordLocation.fromString(request.getParameter("location"))));
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    //退出他人空岛
    QUIT,
}