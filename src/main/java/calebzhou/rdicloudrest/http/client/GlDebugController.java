package calebzhou.rdicloudrest.http.client;

import calebzhou.rdicloudrest.constants.FileConst;
import calebzhou.rdicloudrest.utils.FileUtils;
import com.google.gson.GsonBuilder;
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@WebServlet("/public/graphicsDebug")
public class GlDebugController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String playerName = req.getParameter("name");
        String info = req.getParameter("obj");

        log.info(playerName);
        log.info(info.substring(0,1500));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
        Date date = new Date();
        File infoFile = new File(FileConst.loginLogFolder, playerName+"-" + format.format(date) + "-info.txt");
        FileUtils.writeToFile(infoFile,info);
    }
}
