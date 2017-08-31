package org.smart4j.frame;

import jdk.nashorn.internal.scripts.JD;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;
import org.smart4j.ConfigConstant;
import org.smart4j.DefenseArrangement;
import org.smart4j.component.JDateChooser;
import org.smart4j.entity.Group;
import org.smart4j.statistics.FreeTimeStatistics;
import org.smart4j.statistics.TeacherAndStudentStatistics;
import org.smart4j.statistics.TeacherStatistics;
import org.smart4j.utils.FileUtils;
import org.smart4j.utils.StatisticsUtils;
import sun.security.krb5.Config;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;
import java.util.List;

/**
 * 软件UI
 * Created by ithink on 17-8-29.
 */
public class DefenseUI extends JFrame {

    private JPanel mainPanel;
    private JPanel settingsPanel;
    private JPanel resultPanel;
    private JButton selectTeacherFileButton;
    private JTextField teacherFilenameTextField;
    private JButton selectSubjectFileButton;
    private JTextField subjectFilenameTextField;
    private JButton selectStudentFileButton;
    private JTextField studentFilenameTextField;

    private JButton selectBusinessFileButton;
    private JTextField businessFilenameTextField;

    private JLabel startLabel;
    private JTextField startDateTextField;
    private JLabel endLabel;
    private JTextField endDateTextField;
    private JButton generateButton;
    private JTextArea resultArea;

    private int width = 500;
    private int height = 635;

    //for windows
    /*private int widthOfSettingPanel = 493;
    private int heightOfSettingPanel = 200;
    private int widthOfResultPanel = 492;
    private int heightOfResultPanel = 395;
    private int colOfFilesTextfield = 35;
    private int colOfDateTextfield = 10;
    private int rowOfTextArea = 20;
    private int colOfTextArea = 43;*/

    //for Ubuntu
    private int widthOfSettingPanel = 500;
    private int heightOfSettingPanel = 200;
    private int widthOfResultPanel = 500;
    private int heightOfResultPanel = 425;
    private int colOfFilesTextfield = 55;
    private int colOfDateTextfield = 15;
    private int rowOfTextArea = 23;
    private int colOfTextArea = 69;

    public DefenseUI(){
        //组件初始化
        initComponents();

        //窗口框架结构
        initWindow();

        //主面板初始化
        initMainPanel();
        add(mainPanel);

        //参数设置面板初始化
        initSettingsPanel();
        mainPanel.add(settingsPanel);

        //结果面板初始化
        initResultPanel();
        mainPanel.add(resultPanel);

        //为各个组件添加监听
        addListeners();
    }

    /**
     * 初始化各个组件
     */
    private void initComponents(){

        selectTeacherFileButton = new JButton("专家名单");
        selectTeacherFileButton.setFont(ConfigConstant.FONT);
        teacherFilenameTextField = new JTextField(colOfFilesTextfield);
        teacherFilenameTextField.setFont(ConfigConstant.FONT);
        teacherFilenameTextField.setEditable(false);
        teacherFilenameTextField.setPreferredSize(new Dimension(55, 27));

        selectStudentFileButton = new JButton("学生名册");
        selectStudentFileButton.setFont(ConfigConstant.FONT);
        studentFilenameTextField = new JTextField(colOfFilesTextfield);
        studentFilenameTextField.setFont(ConfigConstant.FONT);
        studentFilenameTextField.setEditable(false);
        studentFilenameTextField.setPreferredSize(new Dimension(55, 27));

        selectSubjectFileButton = new JButton("课程列表");
        selectSubjectFileButton.setFont(ConfigConstant.FONT);
        subjectFilenameTextField = new JTextField(colOfFilesTextfield);
        subjectFilenameTextField.setFont(ConfigConstant.FONT);
        subjectFilenameTextField.setEditable(false);
        subjectFilenameTextField.setPreferredSize(new Dimension(55, 27));

        selectBusinessFileButton = new JButton("出差列表");
        selectBusinessFileButton.setFont(ConfigConstant.FONT);
        businessFilenameTextField = new JTextField(colOfFilesTextfield);
        businessFilenameTextField.setFont(ConfigConstant.FONT);
        businessFilenameTextField.setEditable(false);
        businessFilenameTextField.setPreferredSize(new Dimension(55, 27));

        startLabel = new JLabel("起始日期");
        startLabel.setFont(ConfigConstant.FONT);
        startDateTextField = new JTextField(colOfDateTextfield);
        startDateTextField.setFont(ConfigConstant.FONT);
        startDateTextField.setEditable(false);
        startDateTextField.setPreferredSize(new Dimension(15, 27));

        endLabel = new JLabel("结束日期");
        endLabel.setFont(ConfigConstant.FONT);
        endDateTextField = new JTextField(colOfDateTextfield);
        endDateTextField.setFont(ConfigConstant.FONT);
        endDateTextField.setEditable(false);
        endDateTextField.setPreferredSize(new Dimension(15, 27));

        generateButton = new JButton("生成答辩分组");
        generateButton.setFont(ConfigConstant.FONT);

        resultArea = new JTextArea(rowOfTextArea, colOfTextArea);
        resultArea.setFont(ConfigConstant.FONT);

        JDateChooser startDateChooser = JDateChooser.getInstance(new Date());
        JDateChooser endDateChooser = JDateChooser.getInstance(new Date());
        startDateChooser.register(startDateTextField);
        endDateChooser.register(endDateTextField);
    }

    /**
     * 初始化窗口框架
     */
    public void initWindow(){
        this.setTitle("学校答辩分组生成");
        setSize(width, height);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * 初始化mainPanel
     */
    public void initMainPanel(){
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setSize(width, height);
    }

    /**
     * 初始化settingsPanel
     */
    public void initSettingsPanel(){
        settingsPanel = new JPanel();
        TitledBorder border = BorderFactory.createTitledBorder("设置项");
        border.setTitleFont(ConfigConstant.FONT);
        settingsPanel.setBorder(border);
        settingsPanel.add(selectTeacherFileButton);
        settingsPanel.add(teacherFilenameTextField);
        settingsPanel.add(selectStudentFileButton);
        settingsPanel.add(studentFilenameTextField);
        settingsPanel.add(selectSubjectFileButton);
        settingsPanel.add(subjectFilenameTextField);

        settingsPanel.add(selectBusinessFileButton);
        settingsPanel.add(businessFilenameTextField);

        settingsPanel.add(startLabel);
        settingsPanel.add(startDateTextField);
        settingsPanel.add(endLabel);
        settingsPanel.add(endDateTextField);
        settingsPanel.add(new JLabel("    "));
        settingsPanel.add(generateButton);
        settingsPanel.setSize(widthOfSettingPanel, heightOfSettingPanel);
    }

    /**
     * 初始化resultPanel组件
     */
    public void initResultPanel(){
        resultPanel = new JPanel();
        TitledBorder resultBorder = BorderFactory.createTitledBorder("分组安排");
        resultBorder.setTitleFont(ConfigConstant.FONT);
        resultPanel.setBorder(resultBorder);
        resultPanel.setSize(widthOfResultPanel, heightOfResultPanel);
        resultPanel.setBounds(0, 205, widthOfResultPanel, heightOfResultPanel);

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        resultPanel.add(scrollPane);
    }

    /**
     * 添加监听
     */
    public void addListeners(){

        selectSubjectFileButton.addActionListener(e -> {
            JFileChooser jfc = getFileChooser();
            File file = jfc.getSelectedFile();
            subjectFilenameTextField.setText(file.getAbsolutePath());
        });

        selectTeacherFileButton.addActionListener(e -> {
            JFileChooser jfc = getFileChooser();
            File file = jfc.getSelectedFile();
            teacherFilenameTextField.setText(file.getAbsolutePath());
        });

        selectStudentFileButton.addActionListener(e -> {
            JFileChooser jfc = getFileChooser();
            File file = jfc.getSelectedFile();
            studentFilenameTextField.setText(file.getAbsolutePath());
        });

        selectBusinessFileButton.addActionListener(e -> {
            JFileChooser jfc = getFileChooser();
            File file = jfc.getSelectedFile();
            businessFilenameTextField.setText(file.getAbsolutePath());
        });

        generateButton.addActionListener(e -> {
            String subjectsFilename = subjectFilenameTextField.getText();
            String studentFilename = studentFilenameTextField.getText();
            String teacherFilename = teacherFilenameTextField.getText();
            String bussinessFilename = businessFilenameTextField.getText();
            String startDate = startDateTextField.getText();
            String endDate = endDateTextField.getText();

            if(checkSettings(subjectsFilename,teacherFilename,studentFilename,
                    startDate,endDate))return;

            try {
                Map<String, Boolean> teacherMap = StatisticsUtils.getTeacherMap(teacherFilename);
                TeacherAndStudentStatistics teacherAndStudentStatistics = StatisticsUtils.getTeacherAndStudentStatistics(studentFilename);
                Map<String, List<String>> freeTimeMap = StatisticsUtils.getFreeTimeMap(subjectsFilename, startDate, endDate, teacherMap, bussinessFilename);
                List<Group> groups = StatisticsUtils.getAllGroups(teacherAndStudentStatistics, freeTimeMap, teacherMap);

                StatisticsUtils.optimizeGroups(groups, teacherAndStudentStatistics.getStudentNumberToTeacherMap());

                outputGroups(groups, teacherAndStudentStatistics.getNumberToStudentMap(), teacherMap);

            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        });

    }

    /**
     * 获取文件选择器
     */
    public JFileChooser getFileChooser(){

        JFileChooser jfc = new JFileChooser();

        jfc.addChoosableFileFilter(FileUtils.getXLSFileFliter());
        jfc.setFileFilter(FileUtils.getXLSFileFliter());
        jfc.showDialog(new JLabel(), "选择");

        return jfc;
    }

    /**
     * 检查是否有为空的设置
     */
    public boolean checkSettings(String subjectsFilename, String teacherFilename, String studentFilename,
                                 String startDate, String endDate){
        if(subjectsFilename==null || subjectsFilename.equals("")
                || studentFilename==null || subjectsFilename.equals("")
                || teacherFilename==null || teacherFilename.equals("")
                || startDate==null || startDate.equals("")
                || endDate==null || endDate.equals("") ){
            JOptionPane.showMessageDialog(null,
                    "设置数据不完整！", "错误",
                    JOptionPane.WARNING_MESSAGE);
            return true;
        }

        return false;
    }

    /**
     * 输出分组数据
     */
    public void outputGroups(List<Group> groups, Map<String, String> numberToTeacherMap,
                             Map<String, Boolean> teacherMap) throws IOException{
        StringBuffer sb = new StringBuffer();
        String resultFile = generateResultFile();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();

        sb.append("考核组成员： " + teacherMap.keySet().toString() + "\n");
        sb.append("考核时间段: " + groups.get(0).getTimeQuantum() + " ~ " + groups.get(groups.size()-1).getTimeQuantum() + "（一共"+ groups.size() +"组）\n");

        int col = 0;
        createAndFillRow(sheet, "答辩分组安排", col++, 0);
        sheet.addMergedRegion(new CellRangeAddress(0,0, 0, 1));
        sheet.setColumnWidth(1, 55*256);
        createAndFillRow(sheet, sb.toString(), col++, 0);
        sheet.addMergedRegion(new CellRangeAddress(1,1, 0, 1));
        sheet.getRow(1).setHeightInPoints(50);

        int index = 1;
        for(Group group : groups){
            sb.append("第" + index + "组\n");

            createAndFillRow(sheet, "第" + index + "组", col++, 0);

            sb.append("时间： " + group.getTimeQuantum() + "\n");
            createAndFillRow(sheet, "时间", col, 0);
            createAndFillRow(sheet, group.getTimeQuantum(), col++, 1);

            sb.append("地点： " +group.getRoom() + "\n");
            createAndFillRow(sheet, "地点", col, 0);
            createAndFillRow(sheet, group.getRoom(), col++, 1);

            sb.append("组长： " +group.getChair() + "\n");
            createAndFillRow(sheet, "组长", col, 0);
            createAndFillRow(sheet, group.getChair(), col++, 1);

            sb.append("专家： " +group.getTeachers().toString() + "\n");
            createAndFillRow(sheet, "专家", col, 0);
            createAndFillRow(sheet, group.getTeachers().toString(), col++, 1);

            List<String> students = new ArrayList<>();
            for(String number : group.getStudentsNumber()){
                students.add(numberToTeacherMap.get(number));
            }

            sb.append("学生： " +students.toString() + "\n");
            createAndFillRow(sheet, "学生", col, 0);
            createAndFillRow(sheet, students.toString(), col++, 1);

            index++;
        }
        resultArea.setText(sb.toString());
        workbook.write(FileUtils.getOutputStream(resultFile));
    }

    /**
     * 导出到文件
     */
    public String generateResultFile() throws IOException{
        //弹出文件选择框
        JFileChooser chooser = new JFileChooser();

        //后缀名过滤器
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Excel表格文件(*.xls)", "xls");
        chooser.setFileFilter(filter);

        //下面的方法将阻塞，直到【用户按下保存按钮且“文件名”文本框不为空】或【用户按下取消按钮】
        int option = chooser.showSaveDialog(null);
        if(option==JFileChooser.APPROVE_OPTION){    //假如用户选择了保存
            File file = chooser.getSelectedFile();

            String fname = chooser.getName(file);   //从文件名输入框中获取文件名

            //假如用户填写的文件名不带我们制定的后缀名，那么我们给它添上后缀
            if(fname.indexOf(".xls")==-1){
                fname=chooser.getCurrentDirectory() + File.separator + fname+".xls";
                System.out.println("renamed");
                System.out.println(fname);
            }

            return fname;
        }

        return null;
    }

    /**
     * 创建row并填充
     */
    public void createAndFillRow(HSSFSheet sheet, String content, int col, int colOfCell){
        HSSFRow row1OfGroup = sheet.getRow(col)==null ? sheet.createRow(col) : sheet.getRow(col);
        HSSFCell cell1OfGroup = row1OfGroup.createCell(colOfCell);
        cell1OfGroup.setCellValue(content);
    }

    /**
     * 启动显示窗口
     */
    public void start(){
        this.setVisible(true);
    }

}
