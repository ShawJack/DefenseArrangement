package org.smart4j.statistics;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.smart4j.utils.FileUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 参与答辩的老师的统计数据
 * Created by ithink on 17-8-28.
 */
public class TeacherStatistics {

    private Map<String, Boolean> teacherMap = new HashMap<>();

    /**
     * 读取参与答辩专家的信息表文件，{老师，是否可担任组长}
     */
    public void setTeacherMap(String teacherTableFile) throws IOException{

        InputStream teacherFileInputStream = FileUtils.getInputFileStream(teacherTableFile);
        HSSFSheet sheet = FileUtils.getSheetOfXLS(teacherFileInputStream, 0);

        for(int i=1; i<=sheet.getLastRowNum(); i++){
            HSSFRow row = sheet.getRow(i);

            HSSFCell teacherCell = row.getCell(0);
            HSSFCell isChairCell = row.getCell(1);

            if(teacherCell!=null){
                String teacherName = teacherCell.getStringCellValue().trim();
                Boolean isChair = isChairCell!=null
                        && isChairCell.getStringCellValue().trim().equals("是");

                teacherMap.put(teacherName, isChair);
            }
        }
    }

    public Map<String, Boolean> getTeacherMap() {
        return teacherMap;
    }
}
