package com.neo.base.util;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class FileUtil {

    /**
     * 创建目录
     * @param path
     * @return
     */
    public static String createPath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        return file.getAbsolutePath();
    }

    /**
     *获取存储路径
     * @return
     */
    public static String getPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }else {
            sdDir =YHContext.getInstance().getContext().getCacheDir();
        }
        return sdDir.toString();

    }

    /** 根据路径删除文件 */
    public static boolean deleteFile(String path) {
        if (TextUtils.isEmpty(path)) return true;
        File file = new File(path);
        if (!file.exists()) return true;
        if (file.isFile()) return file.delete();
        return false;
    }

    /**
     * 删除文件
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }
            for (int i = 0; i < childFiles.length; i++) {
                deleteFile(childFiles[i]);
            }
            file.delete();
        }
    }

    /**
     * 写入文件
     *
     * @param in
     * @param file
     */
    public static void writeFile(InputStream in, File file) throws IOException {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        if (file != null && file.exists())
            file.delete();



        FileOutputStream out = new FileOutputStream(file);
        byte[] buffer = new byte[1024 * 10 * 5];
        int len = -1;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        out.flush();
        out.close();
        in.close();

    }
    public static void renameFile(String n1, String n2) {
        File file = new File(n1);
        File f = new File(n2);
        file.renameTo(f);
    }


    public static File getEpubName(String name, String suffix) {
        return new File(getPath() +"/longyuan/epub/" +name +"/"+ name + "." + suffix);
    }


    public static File getImgCache() {
        File file = new File(getPath() + "/longyuan/img/");
        if(!file.exists()){
            file.mkdirs();
        }
        return file;
    }

    public static File getAppPath(){
        File file = new File(getPath() + "/longyuan/app/");
        if(!file.exists()){
            file.mkdirs();
        }
        return file;
    }



}
