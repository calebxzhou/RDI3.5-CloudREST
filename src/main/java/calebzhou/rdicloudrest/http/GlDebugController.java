package calebzhou.rdicloudrest.http;

import calebzhou.rdicloudrest.constants.FileConst;
import calebzhou.rdicloudrest.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@WebServlet("/glDebug")
public class GlDebugController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String playerName = req.getParameter("name");
        String info = req.getParameter("obj");
        SimpleDateFormat var10003 = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
        Date var10004 = new Date();
        File infoFile = new File(FileConst.loginLogFolder, playerName+"-" + var10003.format(var10004) + "-info.txt");
        FileUtils.writeToFile(infoFile,info);
    }
}
