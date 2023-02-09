package com.chenghan.keepaccounts.common;

import android.content.Context;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StringUtils {
    /**
     * 获取图片资源
     * @param imageName 图片名
     * @return
     */
    public static int getResourceId(Context context, String imageName) {
        String packageName = "com.chenghan.keepaccounts";
        int imageId = context.getResources().getIdentifier(imageName, "mipmap", packageName);
        return imageId;
    }

    /**
     * 生成随机数字和字母组合字符串
     * @param length [生成随机数的长度]
     * @return
     */
    public static String getRandomNickname(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    /**
     * 字符串加密
     * @param strSrc 要加密的字符串
     * @return
     */
    public static String MD5(String strSrc) {
        try {
            char hexChars[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
                    '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            byte[] bytes = strSrc.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            bytes = md.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                chars[k++] = hexChars[b >>> 4 & 0xf];
                chars[k++] = hexChars[b & 0xf];
            }
            return new String(chars);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("MD5加密出错！！+" + e);
        }
    }



    /**
     * 字符串计算
     * @param expression
     * @return
     */
    public static double calculator(String expression) {
        if (expression == null || "".equals(expression)) {
            return 0.0;
        }

        if (!(expression.contains("+") || expression.contains("-"))) {
            return Double.parseDouble(expression);
        }

        String exp = ""; // 去括号后的表达式
        // 是否有括号
        if (expression.contains("(") && expression.contains(")")) {
            int starIndex = expression.indexOf("("); // 第一次出现括号的下标
            if (starIndex > 0) {
                exp = expression.substring(0, starIndex);
            }
            expression = expression.substring(starIndex + 1);

            int num = 0; // 括号里还有括号的次数
            for (int i = 0; i < expression.length(); i++) {
                if ("(".equals(expression.charAt(i) + "")) {
                    num++;
                }else if (")".equals(expression.charAt(i) + "") && num == 0) { // 结束括号
                    double value = calculator(expression.substring(0, i));
                    exp += value;
                    if (i + 1 < expression.length()) {
                        expression = expression.substring(i + 1);
                        starIndex = expression.indexOf("(");
                        if (starIndex != -1) { // 后面表达式存在括号
                            if (starIndex > 0) {
                                exp += expression.substring(0, starIndex);
                            }
                            expression = expression.substring(starIndex + 1);
                            i = -1;
                        }else { // 后面表达式没有括号
                            exp += expression;
                            break;
                        }
                    }
                }else if (")".equals(expression.charAt(i) + "")) {
                    num--;
                }
            }
        }else { // 没有括号
            exp = expression;
        }

        // 表达式拆分到集合
        List<String> list = new ArrayList<>();
        String chars = "";
        for (int i = 0; i < exp.length(); i++) {
            chars = exp.charAt(i) + "";
            if ("-".equals(chars) || "+".equals(chars) || "*".equals(chars) || "/".equals(chars)) {
                if ("-".equals(chars) && list.size() == 0 && i == 0) {
                    list.add("0");
                }else if (i != 0) {
                    list.add(exp.substring(0, i));
                }
                list.add(chars);
                exp = exp.substring(i + 1);
                i = -1;
            }else if (i + 1 >= exp.length()) {
                list.add(exp);
                break;
            }
        }

        // 计算表达式（先乘除后加减）
        double results = 0.0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < list.size(); j++) {
                if (i == 0) {
                    if ("*".equals(list.get(j))) {
                        results = Double.parseDouble(list.get(j-1)) * Double.parseDouble(list.get(j+1));
                    }else if ("/".equals(list.get(j))) {
                        results = Double.parseDouble(list.get(j-1)) / Double.parseDouble(list.get(j+1));
                    }
                    if ("*".equals(list.get(j)) || "/".equals(list.get(j))) {
                        list.remove(j + 1);
                        list.remove(j);
                        list.remove(j - 1);
                        list.add(j - 1, results + "");
                        j--;
                    }
                }else {
                    if ("+".equals(list.get(j))) {
                        results = Double.parseDouble(list.get(j-1)) + Double.parseDouble(list.get(j+1));
                    }else if ("-".equals(list.get(j))) {
                        results = Double.parseDouble(list.get(j-1)) - Double.parseDouble(list.get(j+1));
                    }
                    if ("+".equals(list.get(j)) || "-".equals(list.get(j))) {
                        list.remove(j + 1);
                        list.remove(j);
                        list.remove(j - 1);
                        list.add(j - 1, results + "");
                        j--;
                    }
                }
            }
        }
        // 结果保留两位小时
        results = (double) Math.round(results * 100) / 100;
        return results;
    }
}


