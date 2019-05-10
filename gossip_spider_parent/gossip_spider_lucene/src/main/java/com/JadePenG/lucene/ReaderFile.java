package com.JadePenG.lucene;

import java.io.*;

/**
 * @ClassName ReaderFile
 * @Description  读取文件, 写入文件中的数据
 */
public class ReaderFile {
    private static BufferedReader bufferedReader1;
    private static BufferedReader bufferedReader2;

    static {
        try {
            //写入索引库的文件地址
            bufferedReader1 = new BufferedReader(new FileReader(new File("h:/ReaderFile_title.txt")));
            bufferedReader2 = new BufferedReader(new FileReader(new File("h:/ReaderFile_content.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static String nextTitle() throws IOException {
       return bufferedReader1.readLine();
    }
    public static String nextContent() throws IOException {
        return bufferedReader2.readLine();
    }

}
