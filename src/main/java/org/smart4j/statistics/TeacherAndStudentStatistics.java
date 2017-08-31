package org.smart4j.statistics;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.CellType;
import org.smart4j.utils.FileUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导师与学生的统计数据
 * Created by ithink on 17-8-28.
 */
public class TeacherAndStudentStatistics {

    private Map<String, List<String>> teacherToStudentNumberMap = new HashMap<>();
    private Map<String, String> studentNumberToTeacherMap = new HashMap<>();
    private Map<String, String> numberToStudentMap = new HashMap<>();

    /**
     * 获取导师与学生关系映射的统计数据，{key-导师，value-{学生列表}}
     */
    public void setMaps(String teacherToStudentsTableFile) throws IOException{

        // 获取到花名册表文件的sheet
        InputStream fileInputStream = FileUtils.getInputFileStream(teacherToStudentsTableFile);
        HSSFSheet sheet = FileUtils.getSheetOfXLS(fileInputStream, 0);

        for(int i=0; i<=sheet.getLastRowNum(); i++){

            HSSFRow row = sheet.getRow(i);

            HSSFCell studentNumberCell = row.getCell(0);
            HSSFCell studentCell = row.getCell(1);
            HSSFCell teacherCell = row.getCell(4);

            fillTeacherToStudentNumberMap(studentNumberCell, teacherCell);
            fillNumberToStudentMap(studentNumberCell, studentCell);
            fillStudentNumberToTeacherMap(studentNumberCell, teacherCell);
        }

    }

    /**
     * 填充{导师-学生列表}的信息
     * @param studentNumberCell
     * @param teacherCell
     */
    private void fillTeacherToStudentNumberMap(HSSFCell studentNumberCell, HSSFCell teacherCell){

        if(studentNumberCell!=null && teacherCell!=null){

            String studentNumber = String.valueOf(studentNumberCell.getNumericCellValue());
            String teacherName = teacherCell.getStringCellValue().trim();

            if(teacherToStudentNumberMap.containsKey(teacherName)){
                teacherToStudentNumberMap.get(teacherName).add(studentNumber);
            }else{
                List<String> studentNumberList = new ArrayList<>();
                studentNumberList.add(studentNumber);
                teacherToStudentNumberMap.put(teacherName, studentNumberList);
            }
        }
    }

    /**
     * 填充{学生学号，老师}的信息
     */
    private void fillStudentNumberToTeacherMap(HSSFCell studentNumberCell, HSSFCell teacherCell){

        String studentNumber = String.valueOf(studentNumberCell.getNumericCellValue());
        String teacherName = teacherCell.getStringCellValue().trim();

        studentNumberToTeacherMap.put(studentNumber, teacherName);
    }

    /**
     * 填充{学号，学生}信息
     */
    private void fillNumberToStudentMap(HSSFCell studentNumberCell, HSSFCell studentCell){

        String studentNumber = String.valueOf(studentNumberCell.getNumericCellValue());
        String studentName = studentCell.getStringCellValue().trim();

        numberToStudentMap.put(studentNumber, studentName);
    }

    public Map<String, List<String>> getTeacherToStudentNumberMap() {
        return teacherToStudentNumberMap;
    }

    public Map<String, String> getNumberToStudentMap() {
        return numberToStudentMap;
    }

    public Map<String, String> getStudentNumberToTeacherMap() {
        return studentNumberToTeacherMap;
    }
}
