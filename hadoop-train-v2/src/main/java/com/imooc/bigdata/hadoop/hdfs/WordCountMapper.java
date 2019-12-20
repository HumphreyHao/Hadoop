package com.imooc.bigdata.hadoop.hdfs;

/*
 * @Author haozhao
 * @Description //TODO 自定义实现
 * @Date  12/20/19
 * @Param
 * @return
 **/
public class WordCountMapper implements ImoocMapper{

    @Override
    public void map(String line, ImoocContext context) {
        String[] words = line.split(" ");

        for(String word: words){
            Object value = context.get(word);
            if (value == null){
                context.write(word,1);
            }else {
                int v = Integer.parseInt(value.toString());
                context.write(word,v+1);
            }
        }

    }
}
