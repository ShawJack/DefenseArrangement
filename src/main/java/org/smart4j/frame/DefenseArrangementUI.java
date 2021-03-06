package org.smart4j.frame;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.smart4j.ConfigConstant;
import org.smart4j.component.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Date;

/**
 * Created by ithink on 17-8-29.
 */
public class DefenseArrangementUI {

    private JPanel panel1;
    private JButton button1;
    private JTextField textField1;
    private JButton button2;
    private JTextField textField2;
    private JButton button3;
    private JTextField textField3;
    private JTextField textField4;
    private JButton button5;
    private JTextField textField5;

    public DefenseArrangementUI() {
        $$$setupUI$$$();

        button1.addActionListener(e -> {
                    JFileChooser jfc = new JFileChooser();
                    jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    jfc.showDialog(new JLabel(), "选择");
                    File file = jfc.getSelectedFile();
                    textField1.setText(file.getAbsolutePath());
                }
        );

        button2.addActionListener(e -> {
                    JFileChooser jfc = new JFileChooser();
                    jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    jfc.showDialog(new JLabel(), "选择");
                    File file = jfc.getSelectedFile();
                    textField2.setText(file.getAbsolutePath());
                }
        );

        button3.addActionListener(e -> {
                    JFileChooser jfc = new JFileChooser();
                    jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    jfc.showDialog(new JLabel(), "选择");
                    File file = jfc.getSelectedFile();
                    textField3.setText(file.getAbsolutePath());
                }
        );

        JDateChooser dateChooser = JDateChooser.getInstance(new Date());
        textField4.setBounds(0, 0, 10, 20);
        textField4.setText(ConfigConstant.DATE_FORMAT.format(new Date()));
        dateChooser.register(textField4);
        //dateChooser.register(textField5);
    }

    public static void main(String[] args) {

        DefenseArrangementUI defenseArrangementUI = new DefenseArrangementUI();
        defenseArrangementUI.start();
    }

    public void start() {

        JDateChooser dateChooser = JDateChooser.getInstance(new Date());
        textField4.setBounds(0, 0, 10, 20);
        textField4.setText(ConfigConstant.DATE_FORMAT.format(new Date()));
        dateChooser.register(textField4);

        JFrame frame = new JFrame("DefenseArrangementUI");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(5, 1, new Insets(1, 1, 1, 1), -1, -1));
        panel1.setBackground(new Color(-2694167));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-3287074));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        button1 = new JButton();
        button1.setBackground(new Color(-2171170));
        button1.setFont(new Font("YaHei Consolas Hybrid", button1.getFont().getStyle(), button1.getFont().getSize()));
        button1.setText("专家名单");
        panel2.add(button1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField1 = new JTextField();
        textField1.setBackground(new Color(-2171170));
        textField1.setEditable(false);
        textField1.setFont(new Font("YaHei Consolas Hybrid", textField1.getFont().getStyle(), textField1.getFont().getSize()));
        panel2.add(textField1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-3287074));
        panel1.add(panel3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        button2 = new JButton();
        button2.setBackground(new Color(-2171170));
        button2.setFont(new Font("YaHei Consolas Hybrid", button2.getFont().getStyle(), button2.getFont().getSize()));
        button2.setText("课程名单");
        panel3.add(button2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField2 = new JTextField();
        textField2.setBackground(new Color(-2171170));
        textField2.setEditable(false);
        textField2.setFont(new Font("YaHei Consolas Hybrid", textField2.getFont().getStyle(), textField2.getFont().getSize()));
        panel3.add(textField2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel4.setBackground(new Color(-3287074));
        panel1.add(panel4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        button3 = new JButton();
        button3.setBackground(new Color(-2171170));
        button3.setFont(new Font("YaHei Consolas Hybrid", button3.getFont().getStyle(), button3.getFont().getSize()));
        button3.setText("学生名册");
        panel4.add(button3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField3 = new JTextField();
        textField3.setBackground(new Color(-2171170));
        textField3.setEditable(false);
        textField3.setFont(new Font("YaHei Consolas Hybrid", textField3.getFont().getStyle(), textField3.getFont().getSize()));
        panel4.add(textField3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel5.setBackground(new Color(-3287074));
        panel1.add(panel5, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        textField4 = new JTextField();
        textField4.setBackground(new Color(-2171170));
        textField4.setEditable(false);
        textField4.setFont(new Font("YaHei Consolas Hybrid", textField4.getFont().getStyle(), textField4.getFont().getSize()));
        panel5.add(textField4, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setBackground(new Color(-3287074));
        label1.setFont(new Font("YaHei Consolas Hybrid", label1.getFont().getStyle(), label1.getFont().getSize()));
        label1.setForeground(new Color(-16777216));
        label1.setText("起始日期");
        panel5.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setBackground(new Color(-3287074));
        label2.setFont(new Font("YaHei Consolas Hybrid", label2.getFont().getStyle(), label2.getFont().getSize()));
        label2.setForeground(new Color(-16777216));
        label2.setText("结束日期");
        panel5.add(label2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField5 = new JTextField();
        textField5.setBackground(new Color(-2171170));
        textField5.setEditable(false);
        textField5.setFont(new Font("YaHei Consolas Hybrid", textField5.getFont().getStyle(), textField5.getFont().getSize()));
        panel5.add(textField5, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        panel6.setBackground(new Color(-3287074));
        panel1.add(panel6, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        button5 = new JButton();
        button5.setBackground(new Color(-2171170));
        button5.setFont(new Font("YaHei Consolas Hybrid", button5.getFont().getStyle(), button5.getFont().getSize()));
        button5.setText("生成答辩安排");
        panel6.add(button5);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
