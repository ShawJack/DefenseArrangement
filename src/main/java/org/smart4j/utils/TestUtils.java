package org.smart4j.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.smart4j.ConfigConstant;
import org.smart4j.DefenseArrangement;
import org.smart4j.statistics.FreeTimeStatistics;
import org.smart4j.statistics.TeacherAndStudentStatistics;
import org.smart4j.statistics.TeacherStatistics;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ithink on 17-8-28.
 */
public class TestUtils {

    /**
     * 生成teachers.xls文件
     * @throws IOException
     */
    @Test
    public void generateTeacherTableFile() throws IOException{
        String root = "/home/ithink/java/DefenseArrangement/src/main/resources/";

        File file = new File(root + "subjectTables");
        String[] files = file.list();
        for(int i=0; i<files.length; i++){
            files[i] = files[i].substring(0, files[i].lastIndexOf("."));
        }

        Path teacherFile = Paths.get(root + "teachers.xls");
        if(!Files.exists(teacherFile)){
            Files.createFile(teacherFile);
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow row = sheet.createRow(0);
        HSSFCell nameCell = row.createCell(0);
        nameCell.setCellValue("姓名");
        HSSFCell isChairCell = row.createCell(1);
        isChairCell.setCellValue("是否可担任答辩组长");
        for(int i=1; i<=files.length; i++){
            HSSFRow r = sheet.createRow(i);
            HSSFCell name = r.createCell(0);
            name.setCellValue(files[i-1]);

            HSSFCell isChair = r.createCell(1);
            if(ConfigConstant.CHAIRS.contains(files[i-1])){
                isChair.setCellValue("是");
            }else{
                isChair.setCellValue("否");
            }
        }

        workbook.write(Files.newOutputStream(teacherFile));
    }

    /**
     * 生成subjects.xls文件
     * @throws IOException
     */
    @Test
    public void generateSubjectTableFile() throws IOException{
        String root = "/home/ithink/java/DefenseArrangement/src/main/resources/";

        Path subjectFile = Paths.get(root + "subjects.xls");
        if(!Files.exists(subjectFile)){
            Files.createFile(subjectFile);
        }
        Files.write(subjectFile, Files.readAllBytes(Paths.get(root+"model.xls")));

        HSSFWorkbook subjectWorkbook = new HSSFWorkbook(Files.newInputStream(subjectFile));
        HSSFSheet subjectSheet = subjectWorkbook.getSheetAt(0);

        File file = new File(root + "subjectTables");
        String[] files = file.list();
        for(int i=0; i<files.length; i++){
            String teacherName = files[i].substring(0, files[i].lastIndexOf("."));
            files[i] = root + "subjectTables" + File.separator + files[i];

            HSSFWorkbook workbook = new HSSFWorkbook(Files.newInputStream(Paths.get(files[i])));
            HSSFSheet sheet = workbook.getSheetAt(0);

            for(int j=1; j<=sheet.getLastRowNum(); j++){
                HSSFRow row = sheet.getRow(j);
                HSSFRow subjectRow =subjectSheet.getRow(j);
                for(int k=1; k<=7; k++){
                    HSSFCell cell = row.getCell(k);
                    HSSFCell subjectCell = subjectRow.getCell(k);
                    if(subjectCell == null){
                        subjectCell = subjectRow.createCell(k);
                        subjectCell.setCellValue("");
                    }

                    if(cell!=null && cell.getStringCellValue().equals("课")){
                        subjectCell.setCellValue(subjectCell.getStringCellValue()+","+teacherName);
                    }
                }
            }
        }

        for(int i=1; i<=subjectSheet.getLastRowNum(); i++){
            HSSFRow subjectRow =subjectSheet.getRow(i);

            for(int j=1; j<=7; j++){
                HSSFCell subjectCell = subjectRow.getCell(j);

                subjectCell.setCellValue(subjectCell.getStringCellValue().substring(1));
            }
        }

        subjectWorkbook.write(Files.newOutputStream(subjectFile));
    }

    /**
     * 测试教师数据是否统计正确
     */
    @Test
    public void testTeacherStatistics() throws IOException{
        TeacherStatistics teacherStatistics = new TeacherStatistics();
        teacherStatistics.setTeacherMap(ConfigConstant.ROOT_PATH + "teachers.xls");

        System.out.println(teacherStatistics.getTeacherMap().toString());
    }

    /**
     * 测试学生与导师映射关系数据是否统计正确
     */
    @Test
    public void testTeacherAnStudentsStatistics() throws IOException{
        TeacherAndStudentStatistics teacherAndStudentStatistics = new TeacherAndStudentStatistics();
        teacherAndStudentStatistics.setMaps(ConfigConstant.ROOT_PATH + "input.xls");

        System.out.println(teacherAndStudentStatistics.getNumberToStudentMap());
        System.out.println(teacherAndStudentStatistics.getStudentNumberToTeacherMap());
        System.out.println(teacherAndStudentStatistics.getTeacherToStudentNumberMap());
    }

    /**
     * 测试空闲时间列表是否获取正确
     */
    @Test
    public void testFreeTimeStatistics() throws IOException, ParseException{
        TeacherStatistics teacherStatistics = new TeacherStatistics();
        teacherStatistics.setTeacherMap(ConfigConstant.ROOT_PATH + "teachers.xls");

        FreeTimeStatistics freeTimeStatistics = new FreeTimeStatistics("2017-9-10", "2017-9-22",
                ConfigConstant.ROOT_PATH+"subjects.xls",
                teacherStatistics.getTeacherMap().keySet(), null);
        freeTimeStatistics.initMaps();
        freeTimeStatistics.fillFreeTimeMap();

        System.out.println(freeTimeStatistics.getFreeTimeMap().toString());
    }

    /**
     * 测试分组生成
     */
    @Test
    public void testDefenseArrangement() throws IOException, ParseException{
        TeacherStatistics teacherStatistics = new TeacherStatistics();
        teacherStatistics.setTeacherMap(ConfigConstant.ROOT_PATH + "teachers.xls");

        System.out.println(teacherStatistics.getTeacherMap().toString());

        FreeTimeStatistics freeTimeStatistics = new FreeTimeStatistics("2017-9-10", "2017-9-22",
                ConfigConstant.ROOT_PATH+"subjects.xls",
                teacherStatistics.getTeacherMap().keySet(), null);
        freeTimeStatistics.initMaps();
        freeTimeStatistics.fillFreeTimeMap();

        System.out.println(freeTimeStatistics.getFreeTimeMap().toString());

        TeacherAndStudentStatistics teacherAndStudentStatistics = new TeacherAndStudentStatistics();
        teacherAndStudentStatistics.setMaps(ConfigConstant.ROOT_PATH + "input.xls");

        System.out.println(teacherAndStudentStatistics.getNumberToStudentMap());
        System.out.println(teacherAndStudentStatistics.getStudentNumberToTeacherMap());
        System.out.println(teacherAndStudentStatistics.getTeacherToStudentNumberMap());

        DefenseArrangement defenseArrangement = new DefenseArrangement(freeTimeStatistics.getFreeTimeMap(),
                teacherAndStudentStatistics.getTeacherToStudentNumberMap(),
                teacherAndStudentStatistics.getStudentNumberToTeacherMap(),
                teacherAndStudentStatistics.getNumberToStudentMap(),
                teacherStatistics.getTeacherMap());
        defenseArrangement.setAllGroups();
        System.out.println(defenseArrangement.getAllGroups());
    }


    public static void main(String[] args){
        //n行m列，三个人站在不同的位置，形成平行四边形
        //n,m,符号矩阵(char[][]{'+', '-','+'})，两个人的位置坐标，
        //4,8,
        char[][] matrix = {
                {'-','-', '-','-','-', '-','-','-'},
                {'-','+', '-','-','-', '-','-','-'},
                {'-','-', '-','-','-', '-','-','+'},
                {'-','-', '-','-','-', '-','+','-'}
        };
        System.out.println(Arrays.toString(test(4,8,matrix,2,2,4,7)));

        char[][] matrix1 = {
                {'-','-', '-','-'},
                {'-','+', '-','-'},
                {'-','+', '-','-'},
                {'-','-', '-','-'},
                {'-','-', '+','-'}
        };
        System.out.println(Arrays.toString(test(5,4,matrix1,2,2,5,3)));

        //{2,5,8,}
        System.out.println(test2(3, new int[]{5,7,10}, new int[]{2,3,6}, 15, 5));

    }

    public static int[] test(int m, int n, char[][] matrix, int x1, int y1, int x2, int y2){
        int[] xx = new int[3];
        int[] yy = new int[3];
        int index = 0;

        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[0].length; j++){
                if(matrix[i][j] == '+'){
                    xx[index] = i;
                    yy[index] = j;
                    index++;
                }
            }
        }

        int[] result;
        if((result = getResult(0,1,2,matrix, xx, yy)) != null){
            return result;
        }
        if((result = getResult(0,2,1,matrix, xx, yy)) != null){
            return result;
        }
        if((result = getResult(1,0,2,matrix, xx, yy)) != null){
            return result;
        }
        if((result = getResult(1,2,0,matrix, xx, yy)) != null){
            return result;
        }
        if((result = getResult(2,1,0,matrix, xx, yy)) != null){
            return result;
        }
        if((result = getResult(2,0,1,matrix, xx, yy)) != null){
            return result;
        }

        return result;
    }

    public static int[] getResult(int i, int j, int k, char[][] matrix, int[]xx, int[] yy){
        int x, y;

        x = xx[i] - (xx[j] - xx[k]);
        y = yy[i] - (yy[j] - yy[k]);
        if((x>=0 && x<matrix.length) && (y>=0 && y<matrix[0].length)){
            return new int[]{x, y};
        }

        return null;
    }

    public static int test2(int n, int[] dos, int[] jq, int d, int ie){
        if(ie < dos[0])return -1;

        int count = 0;
        for(int i=0; i<dos.length; i++){
            ie -= (i==0 ? dos[i] : dos[i]-dos[i-1]);
            if(i!=dos.length-1 && ie < dos[i+1]-dos[i]){
                count++;
                ie += jq[i];
                if(ie < dos[i+1]-dos[i])return -1;
            }
            else if(i==dos.length-1 && ie<d-dos[i]){
                count++;
                ie += jq[i];
                if(ie < d-dos[i])return -1;
            }
        }

        return count;
    }

    @Test
    public void test(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("吴晓军");
        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("朱利");
        list.removeAll(list2);

        String[] strs = "xxx".split(",");
        System.out.println(strs.length);
    }

}
