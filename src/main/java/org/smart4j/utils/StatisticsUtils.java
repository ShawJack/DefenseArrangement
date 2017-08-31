package org.smart4j.utils;

import org.smart4j.DefenseArrangement;
import org.smart4j.entity.Group;
import org.smart4j.statistics.FreeTimeStatistics;
import org.smart4j.statistics.TeacherAndStudentStatistics;
import org.smart4j.statistics.TeacherStatistics;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by ithink on 17-8-30.
 */
public class StatisticsUtils {

    /**
     * 获取老师名单
     */
    public static Map<String, Boolean> getTeacherMap(String teacherFilename) throws IOException{
        TeacherStatistics teacherStatistics = new TeacherStatistics();
        teacherStatistics.setTeacherMap(teacherFilename);

        return teacherStatistics.getTeacherMap();
    }

    /**
     * 获取学生名单
     */
    public static TeacherAndStudentStatistics getTeacherAndStudentStatistics(String studentsFilename) throws  IOException{

        TeacherAndStudentStatistics teacherAndStudentStatistics = new TeacherAndStudentStatistics();
        teacherAndStudentStatistics.setMaps(studentsFilename);

        return teacherAndStudentStatistics;
    }

    /**
     * 获取空闲时间
     */
    public static  Map<String, List<String>> getFreeTimeMap(String subjectsFilename, String startDate,
                                                     String endDate, Map<String, Boolean> teacherMap,
                                                            String businessFilename)
            throws IOException, ParseException{

        FreeTimeStatistics freeTimeStatistics = new FreeTimeStatistics(startDate, endDate, subjectsFilename,
                teacherMap.keySet(), businessFilename);
        freeTimeStatistics.initMaps();
        freeTimeStatistics.fillFreeTimeMap();
        freeTimeStatistics.updateFreeMapByBusinessMap();

        return freeTimeStatistics.getFreeTimeMap();
    }

    /**
     * 获取分组数据
     */
    public static List<Group> getAllGroups(TeacherAndStudentStatistics teacherAndStudentStatistics,
                                           Map<String, List<String>> freeTimeMap,
                                           Map<String, Boolean> teacherMap){
        DefenseArrangement defenseArrangement = new DefenseArrangement(freeTimeMap,
                teacherAndStudentStatistics.getTeacherToStudentNumberMap(),
                teacherAndStudentStatistics.getStudentNumberToTeacherMap(),
                teacherAndStudentStatistics.getNumberToStudentMap(),
                teacherMap);
        defenseArrangement.setAllGroups();

        return defenseArrangement.getAllGroups();
    }

    /**
     * 完善分组，合并分组
     */
    public static void optimizeGroups(List<Group> groups, Map<String, String> numberToTeacherMap){

        ListIterator<Group> iterator = groups.listIterator();
        List<String> groupsOptimited = new ArrayList<>();
        while(iterator.hasNext()){
            Group group = iterator.next();
            if(group.getStudentsNumber().size() == 0){
                iterator.remove();
            }else if(group.getStudentsNumber().size() < 6){
                groupsOptimited.addAll(group.getStudentsNumber());
                iterator.remove();
            }
        }

        while(iterator.hasPrevious()){
            Group group = iterator.previous();

            if(group.getStudentsNumber().size() <= 8){
                //查看该分组中是否有待分组学生的导师
                checkGroup(group, numberToTeacherMap, groupsOptimited);
                if(groupsOptimited.size() == 0)break;
            }
        }
    }

    /**
     * 查看该分组中是否有待分组学生的导师
     */
    public static void checkGroup(Group group, Map<String, String> numberToTeacherMap, List<String> groupsOptimited){
        Iterator<String> iterator = groupsOptimited.iterator();
        while(iterator.hasNext()){
            String number = iterator.next();
            if(group.getTeachers().contains(numberToTeacherMap.get(number))){
                group.getStudentsNumber().add(number);
                iterator.remove();
                break;
            }
        }
    }

}
