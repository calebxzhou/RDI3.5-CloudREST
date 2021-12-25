package calebzhou.rdicloudrest.utils;

import calebzhou.rdicloudrest.dao.DatabaseConnector;
import calebzhou.rdicloudrest.model.PlayerHome;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
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
    //向表中插入对象
    public static <T extends Serializable> int insertObjectToTable (T objInstance, Class<T> objClass) throws SQLException, IllegalAccessException {
        //全部变量名
        Field[] fields = ReflectUtils.getDeclaredAccessibleFields(objClass);
        String tableName = objClass.getSimpleName();
        //变量的数量
        int fieldAmount = fields.length;
        StringBuilder sb = new StringBuilder("INSERT INTO " + tableName + "  VALUES (");
        for (int i = 0; i < fieldAmount; i++) {
            sb.append("?");
            //结尾不应该有逗号，所以判断减1
            if (i != fieldAmount - 1)
                sb.append(",");

        }
        sb.append(")");
        String sql = sb.toString();
        PreparedStatement pstm = DatabaseConnector.getConnection().prepareStatement(sql);
        for (int i = 0; i < fieldAmount; i++) {
            Field fn = fields[i];
            Object value = fn.get(objInstance);
            pstm.setObject(i + 1, value);
        }
        return pstm.executeUpdate();
    }
}
