package com.itheima.lucene;

import java.io.*;

/**
 * @ClassName ReaderFile
 * @Description TODO
 */
public class ReaderFile {
    private static BufferedReader bufferedReader1;
    private static BufferedReader bufferedReader2;

    static {
        try {
            bufferedReader1 = new BufferedReader(new FileReader(new File("d:/temp/title.txt")));
            bufferedReader2 = new BufferedReader(new FileReader(new File("d:/temp/content.txt")));
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
