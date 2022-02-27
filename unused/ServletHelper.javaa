package calebzhou.rdicloudrest.utils;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class ServletHelper {
    public static String getPathAction(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();

        String path = requestURI.replaceAll(contextPath, "").replaceAll(servletPath, "");
        // array length must bigger than 1
        // sample path="/test/"
        String action = "";
        String[] paths = path.split("/");
        if (paths.length > 1) {
            action = paths[1];
        }

        return action;
    }

    public static void invokeMethod(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String methodName = ServletHelper.getPathAction(request);
        ServletHelper.invokeMethod(methodName, servlet, request, response);
    }

    public static void invokeMethod(String methodName, HttpServlet servlet, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        Class<?> cls = servlet.getClass();
        Method[] methods = cls.getMethods();
        for (Method method : methods) {
            if (Modifier.isPublic(method.getModifiers())
                    && method.getName().toLowerCase().equals(methodName.toLowerCase())) {
                method.invoke(servlet, request, response);
                break;
            }
        }
    }
}
