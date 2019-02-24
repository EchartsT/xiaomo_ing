package com.book.web;

import com.book.domain.KeyWord;
import com.book.service.KeyWordService;
import com.book.util.ApplicationContextHelper;
import com.book.util.FileUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class KeyWordCommand {
    private KeyWordService keyWordService= ApplicationContextHelper.getBean(KeyWordService.class);

    public void getWordsFrequency(String filepath) throws IOException {
        try {
            //执行python脚本———统计词频
            String tmpline;
            String line;

            Process proc;
            String[] args = new String[] {"D:\\python\\anaconda\\setupway\\python","D:\\python\\code\\word_frequency\\StatisticalWordFrequency.py",filepath};
            proc = Runtime.getRuntime().exec(args);

            //使用缓冲流接受程序返回的结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "GBK"));//注意格式
            tmpline = in.readLine();

            System.out.println(tmpline);
            in.close();
            proc.waitFor();

            KeyWord keyWord = new KeyWord();
            line = tmpline.replace("Counter({","").replace("})","").replace("'","");
            Map<String, Object> map = FileUtil.transStringToMap(line, ", ", ": ");
            for (String key : map.keySet()) {
                System.out.println("key:" + key + ", value:" + map.get(key));
                keyWord.setKeywordName(key);
                keyWord.setKeywordNum(Integer.parseInt((String)map.get(key)));
                keyWordService.addKeyWord(keyWord);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
