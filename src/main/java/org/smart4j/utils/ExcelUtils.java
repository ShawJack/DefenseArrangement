package org.smart4j.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.smart4j.statistics.TeacherStatistics;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ithink on 17-8-26.
 */
public class ExcelUtils {
    private static final String[] week = new String[]{"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};

    /**
     * 学号-学生-导师 名册读取，{导师姓名，{学生姓名列表}}
     */
    public static Map<String, List<String>> getNameList(String filename) throws IOException{
        InputStream nameListFileIs = ExcelUtils.class.getClassLoader().getResourceAsStream(filename);

        HSSFWorkbook workbook = new HSSFWorkbook(nameListFileIs);
        Map<String, List<String>> nameList = new HashMap<String, List<String>>();

        HSSFSheet sheet = workbook.getSheetAt(0);

        for(int rowNum=0; rowNum<=sheet.getLastRowNum(); rowNum++){
            HSSFRow row = sheet.getRow(rowNum);

            HSSFCell studentNameCell = row.getCell(1);
            HSSFCell tearchNameCell = row.getCell(4);
            if(studentNameCell!=null && tearchNameCell!=null){
                String studentName = studentNameCell.getStringCellValue();
                String teacherName = tearchNameCell.getStringCellValue();
                if(nameList.containsKey(teacherName)){
                    nameList.get(teacherName).add(studentName);
                }else{
                    List<String> studentList = new LinkedList<String>();
                    studentList.add(studentName);
                    nameList.put(teacherName, studentList);
                }
            }
        }

        return nameList;
    }

    /**
     * 导出答辩安排，每组老师自己的学生随机有0～3个
     */
    public static void outputArrangement(LinkedHashMap<String, List<String>> freeTimeMap, Map<String, List<String>> teacherToStudentMap){
        for(Map.Entry<String, List<String>> entry : freeTimeMap.entrySet()){
            List<String> teachers = entry.getValue();

            Collections.sort(teachers, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    boolean existO1 = teacherToStudentMap.containsKey(o1);
                    boolean existO2 = teacherToStudentMap.containsKey(o2);
                    if(!existO1 && !existO2)return 0;
                    else if(existO1 && !existO2)return -1;
                    else if(!existO1 && existO2)return 1;

                    int size1 = teacherToStudentMap.get(o1).size();
                    int size2 = teacherToStudentMap.get(o2).size();
                    if(size1 > size2)return -1;
                    else if(size1 < size2)return 1;
                    return 0;
                }
            });


            List<String> group1 = new ArrayList<>();
            for (int i=0; i<1; i++)group1.add(teachers.get(i));
            for (int i=9; i>=6; i--)group1.add(teachers.get(i));
            arrangeOneGroup(group1, teacherToStudentMap, "讲习室202", entry.getKey());

            List<String> group2 = new ArrayList<>();
            for (int i=1; i<6; i++)group2.add(teachers.get(i));
            arrangeOneGroup(group2, teacherToStudentMap, "讲习室208", entry.getKey());
        }
    }

    /**
     * 安排每一组的成员
     */
    public static void arrangeOneGroup(List<String> teachers, Map<String, List<String>> teacherToStudentMap, String room, String time){

        List<String> students1 = new ArrayList<>();
        int index = 0;
        int limited = 0;
        for(String teacher : teachers){
            if(teacherToStudentMap.containsKey(teacher)){
                limited += teacherToStudentMap.get(teacher).size();
            }
        }
        limited = limited > 10 ? 8 : limited;
        while(students1.size() < limited){
            if(teacherToStudentMap.containsKey(teachers.get(index).trim()) && teacherToStudentMap.get(teachers.get(index).trim()).size() > 0){
                students1.add(teacherToStudentMap.get(teachers.get(index).trim()).get(0));
                teacherToStudentMap.get(teachers.get(index).trim()).remove(0);
            }
            index = (++index) % teachers.size();
        }

        System.out.println("时间： " + time);
        System.out.println("地点： " + room);
        System.out.println("评审专家： " + teachers.toString());
        System.out.println("答辩学生名单： " + students1.toString());
        System.out.println();
    }

    /**
     * 读取教师的课表，得到空闲时间表格
     */
    public static LinkedHashMap<String, List<String>> getFreeTimeMap(String[] subjectTables, String startDate, String endDate) throws ParseException, IOException{

        Map<String, List<String>> weekToDateMap = new HashMap<String, List<String>>();
        LinkedHashMap<String, List<String>> freeTimeMap = new LinkedHashMap<String, List<String>>();

        //初始化上面两个表
        initFreeTimeMap(freeTimeMap, weekToDateMap, startDate, endDate);

        fillFreeTimeMapBySubjectTables(freeTimeMap, weekToDateMap, subjectTables);

        return freeTimeMap;
    }

    /**
     * 初始化空闲时间表
     */
    public static void initFreeTimeMap(LinkedHashMap<String, List<String>> freeTimeMap, Map<String, List<String>> weekToDateMap, String start, String end) throws ParseException{

        //星期的格式
        SimpleDateFormat weekFormat = new SimpleDateFormat("EEEE");
        //答辩时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //转换为日期格式
        Date startDate = dateFormat.parse(start);
        Date endDate = dateFormat.parse(end);
        //计算天数
        long days = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
        //用于日期累加
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        //初始化星期-日期的Map
        for(int i=0; i<7; i++){
            weekToDateMap.put(week[i], new ArrayList<>());
        }

        //初始化空闲时间表
        for(int i=0; i<=days; i++){
            String dateToString = dateFormat.format(calendar.getTime());

            freeTimeMap.put(dateToString+" 上午", new ArrayList<>());
            freeTimeMap.put(dateToString+" 下午", new ArrayList<>());

            String weekOFDate = weekFormat.format(calendar.getTime());
            weekToDateMap.get(weekOFDate).add(dateToString);

            calendar.add(Calendar.DATE, 1);
        }

    }

    /**
     * 依次读取各个老师的课程表，填充空闲时间表
     */
    public static void fillFreeTimeMapBySubjectTables(LinkedHashMap<String, List<String>> freeTimeMap, Map<String, List<String>> weekToDateMap, String[] subjectTables) throws IOException{
        for(String subjectTable : subjectTables){
            int lastPathSeparator = subjectTable.lastIndexOf(File.separator);
            String teacherName = subjectTable.substring(lastPathSeparator==-1?0:lastPathSeparator+1, subjectTable.lastIndexOf("."));

            Path path = Paths.get(subjectTable);
            InputStream inputStream = Files.newInputStream(path);

            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            for(int i=1; i<4; i+=2){
                HSSFRow row1 = sheet.getRow(i);
                HSSFRow row2 = sheet.getRow(i+1);

                for(int j=1; j<=7; j++){
                    HSSFCell cell1 = row1.getCell(j);
                    HSSFCell cell2 = row2.getCell(j);
                    String weekday = week[j-1];
                    String timeQuantum = i==1 ? "上午" : "下午";

                    fillFreeTimeMap(freeTimeMap, weekToDateMap, cell1, cell2, teacherName, weekday, timeQuantum);
                }

            }
        }
    }

    /**
     * 填充空闲时间表
     */
    public static void fillFreeTimeMap(LinkedHashMap<String, List<String>> freeTimeMap, Map<String, List<String>> weekToDateMap, HSSFCell cell1, HSSFCell cell2, String teacherName, String weekday, String timeQuantum){
        if((cell1==null || cell1.getStringCellValue().equals(""))
                && (cell2==null || cell2.getStringCellValue().equals(""))){
            List<String> dates = weekToDateMap.get(weekday);
            for(String date : dates){
                freeTimeMap.get(date+" "+timeQuantum).add(teacherName);
            }
        }
    }


    @Test
    public void test() throws Exception{
        //System.out.println(System.getProperty("user.dir") + " " + getClass().getResource(".").getPath());
        Map<String, List<String>> nameList = getNameList("input.xls");
        System.out.println(nameList.toString());

        String startDate = "2017-8-27";
        String endDate = "2017-9-1";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);
        long days = (simpleDateFormat.parse(endDate).getTime()-simpleDateFormat.parse(startDate).getTime())/(1000*60*60*24);
        System.out.println(days);

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(simpleDateFormat.parse(startDate));
        calendar.add(Calendar.DATE, 1);
        System.out.println(simpleDateFormat.format(calendar.getTime()));

        System.out.println(new SimpleDateFormat("EEEE").format(new Date()));

        LinkedHashMap<String, List<String>> freeTimeMap = new LinkedHashMap<>();
        Map<String, List<String>> weekToDateMap = new HashMap<>();
        initFreeTimeMap(freeTimeMap, weekToDateMap, "2017-8-27", "2017-9-12");
        System.out.println(freeTimeMap.toString());
        System.out.println(weekToDateMap.toString());

        String rootDictionary = "/home/ithink/java/DefenseArrangement/src/main/resources/subjectTables";
        File file = new File(rootDictionary);
        String[] subjectTables = file.list();
        for(int i=0; i<subjectTables.length; i++){
            subjectTables[i] = rootDictionary + File.separator + subjectTables[i];
        }
        LinkedHashMap<String, List<String>> freeTime = getFreeTimeMap(subjectTables, "2017-9-10", "2017-9-27");
        System.out.println(freeTime.toString());

        /*for(Map.Entry<String, List<String>> entry : freeTime.entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue().toString());
        }*/
        outputArrangement(freeTime, nameList);

    }

    @Test
    public void test2() throws IOException{
        /*Path path = Paths.get("/home/ithink/桌面/teachers");
        String line = Files.readAllLines(path).get(0);

        String[] teachers = line.split("、");
        System.out.println(Arrays.toString(teachers));*/
        //Map<String, List<String>> nameList = getNameList("input.xls");
        String rootDir = "/home/ithink/java/DefenseArrangement/src/main/resources/";

        TeacherStatistics teacherStatistics = new TeacherStatistics();
        teacherStatistics.setTeacherMap(rootDir + "teachers.xls");
        Set<String> teachers =teacherStatistics.getTeacherMap().keySet();


        Path model = Paths.get(rootDir + "model.xls");
        byte[] modelBytes = Files.readAllBytes(model);
        for(String teacher : teachers){
            Path file = Paths.get(rootDir + "subjectTables" + File.separator + teacher + ".xls");
            if(Files.exists(file)){
                Files.delete(file);
                Files.createFile(file);
            }
            Files.write(file, modelBytes);

            HSSFWorkbook workbook = new HSSFWorkbook(Files.newInputStream(file));
            HSSFSheet sheet = workbook.getSheetAt(0);
            for(int i=1; i<=sheet.getLastRowNum(); i++){
                HSSFRow row = sheet.getRow(i);
                for(int j=1; j<=7; j++){
                    int hasSubject = new Random().nextInt(10);
                    if(hasSubject > 8){
                        HSSFCell cell = row.createCell(j);
                        cell.setCellValue("课");
                    }
                }
            }

            workbook.write(Files.newOutputStream(file));
        }
    }

}
