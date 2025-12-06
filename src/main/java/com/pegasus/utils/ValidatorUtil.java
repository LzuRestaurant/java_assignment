package com.pegasus.utils;

import java.util.regex.Pattern;

// 校验工具类，用于校验excel导入的数据是否合法
public class ValidatorUtil {
    // 正则
    private static final Pattern ID_8_DIGITS = Pattern.compile("^\\d{8}$");

    /**
     * 校验医生数据
     * @param id 医生ID
     * @param name 姓名
     * @param pwd 密码
     * @param dept 科室
     * @param spec 专长
     * @throws IllegalArgumentException 如果校验失败，抛出带错误信息的异常
     */
    public static void validateDoctor(Long id, String name, String pwd, String dept, String spec) {
        // 1. 校验 ID (8位数字)
        if (id == null || !ID_8_DIGITS.matcher(String.valueOf(id)).matches()) {
            throw new IllegalArgumentException("医生ID [" + id + "] 必须是8位数字");
        }

        // 2. 校验姓名 (最多20字符)
        if (name == null || name.length() > 20) {
            throw new IllegalArgumentException("姓名 [" + name + "] 长度不能超过20字符");
        }

        // 3. 校验密码 (4-20位)
        if (pwd == null || pwd.length() < 4 || pwd.length() > 20) {
            throw new IllegalArgumentException("密码长度必须在4-20位之间");
        }

        // 4. 校验科室 (最多30字符)
        if (dept == null || dept.length() > 30) {
            throw new IllegalArgumentException("科室名称长度不能超过30字符");
        }

        // 5. 校验专长 (最多200字符)
        if (spec != null && spec.length() > 200) {
            throw new IllegalArgumentException("专长描述不能超过200字符");
        }
    }
}