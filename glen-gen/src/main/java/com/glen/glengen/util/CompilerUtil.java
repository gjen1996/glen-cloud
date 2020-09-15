package com.glen.glengen.util;/**
 * @author Glen
 * @create 2020- 09-2020/9/11-17:00
 * @Description
 */

import com.glen.glencommonsystem.util.R;
import lombok.extern.slf4j.Slf4j;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Glen
 * @create 2020/9/11 17:00
 * @Description 将java文件转为class文件
 */
@Slf4j
public class CompilerUtil {
    public static R compilerFirstTpye(String fireUrl) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        log.info("user.dir"+System.getProperty("user.dir")+"----"+"\\glen-gen\\target\\classes\\com\\glen\\glengen\\entity");
        log.info("fireUrlCompiler:"+fireUrl);
        int status = javac.run(null, null, null, "-d", System.getProperty("user.dir")+"\\glen-gen\\target\\classes",fireUrl);
        return R.ok();
    }

    public static R compilerSecondTpye(String fireUrl) {
        log.info(".class文件转化fireUrl:"+fireUrl);
        try {
            Process process = Runtime.getRuntime().exec("javac -classpath " + fireUrl);
            InputStream errorStream = process.getErrorStream();
            InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            int exitVal = process.waitFor();
            System.out.println("Process exitValue: " + exitVal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok();
    }


}
