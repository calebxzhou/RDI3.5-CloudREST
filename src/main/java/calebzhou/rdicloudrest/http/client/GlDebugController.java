package calebzhou.rdicloudrest.http.client;

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

@Slf4j
@WebServlet("/public/graphicsDebug")
public class GlDebugController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String playerName = req.getParameter("name");
        String info = req.getParameter("obj");

        log.info(playerName);
        log.info(info.substring(0,1500));
        File infoFile = new File(FileConst.loginLogFolder, playerName+"-info.txt");
        FileUtils.writeToFile(infoFile,info);
    }
}
