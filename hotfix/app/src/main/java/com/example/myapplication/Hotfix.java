package com.example.myapplication;

import android.content.Context;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hotfix {


//    获取到当前应用的PathClassloader;
//
//    反射获取到DexPathList属性对象pathList;
//
//    反射修改pathList的dexElements
//      把补丁包patch.dex转化为Element[]  (patch)
//      获得pathList的dexElements属性（old）
//      patch+old合并，并反射赋值给pathList的dexElements

    public static void fix(Context context, File path){
        //1,find pathClassLoader
        ClassLoader loader = context.getClassLoader();
        //2,find 待修复的 pathList
        Field pathListField = null;
        File  optimizedDirector =  context.getExternalFilesDir("fix");
        try {
            pathListField = ShareReflectUtil.getField(loader,"pathList");
            Object dexPathList = pathListField.get(loader);
        //3 修改 elements
            //3.1，path.jar 的 element
            ArrayList<File> list = new ArrayList();
            list.add(path);
            Method  makeDexElementsMethod = ShareReflectUtil.getMethod(dexPathList,"makeDexElements", List.class,File.class,List.class,ClassLoader.class);
            Object[] pathElements = (Object[]) makeDexElementsMethod.invoke(null,list, optimizedDirector,new ArrayList<>(),loader);
            //3.2, 待修复的elements
            Field elementsField = ShareReflectUtil.getField(dexPathList,"dexElements");
            Object[] dexElements = (Object[]) elementsField.get(dexPathList);
            //3.3，修改 elements
            Object[] finalElements = (Object[]) Array.newInstance(pathElements.getClass().getComponentType(),pathElements.length+dexElements.length);
            System.arraycopy(pathElements,0,finalElements,0,pathElements.length);
            System.arraycopy(dexElements,0,finalElements,pathElements.length,dexElements.length);
            elementsField.set(dexPathList,finalElements);

            System.out.println("pathElements "+pathElements.length);
            System.out.println("dexElements "+dexElements.length);
            System.out.println("finalElements "+finalElements.length);
            for (Object o:finalElements){

                System.out.println(o.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
