package calebzhou.rdicloudrest.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@Slf4j
public class ResponseUtils {
    public static void write(HttpServletResponse response,Object value){
        log.info("response:"+value);
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();

        } catch (IOException e) {
            e.printStackTrace();
        }
        out.print(value);
    }
}
