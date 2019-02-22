package com.book.util;

import com.book.domain.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileUtil {
    public static File createDirectory() {
        File directory = new File(".");
        String path = null;
        try {
            path = directory.getCanonicalPath();//获取当前路径
            System.out.println(path);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        path +="/chatData";
        File file =new File(path);
//        System.out.println(path);
        //如果文件夹不存在则创建
        if  (!file .exists()  && !file .isDirectory())
        {
//            System.out.println("聊天文件目录不存在");
            file .mkdir();
        }
        return file;
    }

    public static void writeUserFile(String userInfo){
        try {
            String fileName = createDirectory()+"/用户信息列表文件.txt";
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            userInfo+="\n";
            byte[] data = userInfo.getBytes("utf-8");
            randomFile.write(data);
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(String openId,String userContent,String robotContent) {
        try {
            String fileName = createDirectory()+"/"+openId+"/.txt";
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            userContent = openId+"--->"+userContent+"\n";
            robotContent = "robot--->"+robotContent+"\n";
            byte[] data1 = userContent.getBytes("utf-8");
            randomFile.write(data1);
            byte[] data2 = robotContent.getBytes("utf-8");
            randomFile.write(data2);
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
