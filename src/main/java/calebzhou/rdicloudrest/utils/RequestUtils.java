package calebzhou.rdicloudrest.utils;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.Serial;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
@Slf4j
public class RequestUtils {
    //把http请求转换成java对象
    public static <T extends Serializable> T parseRequstToObject(Class<T> objClass , HttpServletRequest request) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        T objInstance = ReflectUtils.createInstance(objClass);

        for(Field field : ReflectUtils.getDeclaredAccessibleFields(objClass)){
            //变量名
            String fieldName = field.getName();
            //变量类型
            Class<?> fieldTypeClass = field.getType();

            String reqParam = request.getParameter(fieldName);
            //转换成对应类型的对象
            Object fieldCastedValue;
            //如果不是字符串，则使用valueOf进行类型转换
            if(fieldTypeClass != String.class)
                fieldCastedValue = fieldTypeClass.getMethod("valueOf",String.class).invoke(objInstance,reqParam);
            else //如果是字符串，就不转换
                fieldCastedValue = reqParam;
            field.set(objInstance,fieldCastedValue);
        }


        return objInstance;
    }
    //把http请求转换成java对象 json版
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
