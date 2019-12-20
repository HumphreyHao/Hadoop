package com.imooc.bigdata.hadoop.hdfs;

import java.util.HashMap;
import java.util.Map;

/*
 * @Author haozhao
 * @Description 缓存
 * @Date  12/20/19
 * @Param
 * @return
 **/
public class ImoocContext {
    private Map<Object,Object> cacheMap = new HashMap<>();
    public void write(Object key, Object value){
        cacheMap.put(key,value);
    }
    public Object  get(Object key){
        return cacheMap.get(key);
    }

    public Map<Object,Object> getCacheMap(){
        return cacheMap;
    }
}
