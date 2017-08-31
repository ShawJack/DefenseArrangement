package org.smart4j;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ithink on 17-8-28.
 */
public interface ConfigConstant {

    String[] WEEKDAY = new String[]{"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat WEEK_FORMAT = new SimpleDateFormat("EEEE");
    Set<String> CHAIRS = new HashSet<>(Arrays.asList(new String[]{"曾明", "朱利", "陈建明", "金莉", "赵银亮", "曲桦", "宋永红", "刘跃虎", "吴晓军"}));
    String ROOT_PATH = "/home/ithink/java/DefenseArrangement/src/main/resources/";
    Font FONT = new Font("YaHei Consolas Hybrid", Font.PLAIN, 12);

}
