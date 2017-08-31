# DefenseArrangement
# 答辩分组生成工具

### 1. 预览 

![image](https://github.com/ShawJack/DefenseArrangement/blob/master/img/img.jpg)

### 2.思路 
- 根据老师的上课时间表和出差时间表，生成一个空闲时间表，表中结构为{时间段，{xxx, yyy, zzz,……}}，表示哪个时间段哪些老师有时间；
- 从空闲时间表中选择老师分组答辩，每天的时间段分为上午和下午，比如2017-8-1有两个时间段（2017-8-1 上午， 2017-8-1 下午）
- 按照惯例，每个时间段安排两个小组，分别在两个讲习室内答辩，每个小组5个老师和8个学生，学生人数可以根据情况变化，但必须保证在6-9人
- 每个小组内，老师中必须包括一名组长主持答辩。当时间段内的老师中，没有老师可以担任组长，该时间段不安排答辩。

### 3.整个工程的目录结构如下：
```
DefenseArrangement/
　　┗ src/
　　　　┗ main/
　　　　　　┗ java/
　　　　　　┗ resources/
　　┗ pom.xml
```
在 `java` 目录下，创建以下包名目录结构：

```
org/
　　┗ smart4j/
　　　　┗ component/
　　　　┗ entity/
　　　　┗ frame/
　　　　┗ statistics/
　　　　┗ utils/
```
其中
```
org/
　　┗ smart4j/
　　　　┗ component/
```
存放自定义组件，比如日期选择组件JDateChooser

```
org/
　　┗ smart4j/
　　　　┗ entity/
```
存放自定义实体类，比如分组实体Group

```
org/
　　┗ smart4j/
　　　　┗ frame/
```
存放GUI，项目的UI在这里生成

```
org/
　　┗ smart4j/
　　　　┗ statistics/
```
存放统计数据工具类，包括空闲时间统计数据、老师信息统计数据、学生信息统计数据

```
org/
　　┗ smart4j/
　　　　┗ utils/
```
存放各种工具类，比如文件读写工具、日期转换工具、统计数据获取工具、测试文件生成工具等

### 4.表格文件规范
老师表：
![image](https://github.com/ShawJack/DefenseArrangement/blob/master/xlsmodels/teacher.png)

学生表
![image](https://github.com/ShawJack/DefenseArrangement/blob/master/xlsmodels/student.png)

课程表
![image](https://github.com/ShawJack/DefenseArrangement/blob/master/xlsmodels/subjects.png)

出差表
![image](https://github.com/ShawJack/DefenseArrangement/blob/master/xlsmodels/business.png)








