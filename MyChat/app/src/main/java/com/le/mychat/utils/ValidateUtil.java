package com.le.mychat.utils;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ValidateUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm",
            Locale.CHINA);
    private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd",
            Locale.CHINA);

    /**
     * 验证EditText内容是否为空
     * <br>
     * note:不为空返回true
     *
     * @param ets
     * @return
     */
    public static boolean isNotEmpty(EditText... ets) {
        for (EditText et : ets) {
            if ("".equals(et.getText().toString().trim())) {
                et.setError("不能为空");
                et.requestFocus();
                return false;
            }
        }
        return true;
    }

    public static boolean isAfter(TextView date1, TextView date2, String msg) {
        boolean b = false;
        try {
            b = sdf1.parse(date2.getText().toString()).after(
                    sdf1.parse(date1.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!b) {
            Toast.makeText(date2.getContext(), msg, Toast.LENGTH_SHORT).show();
        }
        return b;
    }

    public static boolean isAfterNow(TextView date) {
        boolean b = false;
        try {
            b = sdf.parse(date.getText().toString()).after(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static boolean isAfterNow(TextView date, SimpleDateFormat simpleDateFormat) {
        return date.getText().toString().compareTo(simpleDateFormat.format(new Date())) >= 0;
    }

    /**
     * @param et
     * @param msg
     * @return
     */
    public static boolean isAddressOk(EditText et, String msg) {
        int i = 0;
        try {
            i = Integer.parseInt(et.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (i < 1 || i > 255) {
            Toast.makeText(et.getContext(), msg, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean isPhoneNoOk(EditText et) {
        String no = et.getText().toString();
        if (no.matches("\\d{11}")) {
            return true;
        }
        et.setError("电话号码必须为11位数字");
        return false;
    }

    public static List<String> findNumber(CharSequence text) {
        Pattern p = Pattern.compile("(\\-|\\+)?\\d+(\\.\\d+)?");
        Matcher m = p.matcher(text);
        List<String> nums = new ArrayList<String>();
        while (m.find()) {
            nums.add(m.group(0));
        }
        return nums;
    }

}
