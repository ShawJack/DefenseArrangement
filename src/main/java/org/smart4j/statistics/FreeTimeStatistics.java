package org.smart4j.statistics;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.smart4j.ConfigConstant;
import org.smart4j.utils.DateUtils;
import org.smart4j.utils.FileUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;

/**
 * 空闲时间统计数据
 * Created by ithink on 17-8-28.
 */
public class FreeTimeStatistics {

    private String startDate;
    private String endDate;
    private String subjectTableFile;
    private Map<String, List<String>> freeTimeMap;
    private Map<String, List<String>> weekToDateMap;
    private Set<String> teacherSet;
    private String businessFile;

    public FreeTimeStatistics(String startDate, String endDate,
                              String subjectTableFile, Set<String> teacherSet, String businessFile){

        this.startDate = startDate;
        this.endDate = endDate;
        this.subjectTableFile = subjectTableFile;
        this.teacherSet = teacherSet;
        this.businessFile = businessFile;

        freeTimeMap = new LinkedHashMap<>();
        weekToDateMap = new HashMap<>();
    }

    /**
     * 初始化空闲时间映射表
     */
    public void initMaps() throws ParseException{

        Date start = ConfigConstant.DATE_FORMAT.parse(startDate);
        Date end = ConfigConstant.DATE_FORMAT.parse(endDate);

        long days = DateUtils.getDays(start, end);
        Calendar calendar = DateUtils.getCalendar(start);

        initWeekToDateMap();
        intFreeTimeMapAndFillWeekToDateMap(days, calendar);
    }

    /**
     * 初始化星期与日期的对应表
     */
    private void initWeekToDateMap(){

        for(int i=0; i<ConfigConstant.WEEKDAY.length; i++){

            weekToDateMap.put(ConfigConstant.WEEKDAY[i], new ArrayList<String>());
        }
    }

    /**
     * 初始化freeTimeMap，并填充weekToDateMap
     */
    private void intFreeTimeMapAndFillWeekToDateMap(long days, Calendar calendar){

        for(long i=0; i<days; i++){
            //freeTimeMap初始化
            String date = ConfigConstant.DATE_FORMAT.format(calendar.getTime());
            freeTimeMap.put(date + " 上午", new ArrayList<>(teacherSet));
            freeTimeMap.put(date + " 下午", new ArrayList<>(teacherSet));

            //weekToDateMap填充
            String weekday = ConfigConstant.WEEK_FORMAT.format(calendar.getTime());
            weekToDateMap.get(weekday).add(date);

            calendar.add(Calendar.DATE, 1);
        }
    }

    /**
     * 填充freeTimeMap
     */
    public void fillFreeTimeMap() throws IOException{

        InputStream fileInputStream = FileUtils.getInputFileStream(subjectTableFile);
        HSSFSheet sheet = FileUtils.getSheetOfXLS(fileInputStream, 0);

        for(int i=1; i<=4; i+=2){

            HSSFRow row1 = sheet.getRow(i);
            HSSFRow row2 = sheet.getRow(i+1);
            for(int j=1; j<=7; j++){
                HSSFCell cell1 = row1.getCell(j);
                HSSFCell cell2 = row2.getCell(j);

                String[] teachers1 = cell1.getStringCellValue().split("、|,|，");
                String[] teachers2 = cell2.getStringCellValue().split("、|,|，");

                Set<String> teachers = new HashSet<>();
                teachers.addAll(Arrays.asList(teachers1));
                teachers.addAll(Arrays.asList(teachers2));

                String timeQuantum = i<=2 ? " 上午" : " 下午";

                List<String> dateListOnWeek = weekToDateMap.get(ConfigConstant.WEEKDAY[j-1]);
                for(String date : dateListOnWeek){
                    freeTimeMap.get(date + timeQuantum).removeAll(teachers);
                }
            }
        }
    }

    /**
     * 根据出差表更新空闲时间表
     */
    public void updateFreeMapByBusinessMap() throws IOException{
        if(businessFile!=null && !businessFile.equals("")) {
            InputStream businessFileInputStream = FileUtils.getInputFileStream(businessFile);
            HSSFSheet sheet = FileUtils.getSheetOfXLS(businessFileInputStream, 0);

            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                HSSFRow row = sheet.getRow(i);

                HSSFCell dateCell = row.getCell(0);
                HSSFCell teacherListCell = row.getCell(1);

                String date = ConfigConstant.DATE_FORMAT.format(dateCell.getDateCellValue());
                List<String> teacherList = new ArrayList<>(Arrays.asList(teacherListCell.getStringCellValue().split(",|、|，")));
                freeTimeMap.get(date + " 上午").removeAll(teacherList);
                freeTimeMap.get(date + " 下午").removeAll(teacherList);
            }
        }
    }

    public Map<String, List<String>> getFreeTimeMap() {
        return freeTimeMap;
    }
}
