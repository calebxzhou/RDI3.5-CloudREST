package calebzhou.rdicloudrest.http.ingame;

import calebzhou.rdicloudrest.DaoFactory;
import calebzhou.rdicloudrest.http.BasicServlet;
import calebzhou.rdicloudrest.logic.IslandLogic;
import calebzhou.rdicloudrest.model.CoordLocation;
import calebzhou.rdicloudrest.model.Island;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet("/island/*")
@Slf4j
public class IslandServlet extends BasicServlet {
    /**
     * 创建空岛 POST island/{pid}
     * TESTED
     * */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)   {
        super.doPost(req, resp);
            String pid =getPath(req);
            Island island = IslandLogic.createIsland(pid,resp);
            if(island!=null)
                responseSuccess(resp,"成功创建了岛屿.",island);
    }

    /**
     * 获取空岛对象 GET
     * 1. island/{iid or pid}
     * iid{岛ID}  pid{玩家uuid}
     *
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        super.doGet(req, resp);
        //岛id 或者 玩家id
        String id = getPath(req);
        String idType=req.getParameter("idType");
        if(idType==null){
            responseErrorParams(resp,"id格式");
            return;
        }
        Island island = null;
        //如果提供岛id，就根据岛id寻找岛屿对象
        if(idType.equals("iid")){
            island = DaoFactory.getIslandDao().getIslandById(id);
        }else if(idType.equals("pid")){
            //如果提供uuid，就看他有没有空岛
            island = DaoFactory.getIslandDao().getIslandByPlayerUuid(id);
            if(island==null)
                //没有空岛，加入了哪个？
                island = DaoFactory.getIslandDao().getIslandById(DaoFactory.getIslandDao().getIslandIdJoined(id));
        }else{
            responseErrorParams(resp,"");
            return;
        }

        if(island==null)
            responseError(resp,"找不到您的空岛。您可以通过/create指令创建一个，或者让朋友通过/invite指令邀请您。");
        else
            responseSuccess(resp,"成功获取空岛信息。",island);
    }
    /**
     * DELETE
     * 删除空岛 island/{pid}
     * 删除成员 island/{pid}? isOwner={isOwner} & memberId={mid}
     * TESTED
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        super.doDelete(req, resp);
        String pid=getPath(req);
        String mid = req.getParameter("memberId");

        //成员Id为空，进入删除空岛逻辑
        if(StringUtils.isEmpty(mid)){
            if(!IslandLogic.checkHasIsland(pid)){
                responseErrorHasIsland(resp,false);
                return;
            }
            try {
                if (DaoFactory.getIslandDao().delete(pid))
                    responseSuccess(resp,"删除成功");
                else
                    responseErrorHasIsland(resp,false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            //成员id不为空，进入删除成员逻辑
            boolean isOwner = Boolean.parseBoolean(req.getParameter("isOwner"));
            //如果是岛主，则踢出成员
            if(isOwner)
                if(IslandLogic.deleteMember(pid, mid, resp))
                    responseSuccess(resp,"删除成员成功");
                else
                    responseErrorHasIsland(resp,false);
            else
                if(IslandLogic.deleteMember(DaoFactory.getIslandDao().getIslandIdJoined(mid),mid))
                    responseSuccess(resp,"退出成功");
                else
                    responseErrorJoinedIsland(resp,false);
        }

    }

    /**
     * PUT
     * 修改空岛位置 island/{pid}?location={loca}
     * 添加成员 island/{pid}?member={mid}
     *
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        super.doPut(req, resp);
        String pid=getPath(req);
        if(!IslandLogic.checkHasIsland(pid)) {
            responseErrorHasIsland(resp,false);
            return;
        }
        String locas = req.getParameter("location");
        String mid = req.getParameter("member");
        String iid = req.getParameter("iid");
        //mid不为空，iid为空 进入邀请成员逻辑
        if(!StringUtils.isEmpty(mid) && StringUtils.isEmpty(iid)){
            iid = DaoFactory.getIslandDao().getIslandIdByPlayerUuid(pid);
            try {
                if (DaoFactory.getIslandDao().joinOtherIsland(iid,mid))
                    responseSuccess(resp,"添加成员成功!");
                else
                    responseErrorJoinedIsland(resp,true);
            } catch (SQLException e){
                responseErrorJoinedIsland(resp,true);
                log.error(e.getMessage());
            }
            return;

        }else if(!StringUtils.isEmpty(iid) && !StringUtils.isEmpty(mid)){
                //iid不为空，mid不为空 进入加入空岛逻辑
            try {
                if (DaoFactory.getIslandDao().joinOtherIsland(iid,mid))
                    responseSuccess(resp,"成功!");
                else
                    responseErrorJoinedIsland(resp,true);
            } catch (SQLException e){
                responseErrorJoinedIsland(resp,true);
                log.error(e.getMessage());
            }
            return;

        }
        if(locas==null){
            responseError(resp,"参数错误");
            return;
        }
        CoordLocation location;
        try {
            location = CoordLocation.fromString(locas);
        } catch (ArrayIndexOutOfBoundsException e) {
            responseError(resp,"位置格式错误");
            return;
        }
        iid = DaoFactory.getIslandDao().getIslandByPlayerUuid(pid).getIslandId();
        try {
            boolean b = DaoFactory.getIslandDao().sethome(iid,location);
            if(b)
                responseSuccess(resp,"成功修改了您的空岛传送点为"+location.toString());
            else
                responseErrorHasIsland(resp,false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


   /* private boolean createBookmark(IslandBookmark bookmark){
        String iid = bookmark.getIslandId();
        String pid = bookmark.getCreatorPid();
        if(!checkJoinedIsland(pid) || !checkHasIsland(pid)){
            return false;
        }
        boolean b = false;
        try {
            b = DaoFactory.getIslandDao().addBookmark(bookmark);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }*/






}
