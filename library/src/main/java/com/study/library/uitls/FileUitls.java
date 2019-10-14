package com.study.library.uitls;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @authour lxw
 * @function
 * @date 2019/10/14
 */
public class FileUitls {

    /**
     * 复制文件
     * @param sourceFile 源文件
     * @param TargetFile 目标文件
     * @throws IOException io异常
     */
    public static void copyFile(File sourceFile,File targetFile) throws IOException {
        //新建文件输入流并对他进行缓冲
        FileInputStream input=new FileInputStream(sourceFile);
        BufferedInputStream inBuff=new BufferedInputStream(input);

        //新建文件输出流并对他进行缓冲
        FileOutputStream output=new FileOutputStream(targetFile);
        BufferedOutputStream outBuff=new BufferedOutputStream(output);

        //缓冲数组
        byte[] b=new byte[1024*5];
        int len;
        while ((len=inBuff.read(b))!=-1){
            //写入
            outBuff.write(b,0,len);
        }
        //刷新次缓冲的输出流
        outBuff.flush();
        //关闭流
        inBuff.close();
        outBuff.close();
        output.close();
        input.close();
    }
}
