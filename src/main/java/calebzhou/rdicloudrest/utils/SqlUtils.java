package calebzhou.rdicloudrest.utils;

import calebzhou.rdicloudrest.model.PlayerHome;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlUtils {
    public static <T extends Serializable> T initializeObjectByResultSet(ResultSet rs, Class<T> clazz) throws SQLException, IllegalAccessException {
        T objInstance = ReflectUtils.createInstance(clazz);
        for(Field field : ReflectUtils.getDeclaredAccessibleFields(clazz)){
            field.set(objInstance , rs.getObject(field.getName()));
        }
        return objInstance;
    }
}
