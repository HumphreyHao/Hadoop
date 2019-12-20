package com.imooc.bigdata.hadoop.hdfs;

public interface ImoocMapper {
    /*
     * @Author haozhao
     * @Description //TODO
     * @Date  12/20/19
     * @Param [line, context] 每一行上下文和数据
     * @return
     **/
    public void map(String line, ImoocContext context);
}
