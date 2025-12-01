package com.duckfox.jep.utils;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {
    /**
     * 将字符串列表用指定符号拼接，并在超过最大行长度时换行
     *
     * @param strings 要拼接的字符串列表
     * @param delimiter 连接符号（如", "、"|"等）
     * @param maxLineLength 每行最大字符数
     * @return 拼接并分割后的字符串（用换行符分割）
     */
    public static String joinAndSplit(List<String> strings, String delimiter, int maxLineLength) {
        if (strings == null || strings.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        StringBuilder currentLine = new StringBuilder();

        for (String str : strings) {
            // 如果添加当前字符串会超过行长度限制
            if (!currentLine.isEmpty() &&
                    currentLine.length() + delimiter.length() + str.length() > maxLineLength) {
                // 将当前行添加到结果中并换行
                result.append(currentLine.toString()).append("\n");
                currentLine = new StringBuilder();
            }

            // 如果不是第一个元素，添加分隔符
            if (!currentLine.isEmpty()) {
                currentLine.append(delimiter);
            }

            currentLine.append(str);
        }

        // 添加最后一行
        if (!currentLine.isEmpty()) {
            result.append(currentLine);
        }

        return result.toString();
    }

    /**
     * 将字符串列表用指定符号拼接，并在超过最大行长度时分割为多行
     *
     * @param strings 要拼接的字符串列表
     * @param delimiter 连接符号（如", "、"|"等）
     * @param maxLineLength 每行最大字符数
     * @return 分割后的字符串列表（每个元素是一行）
     */
    public static List<String> joinAndSplitToLines(List<String> strings, String delimiter, int maxLineLength) {
        List<String> result = new ArrayList<>();

        if (strings == null || strings.isEmpty()) {
            return result;
        }

        StringBuilder currentLine = new StringBuilder();

        for (String str : strings) {
            // 如果添加当前字符串会超过行长度限制
            if (!currentLine.isEmpty() &&
                    currentLine.length() + delimiter.length() + str.length() > maxLineLength) {
                // 将当前行添加到结果中并开始新行
                result.add(currentLine.toString());
                currentLine = new StringBuilder();
            }

            // 如果不是第一个元素，添加分隔符
            if (!currentLine.isEmpty()) {
                currentLine.append(delimiter);
            }

            currentLine.append(str);
        }

        // 添加最后一行
        if (!currentLine.isEmpty()) {
            result.add(currentLine.toString());
        }

        return result;
    }
}
