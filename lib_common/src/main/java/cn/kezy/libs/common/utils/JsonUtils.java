package cn.kezy.libs.common.utils; /**
 * Copyright (C) 2006-2014 Tuniu All rights reserved
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JSON字符换和对象间转换的工具类
 */
public class JsonUtils {

    //不用创建对象,直接使用Gson.就可以调用方法
    private static Gson gson = null;

    //判断gson对象是否存在了,不存在则创建对象
    static {
        if (gson == null) {
            //gson = new Gson();
            //当使用GsonBuilder方式时属性为空的时候输出来的json字符串是有键值key的,显示形式是"key":null，而直接new出来的就没有"key":null的
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        }
    }

    //无参的私有构造方法
    private JsonUtils() {
    }

    /**
     * 将对象转成json格式
     *
     * @param object
     * @return String
     */
    public static String obj2JsonStr(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    /**
     * 将json转成特定的cls的对象
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T jsonStr2JsonObj(String gsonString, Class<T> cls) {
        try {
            T t = null;
            if (gson != null) {
                //传入json对象和对象类型,将json转成对象
                t = gson.fromJson(gsonString, cls);
            }
            return t;
        } catch (Exception e) {
            LogUtils.e("------json err: " + e.toString());
        }
        return null;
    }

    /**
     *
     * @param object
     * @param typeOfT
     * @param <T>
     * @return
     * @throws RuntimeException
     */
    public static <T> T decode(Object object, Type typeOfT) {
        String jsonString = gson.toJson(object);
        return gson.fromJson(jsonString, typeOfT);
    }


    /**
     * json字符串转成list
     *
     * @param cls
     * @return
     */
    public static <T> List<T> jsonStr2List(String jsonString,Class<T> cls){
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            JsonArray arry = new JsonParser().parse(jsonString).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                list.add(gson.fromJson(jsonElement, cls));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * json字符串转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> jsonStr2ListMap(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * json字符串转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> jsonStr2Map(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }
}

