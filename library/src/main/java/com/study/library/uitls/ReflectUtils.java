package com.study.library.uitls;

import java.lang.reflect.Field;

/**
 * @authour lxw
 * @function 反射工具类
 * @date 2019/10/14
 */
public class ReflectUtils {

    /**
     *  通过反射获取某对象，并设置私有可访问
     * @param obj 该属性所属类的对象
     * @param clazz 该属性所属类
     * @param field 属性名
     * @return 该属性对象
     */
    private static Object getField(Object obj,Class<?> clazz,String field)
        throws NoSuchFieldException,IllegalAccessException,IllegalArgumentException{
        //getDeclaredField获取当前类的所有修饰符的方法
        Field localField=clazz.getDeclaredField(field);
        localField.setAccessible(true);//设置私有方法可以访问
        return localField.get(obj);
    }

    /**
     * 给某属性赋值，并设置私有可访问
     * @param obj
     * @param clazz
     * @param value
     */
    public static void setField(Object obj,Class<?> clazz,Object value)
            throws NoSuchFieldException,IllegalAccessException,IllegalArgumentException {
        Field localField=clazz.getDeclaredField("dexElements");
        localField.setAccessible(true);
        localField.set(obj,value);
    }

    /**
     *  通过反射获取 baseDexClassLoader 对象中的pathlist对象
     * @param baseDexClassLoader 对象
     * @return pathlist对象
     */
    public static Object getPathList(Object baseDexClassLoader)
            throws NoSuchFieldException,IllegalAccessException,IllegalArgumentException,ClassNotFoundException {
        return getField(baseDexClassLoader,Class.forName("dalvik.system.BaseDexClassLoader"),"pathList");
    }

    /**
     *   通过反射获取 baseDexClassLoader 对象中的pathlist对象 再获取dexElements对象
     * @param paramObject
     * @return
     */
    public static Object getDexElements(Object paramObject)
            throws NoSuchFieldException,IllegalAccessException,IllegalArgumentException {
        return getField(paramObject,paramObject.getClass(),"dexElements");
    }
}
