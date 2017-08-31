package org.smart4j.utils;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by ithink on 17-8-28.
 */
public class FileUtils {

    /**
     * 获取文件的输入流
     */
    public static InputStream getInputFileStream(String filename) throws IOException{

        Path filePath = Paths.get(filename);

        return Files.newInputStream(filePath);
    }

    /**
     * 获取文件的输出流
     */
    public static OutputStream getOutputStream(String filename) throws IOException{

        Path filePath = Paths.get(filename);

        return Files.newOutputStream(filePath);
    }

    /**
     * 读取*.xls文件
     */
    public static HSSFSheet getSheetOfXLS(InputStream fileInputStream, int sheetAt) throws IOException{

        HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);

        HSSFSheet sheet = workbook.getSheetAt(sheetAt);

        return sheet;
    }

    /**
     * 写入*.xls文件
     */
    public static void writeToXLS(OutputStream fileOutputStream, HSSFWorkbook workbook) throws IOException{

        workbook.write(fileOutputStream);

        fileOutputStream.close();

    }

    /**
     * 获取*.xls文件的过滤器
     */
    public static XLSFileFilter getXLSFileFliter(){
        return XLSFileFilter.getInstance();
    }

    private static class XLSFileFilter extends FileFilter{

        private XLSFileFilter(){}

        private static class FileFliterHolder{
            static XLSFileFilter xlsFileFilter = new XLSFileFilter();
        }

        public static XLSFileFilter getInstance(){
            return FileFliterHolder.xlsFileFilter;
        }

        @Override
        public boolean accept(File file) {
            String name = file.getName();
            return file.isDirectory() || name.toLowerCase().endsWith(".xls");
        }

        @Override
        public String getDescription() {
            return "*.xls";
        }
    }
}
