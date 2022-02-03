package calebzhou.rdicloudrest.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ReflectUtils {
    public static Field[] getDeclaredAccessibleFields(Class clazz){
        ArrayList<Field> fields=new ArrayList<>();
        for(Field field:clazz.getDeclaredFields()){
            field.setAccessible(true);
            fields.add(field);
        }
        return fields.toArray(new Field[0]);
    }
    public static <T> T createInstance(Class<T> clazz){
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
