package com.megvii.ocr.common;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author: kezy
 * @create_time 2019/11/4
 * @description:
 */
public class SerializableHashMap implements Serializable {
    private HashMap<String, byte[]> map;
    private HashMap<String, String> stringMap;

    public SerializableHashMap() {
    }

    public HashMap<String, byte[]> getMap() {
        return this.map;
    }

    public void setMap(HashMap<String, byte[]> map) {
        this.map = map;
    }

    public HashMap<String, String> getStringMap() {
        return this.stringMap;
    }

    public void setStringMap(HashMap<String, String> stringMap) {
        this.stringMap = stringMap;
    }
}
