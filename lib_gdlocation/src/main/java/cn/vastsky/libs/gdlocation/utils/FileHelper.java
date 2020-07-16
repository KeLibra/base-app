package cn.vastsky.libs.gdlocation.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import cn.vastsky.lib.base.config.AppConfigLib;
import cn.vastsky.lib.base.utils.JsonUtils;
import cn.vastsky.lib.base.utils.LogUtils;

/**
 * @author: kezy
 * @create_time 2019/11/7
 * @description:
 */
public class FileHelper {
    private static final String TAG = "FileHelper";

    public static final String BP_DOWNLOAD_PATH = "bpdownload"; //断点续传下载的根目录
    public static final String PACKAGES_PATH = "packages"; //当前离线文件根目录
    public static final String PACKAGES_NEXT_PATH = "packages-next"; //新版下载离线文件根目录


    /**
     * 根据url 获取文件名
     *
     * @param url
     * @return
     */
    public static String getFileName(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        int index = url.lastIndexOf("/");

        int paramIndex = url.indexOf("?");

        if (index < 0 || index >= url.length()) {
            return null;
        } else {
            if (paramIndex < 0 || paramIndex >= url.length()) {
                return url.substring(index + 1);
            } else {
                return url.substring(index + 1, paramIndex);
            }
        }
    }

    /**
     * @return 断点续传下载根目录
     */
    public static String getBpDownloadRoot() {
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(AppConfigLib.getContext().getFilesDir()))
                .append(File.separator)
                .append(FileHelper.BP_DOWNLOAD_PATH)
                .append(File.separator);
        return stringBuilder.toString();
    }


    /**
     * 文件的绝对路径
     *
     * @param fileName
     * @return
     */
    public static String getFileAbsolutePath(String fileName) {
        return getBpDownloadRoot() + fileName;
    }

    /**
     * 根据下载的url 获取文件的下载绝对路径
     *
     * @param url
     * @return
     */
    public static String getDownloadFilePath(String url) {
        String fileName = FileHelper.getFileName(url);
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }
        return FileHelper.getFileAbsolutePath(fileName);
    }

    /**
     * 文件是否存在
     *
     * @param path
     * @return
     */
    public static boolean isFileExists(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }

        File file = new File(path);
        return file.exists();
    }

    /**
     * 从文件目录，读取字符串数据流，转成object
     *
     * @param fileName
     * @param objextClass
     * @param <T>
     * @return
     */
    public static <T> T getObjectFromFile(String fileName, Class<T> objextClass) {
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }

        try {
            InputStream file = new FileInputStream(fileName);

            BufferedReader reader = new BufferedReader(new InputStreamReader(file));

            String line;
            StringBuilder result = new StringBuilder();
            do {
                line = reader.readLine();
                if (!TextUtils.isEmpty(line)) {
                    result.append(line);
                }
            } while (!TextUtils.isEmpty(line));

            T object = JsonUtils.decode(result.toString(), objextClass);

            return object;

        } catch (FileNotFoundException e) {
            LogUtils.e(TAG, "FileNotFoundException: " + e.toString());
        } catch (IOException e) {
            LogUtils.e(TAG, "IOException" + e.toString());
        } catch (RuntimeException e) {
            LogUtils.e(TAG, "RuntimeException" + e.toString());
        }

        return null;
    }

    /**
     * 从asset 目录下面，获取对象
     *
     * @param assetPath
     * @param objectClass
     * @param context
     * @param <T>
     * @return
     */
    public static <T> T getObjectFromAsset(String assetPath, Class<T> objectClass, Context context) {
        if (TextUtils.isEmpty(assetPath)) {
            return null;
        }

        AssetManager assetManager = context.getAssets();


        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open(assetPath)));

            String line;
            StringBuilder result = new StringBuilder();
            do {
                line = reader.readLine();
                if (!TextUtils.isEmpty(line) && !line.matches("^\\s*\\/\\/.*")) {
                    result.append(line);
                }
            } while (!TextUtils.isEmpty(line));

            T object = JsonUtils.decode(result.toString(), objectClass);

            return object;

        } catch (IOException e) {
            LogUtils.d(TAG, "asset io error");
        } catch (RuntimeException e) {
            LogUtils.d(TAG, "asset json decode error");
        }
        return null;
    }

    /**
     * 保存文件到指定目录
     *
     * @param content
     * @param path
     * @param fileName
     * @return
     */
    public static boolean saveFileToPath(String content, String path, String fileName) {
        if (TextUtils.isEmpty(path) || TextUtils.isEmpty(content)) {
            return false;
        }

        File parentDir = new File(path);
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }


        File file = new File(path, fileName);
        if (file.exists()) {
            file.delete();
        }
        FileWriter fileWriter = null;

        try {
            file.createNewFile();
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            LogUtils.e(TAG, "IOException: " + e.toString());
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
            }
        }
        return false;
    }

    /**
     * 从Assets中获取Zip文件的文件名
     *
     * @param context
     * @return 文件名list
     */
    public static List<String> getZipFileNameListFromAssets(Context context) {
        List<String> list = new ArrayList<>();
        AssetManager assets = context.getAssets();
        try {
            String[] assetsList = assets.list("");
            if (assetsList.length > 0) {
                for (String filename : assetsList) {
                    if (filename.endsWith("zip")) {
                        list.add(filename);
                    }
                }
            }
        } catch (IOException e) {
        }
        return list;
    }

    /**
     * 根据文件名，从Assets中将Zip文件拷贝到File目录下
     *
     * @param context
     * @param fileName
     * @return Zip文件目录
     */
    public static String copyZipFileFromAssets(Context context, String fileName) {
        AssetManager assets = context.getAssets();
        if (fileName == null || fileName.equals("")) {
            return "";
        }
        File src = new File(context.getFilesDir(), fileName);
        String path = "";
        // 如果旧的ZIP包存在，删除
        if (src.exists()) {
            src.delete();
        }
        try {
            src.createNewFile();
            InputStream open = assets.open(fileName);
            if (inputStreamToFile(open, src)) {
                path = src.getPath();
                LogUtils.d("copyZipFileFromAssets-- path: " + path);
            }
        } catch (IOException e) {
            LogUtils.e("error -- IOException: " + e.toString());
        }
        return path;
    }

    /**
     * io流转换成file
     *
     * @param ins
     * @param file
     * @return
     */
    private static boolean inputStreamToFile(InputStream ins, File file) {
        OutputStream os = null;
        boolean result = false;
        try {
            if (file != null && file.exists()) {
                os = new FileOutputStream(file);
                int bytesRead;
                byte[] buffer = new byte[8192];
                while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                result = true;
            }
        } catch (IOException e) {
            LogUtils.e("error -- IOException: " + e.toString());
        } finally {
            try {
                if (os != null)
                    os.close();
                ins.close();
            } catch (IOException e) {
            }
        }
        LogUtils.d("inputStreamToFile-- result: " + result);
        return result;
    }


    /**
     * 拷贝目录到指定目录
     *
     * @param source
     * @param dest
     * @return
     */
    public static boolean copyDirToDir(String source, String dest) {

        if (TextUtils.isEmpty(source) || TextUtils.isEmpty(dest)) {
            return false;
        }

        File file = new File(source);
        if (!file.exists()) {
            return false;
        }
        String[] subFileList = file.list();

        File destFile = new File(dest);
        if (!destFile.exists()) {
            destFile.mkdirs();
        }

        for (String subFilePath : subFileList) {
            if (TextUtils.isEmpty(subFilePath)) {
                continue;
            }

            String sourcePath = getSubFilePath(source, subFilePath);
            String destPath = getSubFilePath(dest, subFilePath);

            File subFile = new File(sourcePath);

            if (subFile.isDirectory()) {
                copyDirToDir(sourcePath, destPath);
            }

            if (subFile.isFile()) {
                boolean copyFile = copyFileToFile(sourcePath, destPath);
                if (!copyFile) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 拷贝文件到指定目录
     *
     * @param source
     * @param dest
     * @return
     */
    public static boolean copyFileToFile(String source, String dest) {

//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            Path sourcePath = Paths.get(source);
//            Path destPath = Paths.get(dest);
//
//            try {
//                Files.copy(sourcePath, destPath);
//                return true;
//            } catch (IOException e) {
//                LogUtils.e(TAG, "copy file error");
//            }
//
//        } else {
        FileChannel input = null;
        FileChannel output = null;
        try {
            input = new FileInputStream(new File(source)).getChannel();
            output = new FileOutputStream(dest).getChannel();
            output.transferFrom(input, 0, input.size());
            return true;

        } catch (FileNotFoundException e) {
            LogUtils.e(TAG, "copy file error: " + e.toString());
        } catch (IOException e) {
            LogUtils.e(TAG, "copy file error: " + e.toString());
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.close();
                }

            } catch (Exception e) {

            }
        }
//        }

        return false;
    }

    /**
     * 删除文件
     *
     * @param path
     * @return
     */
    public static boolean deleteFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return true;
        }
        File file = new File(path);
        if (file.isDirectory()) {
            deleteDirectory(file);
        } else if (file.isFile()) {
            boolean deleteFile = file.delete();
            if (!deleteFile) {
                return false;
            }
        }
        return true;
    }

    /**
     * delete Directory
     *
     * @param dir
     */
    private static void deleteDirectory(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            return;
        }
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                file.delete(); // 删除所有文件
            } else if (file.isDirectory()) {
                deleteDirectory(file); // 递规的方式删除文件夹
            }
        }
        dir.delete();// 删除目录本身
    }

    /**
     * rename src file to des file
     *
     * @param src
     * @param des
     * @return false if path is null or src file not exists
     */
    public static boolean renameFile(String src, String des) {
        if (src == null || des == null) {
            return false;
        }
        File srcFile = new File(src);
        if (!srcFile.exists()) {
            return false;
        }
        File desFile = new File(des);
        return srcFile.renameTo(desFile);
    }

    /**
     * 拼接两个目录
     *
     * @param parent
     * @param sub
     * @return parent/sub
     */
    public static String getSubFilePath(String parent, String sub) {
        StringBuilder result = new StringBuilder(parent);
        if (!parent.endsWith(File.separator)) {
            result.append(File.separator);
        }
        result.append(sub);
        return result.toString();
    }
}
