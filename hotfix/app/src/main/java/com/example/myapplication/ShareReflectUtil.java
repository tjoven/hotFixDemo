package com.example.myapplication;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ShareReflectUtil {

    public static Field getField(Object object,String name) throws Exception {
        Class clz = object.getClass();
        while (clz != Object.class){
            try {
                Field field = clz.getDeclaredField(name);
                if(field != null){
                    field.setAccessible(true);
                    System.out.println("tzw findField "+name);
                    return field;
                }
            } catch (NoSuchFieldException e) {

            }
            clz = clz.getSuperclass();

        }
        throw new NoSuchFieldException("not find field"+name);
    }

    public static Method getMethod(Object object,String name, Class<?>... parameterTypes) throws Exception {
        System.out.println("tzw findMethod Object "+object.getClass().getName());
        Class clz = object.getClass();
        while (clz != Object.class){
            try {
                Method method = clz.getDeclaredMethod(name,parameterTypes);
                if(method != null){
                    method.setAccessible(true);
                    System.out.println("tzw findMethod "+name);
                    return method;
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            clz = clz.getSuperclass();

        }
        throw new NoSuchMethodException("not find Method"+name);
    }
}
