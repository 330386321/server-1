package com.lawu.eshop.utils;

import java.io.File;
import java.util.Calendar;

/**
 *
 * Created by zhangyong on 2017/3/27.
 */
public class StringUtil {
    /**
     * 获取image上传路径
     *
     * @return
     */
    public static final StringBuffer getUploadImagePath(String dir) {
        Calendar toDay = Calendar.getInstance();
        StringBuffer fileUploadPath = new StringBuffer();
        int intMonth = toDay.get(Calendar.MONTH) + 1;
        String strMonth = "";
        if (intMonth < 10)
            strMonth = "0" + String.valueOf(intMonth);
        else
            strMonth = String.valueOf(intMonth);
        fileUploadPath.append(File.separator).append(dir)
                .append(File.separator).append("image").append(File.separator)
                .append(toDay.get(Calendar.YEAR)).append(File.separator)
                .append(strMonth).append(toDay.get(Calendar.DATE))
                .append(File.separator);

        return fileUploadPath;
    }

    public static void main(String[] args) {
        System.out.println(getUploadImagePath("store").toString().replace("\\", "/"));
    }
}
