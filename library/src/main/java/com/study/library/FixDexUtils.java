package com.study.library;

import android.content.Context;

import com.study.library.uitls.ArrayUtils;
import com.study.library.uitls.Constants;
import com.study.library.uitls.ReflectUtils;

import java.io.File;
import java.util.HashSet;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * @authour lxw
 * @function 修复工具类（核心类）
 * @date 2019/10/14
 */
public class FixDexUtils {

    //有可能有多个修复包同事修复
    private static HashSet<File> loadeDex = new HashSet<>();

    static {
        //修复前清理下集合
        loadeDex.clear();
    }

    public static void loadFixeDex(Context context) {
        File fileDir = context.getDir(Constants.DEX_DIR, Context.MODE_PRIVATE);
        //拿到私有目录下的所有文件
        File[] files = fileDir.listFiles();
        //找到筛选后的dex(修复包)加入loadeDex集合
        for (File file : files) {
            //文件名字以 .dex 结束  并且 不是主包
            if (file.getName().endsWith(Constants.DEX_SUFFIX) &&
                    !"classes.dex".equalsIgnoreCase(file.getName())) {
                //加入
                loadeDex.add(file);
            }
        }
        //创建类加载器
        createDexClassLoader(context, fileDir);
    }

    private static void createDexClassLoader(Context context, File fileDir) {
        //创建了一个odex目录，将dex解压 ...class文件
        String optimizeDir = fileDir.getAbsolutePath() + File.separator + "opt_dex";
        File fopt = new File(optimizeDir);
        if (!fopt.exists()) {
            //创建多级目录
            fopt.mkdirs();
        }
        for (File dex : loadeDex) {
            //没发现一个修复包，创建一次类加载器(自有的)
            DexClassLoader classLoader = new DexClassLoader(dex.getAbsolutePath()
                    , optimizeDir, null, context.getClassLoader());
            //每循环一次，修复一次
            hotfix(classLoader, context);
        }

    }

    private static void hotfix(DexClassLoader classLoader, Context context) {
        //获取系统的pathClassloader（类加载器）
        PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();

        try {
            //获取自有的dexElements数组
            Object myElements = ReflectUtils.getDexElements(ReflectUtils.getPathList(classLoader));
            //获取系统的dexElements数组
            Object systemElements= ReflectUtils.getDexElements(ReflectUtils.getPathList(pathClassLoader));
            //合并后，生成新的dexElements数组
            Object dexElements= ArrayUtils.combineArray(myElements,systemElements);
            //获取系统的pathList属性
            Object systemPathList=ReflectUtils.getPathList(pathClassLoader);
            //将新的dexElements数组，通过反射技术，给系统的pathList属性重新赋值
            ReflectUtils.setField(systemPathList,systemPathList.getClass(),dexElements);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
