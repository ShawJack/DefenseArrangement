package org.smart4j;

import org.smart4j.entity.Group;

import java.util.*;

/**
 * Created by ithink on 17-8-29.
 */
public class DefenseArrangement {

    private Map<String, List<String>> freeTimeMap;
    private Map<String, List<String>> teacherToStudentNumberMap;
    private Map<String, String> studentNumberToTeacherMap;
    private Map<String, String> numberToStudentMap;
    private Map<String, Boolean> teacherMap;

    private List<Group> allGroups;

    public DefenseArrangement(Map<String, List<String>> freeTimeMap,
                              Map<String, List<String>> teacherToStudentNumberMap,
                              Map<String, String> studentNumberToTeacherMap,
                              Map<String, String> numberToStudentMap,
                              Map<String, Boolean> teacherMap){
        this.freeTimeMap = freeTimeMap;
        this.teacherMap = teacherMap;
        this.teacherToStudentNumberMap =teacherToStudentNumberMap ;
        this.numberToStudentMap = numberToStudentMap;
        this.studentNumberToTeacherMap = studentNumberToTeacherMap;

        allGroups = new LinkedList<>();
    }

    /**
     * 按时间顺序安排分组
     */
    public void setAllGroups(){

        for(Map.Entry<String, List<String>> entry : freeTimeMap.entrySet()){
            String timeQuantum = entry.getKey();
            List<String> teachers = entry.getValue();

            Collections.sort(teachers, new ComparatorForTeacher());

            List<String> candicate = getCandicate(teachers, 10);
            List<String> chairs = getChairs(candicate);
            if(chairs.size() < 2){
                chairs = getChairs(teachers);

                //chairs.size() == 0 今天没有组长有空，今日不安排答辩
                if(chairs.size() == 0)continue;
                else{
                    candicate.removeAll(chairs);
                    Group group = prepareForGroup(candicate, chairs.get(0), "讲习室202", timeQuantum,
                            0, 2, candicate.size()-1, candicate.size()-3);
                    allGroups.add(group);
                }
            }else{
                candicate.removeAll(chairs);

                Group group1 = prepareForGroup(candicate, chairs.get(0), "讲习室202", timeQuantum,
                        0, 2, candicate.size()-1, candicate.size()-3);

                Group group2 = prepareForGroup(candicate, chairs.get(1), "讲习室208", timeQuantum,
                        2, 4, candicate.size()-3, candicate.size()-5);

                allGroups.add(group1);
                allGroups.add(group2);
            }

        }
    }

    /**
     * 获取候选老师，人数不少于10，剩余未答辩学生人数超过1也加入
     */
    public List<String> getCandicate(List<String> teachers, int teacherCount){
        List<String> candicate = new ArrayList<>();

        if(teachers.size() <= teacherCount){

            candicate.addAll(teachers);
        }else{

            int index = 0;
            while(candicate.size()<teacherCount || (teacherToStudentNumberMap.containsKey(teachers.get(index)) && teacherToStudentNumberMap.get(teachers.get(index)).size()>0)){
                candicate.add(teachers.get(index));
                index++;
            }
        }

        return candicate;
    }

    /**
     * 得到同一时间段内的两组组长
     */
    public List<String> getChairs(List<String> candicate){

        List<String> chairs = new ArrayList<>(2);

        for(String teacher : candicate){
            if(teacherMap.get(teacher)){
                chairs.add(teacher);

                if(chairs.size() == 2)break;
            }
        }

        return chairs;
    }

    /**
     * 为生成分组做好准备工作
     */
    public Group prepareForGroup(List<String> teachers, String chair, String room, String timeQuantum, int start1, int end1, int start2, int end2){
        List<String> teacherOfGroup = new ArrayList<>();
        teacherOfGroup.add(chair);
        for(int i=start1; i<end1; i++)teacherOfGroup.add(teachers.get(i));
        for(int i=start2; i>end2; i--)teacherOfGroup.add(teachers.get(i));

        Group group = getGroup(teacherOfGroup);
        group.setRoom(room);
        group.setTimeQuantum(timeQuantum);
        group.setChair(chair);

        return group;
    }

    /**
     * 生成一个分组
     */
    public Group getGroup(List<String> teachers){

        Group group = new Group();

        int limited = getLimited(teachers);
        limited = limited > 8 ? 8 : limited;

        List<String> students = new ArrayList<>();
        int index = teachers.size()-1;
        while(students.size() < limited){
            if(teacherToStudentNumberMap.containsKey(teachers.get(index)) && teacherToStudentNumberMap.get(teachers.get(index)).size() > 0){
                students.add(teacherToStudentNumberMap.get(teachers.get(index)).get(0));
                teacherToStudentNumberMap.get(teachers.get(index)).remove(0);
            }
            index = (--index + teachers.size()) % teachers.size();
        }

        group.setStudentsNumber(students);
        group.setTeachers(teachers);

        return group;
    }

    /**
     * 得到组中老师剩余学生总人数
     */
    public int getLimited(List<String> teachers){

        int limited = 0;
        for(String teacher : teachers){
            if(teacherToStudentNumberMap.containsKey(teacher)) {
                limited += teacherToStudentNumberMap.get(teacher).size();
            }
        }

        return limited;
    }

    public List<Group> getAllGroups() {
        return allGroups;
    }

    /**
     * 排序，剩余学生最多的老师排在最开始
     */
    class ComparatorForTeacher implements Comparator<String>{

        @Override
        public int compare(String o1, String o2) {
            boolean existO1 = teacherToStudentNumberMap.containsKey(o1);
            boolean existO2 = teacherToStudentNumberMap.containsKey(o2);
            if(!existO1 && !existO2)return 0;
            else if(existO1 && !existO2)return -1;
            else if(!existO1 && existO2)return 1;

            int size1 = teacherToStudentNumberMap.get(o1).size();
            int size2 = teacherToStudentNumberMap.get(o2).size();
            if(size1 > size2)return -1;
            else if(size1 < size2)return 1;
            return 0;
        }
    }


}
