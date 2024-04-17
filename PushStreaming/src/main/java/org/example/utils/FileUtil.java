package org.example.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Slf4j
public class FileUtil {

    /**
     * 壓縮相關
     */
    public static final String zipExt = ".zip";

    public static final String txtRar = ".rar";

    /**
     * 文字相關
     */
    public static final String txtExt = ".txt";


    /**
     * 圖片相關
     */
    public static final String txtJpg = ".jpg";

    public static final String txtJpeg = ".jpeg";

    public static final String txtPng = ".png";

    /**
     * 影片相關
     */
    public static final String txtMp4 = ".mp4";

    public static final List<String> supportedCompZip =
            Collections.unmodifiableList(Arrays.asList(zipExt));

    public static final List<String> supportedComImages =
            Collections.unmodifiableList(Arrays.asList(txtJpg,txtPng,txtJpeg));

    public static final List<String> supportedComVideo =
            Collections.unmodifiableList(Arrays.asList(txtMp4));


    /**
     * 查该路迳是否有该档案
     */

    public static boolean CheckFilePath(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否为档案 非路迳
     */
    public static boolean isFileExists(File file) {
        return file.exists() && !file.isDirectory();
    }


    /** 取得檔案类類型(副檔名)
     * fileName 檔案名稱
     * */
    public static String getExtByFileName(String fileName) {
        String extension = "";

        if (fileName == null || fileName.isEmpty()) {
            return extension;
        }

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i);
        }

        return extension;
    }


    /** 刪除檔案
     * directory 檔案路徑
     * oldTime 要刪除的時間(離當前時間多久)
     * chronoUnit 時間單位
     * */
    public static void deleteOldFiles(File directory, int oldTime, ChronoUnit chronoUnit) {
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isFile()) {
                long lastModifiedTime = file.lastModified();
                Instant lastModifiedDate = Instant.ofEpochMilli(lastModifiedTime);
                Instant oldInstant = Instant.now().minus(oldTime, chronoUnit);
                if (lastModifiedDate.isBefore(oldInstant)) {
                    boolean delete = file.delete();
                    if (delete) {
                        log.info("已刪除文件：{}",file.getAbsolutePath());
                    } else {
                        log.info("無法刪除文件：{}",file.getAbsolutePath());
                    }
                }
            } else if (file.isDirectory()) {
                deleteOldFiles(file, oldTime, chronoUnit);
            }
        }
    }


    /** 刪除路徑(最外層的路徑會留下)
     * directory 檔案路徑
     * */
    public static void deleteEmptyDirectories(File directory) {

        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                deleteEmptyDirectories(file);
                if (file.isDirectory() && file.listFiles().length == 0) {
                    boolean deleted = file.delete();
                    if (deleted) {
                        log.info("已刪除空目錄：{}",file.getAbsolutePath());
                    } else {
                        log.info("無法刪除空目錄：{}",file.getAbsolutePath());
                    }
                }
            }
        }
    }
}
