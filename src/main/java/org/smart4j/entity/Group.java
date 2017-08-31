package org.smart4j.entity;

import java.util.List;

/**
 * Created by ithink on 17-8-29.
 */
public class Group {

    private String room;
    private String timeQuantum;
    private List<String> teachers;
    private List<String> studentsNumber;

    private String chair;

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTimeQuantum() {
        return timeQuantum;
    }

    public void setTimeQuantum(String timeQuantum) {
        this.timeQuantum = timeQuantum;
    }

    public List<String> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<String> teachers) {
        this.teachers = teachers;
    }

    public List<String> getStudentsNumber() {
        return studentsNumber;
    }

    public void setStudentsNumber(List<String> studentsNumber) {
        this.studentsNumber = studentsNumber;
    }

    public String getChair() {
        return chair;
    }

    public void setChair(String chair) {
        this.chair = chair;
    }

    @Override
    public String toString() {
        return "时间： " + timeQuantum+ "\n" +
                "地点： " + room + "\n" +
                "组长： " + chair + "\n" +
                "专家： " + teachers.toString() + "\n" +
                "学生： " + studentsNumber.toString() + "\n\n";
    }
}
