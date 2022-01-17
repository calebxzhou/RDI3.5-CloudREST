package calebzhou.rdicloudrest.utils;

import calebzhou.rdicloudrest.dao.DatabaseConnector;
import calebzhou.rdicloudrest.model.PlayerHome;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SqlUtils {
    public static <T extends Serializable> List<T> queryAll(Class<T> clazz) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String name = clazz.getSimpleName();
        ResultSet rs = DatabaseConnector.getPreparedStatement("select * from "+name).executeQuery();
        ArrayList<T> list = new ArrayList();
        while(rs.next()){
            T obj = clazz.getConstructor().newInstance();
            for (Field field : ReflectUtils.getDeclaredAccessibleFields(clazz)) {
                field.set(obj, rs.getObject(field.getName()));
            }
            list.add(obj);
        }
        return list;
    }
    public static <T extends Serializable> T initializeObjectByResultSet(ResultSet rs, Class<T> clazz) throws SQLException, IllegalAccessException {
        T objInstance = ReflectUtils.createInstance(clazz);
        for(Field field : ReflectUtils.getDeclaredAccessibleFields(clazz)){
            field.set(objInstance , rs.getObject(field.getName()));
        }
        return objInstance;
    }
    //向表中插入对象
    public static <T extends Serializable> int insertObjectToTable (T objInstance, Class<T> objClass) throws SQLException {
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
            Object value = null;
            try {
                value = fn.get(objInstance);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            pstm.setObject(i + 1, value);
        }
        return pstm.executeUpdate();
    }

    /**
     * @param condition e.g. id=? and , homeName=?
     */

    public static <T extends Serializable> int updateObject(T obj,String[] condition,String... conditionValue) throws SQLException, IllegalAccessException {
        StringBuilder sb = new StringBuilder("UPDATE " + obj.getClass().getSimpleName() + " SET ");
        //全部变量名
        Field[] fields = obj.getClass().getDeclaredFields();
        //变量的数量
        int fieldAmount = fields.length;
        for (int i = 0; i < fieldAmount; i++) {
            Field fn=fields[i];
            fn.setAccessible(true);
            sb.append(fn.getName()+" = ?");
            //结尾不应该有逗号，所以判断减1
            if (i != fieldAmount - 1)
                sb.append(",");

        }
        if(condition != null){
            sb.append(" where ");
            Arrays.stream(condition).forEach(sb::append);
        }
            sb.append(" where "+condition);
        String sql=sb.toString();
        PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(sql);
        for (int i = 0; i < fieldAmount; i++) {
            Field fn = fields[i];
            fn.setAccessible(true);
            Object value = fn.get(obj);
            ps.setObject(i+1,value);
        }
        //最后一个where =?的
        for(int i=0;i<conditionValue.length;i++){
            ps.setObject(fieldAmount+i+1,conditionValue[i]);
        }

        return ps.executeUpdate();

    }


}
