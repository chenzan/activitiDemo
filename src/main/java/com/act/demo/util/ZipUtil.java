package com.act.demo.util;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

public class ZipUtil {
    /**
     * 压缩文件含文件夹
     *
     * @param sourceFile
     * @param targetFile
     * @return
     */
    public static boolean compress(String sourceFile, String targetFile) {
        try {
            File sFile = new File(sourceFile);
            return sFile.isDirectory() ? compressDirectory(sourceFile, targetFile) : compressFile(sourceFile, targetFile);
        } catch (Exception var3) {
            return false;
        }
    }

    /**
     * 压缩文件
     *
     * @param sourceFile
     * @param targetFile
     * @return
     */
    public static boolean compressFile(String sourceFile, String targetFile) {
        Project prj = new Project();
        Zip zip = new Zip();
        zip.setProject(prj);
        zip.setDestFile(new File(targetFile));
        FileSet fileSet = new FileSet();
        fileSet.setProject(prj);
        fileSet.setFile(new File(sourceFile));
        zip.addFileset(fileSet);
        zip.execute();
        return true;
    }

    /**
     * 压缩目录
     *
     * @param sourceFile
     * @param targetFile
     * @return
     */
    public static boolean compressDirectory(String sourceFile, String targetFile) {
        Project prj = new Project();
        Zip zip = new Zip();
        zip.setProject(prj);
        zip.setDestFile(new File(targetFile));
        FileSet fileSet = new FileSet();
        fileSet.setProject(prj);
        fileSet.setDir(new File(sourceFile));
        zip.addFileset(fileSet);
        zip.execute();
        return true;
    }

    /**
     * 解药
     *
     * @param zFile
     * @return
     */
    public static boolean unzip(File zFile) {
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(zFile);
            Enumeration entries = zipFile.getEntries();

            while (true) {
                while (entries.hasMoreElements()) {
                    ZipEntry entry = (ZipEntry) entries.nextElement();
                    File file = new File(zFile.getParent() + "/" + entry.getName());
                    if (entry.isDirectory()) {
                        file.mkdirs();
                    } else {
                        File parent = file.getParentFile();
                        if (!parent.exists()) {
                            parent.mkdirs();
                        }
                        InputStream inputStream = zipFile.getInputStream(entry);
                        FileOutputStream fileOut = new FileOutputStream(file);
                        byte[] buf = new byte[1024];

                        int readedBytes;
                        while ((readedBytes = inputStream.read(buf)) > 0) {
                            fileOut.write(buf, 0, readedBytes);
                        }
                        fileOut.close();
                        inputStream.close();
                    }
                }
                zipFile.close();
                return true;
            }
        } catch (IOException var10) {
            var10.printStackTrace();
            return false;
        }
    }

    /**
     * 解压
     *
     * @param zipFilePath
     * @return
     */
    public static boolean unzip(String zipFilePath) {
        return unzip(new File(zipFilePath));
    }
}
