package util;

import com.lawu.eshop.utils.RandomUtil;
import com.lawu.eshop.utils.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传工具类
 * Created by zhangyong on 2017/3/29.
 */
public class UploadFileUtil {
    private static Logger logger = LoggerFactory.getLogger(UploadFileUtil.class);

    private static final String baseImageDir =File.separator+"usr"+File.separator+"local"+File.separator+"media"+File.separator+"images";

    private static final String baseVideoDir =File.separator+"usr"+File.separator+"local"+File.separator+"media"+File.separator+"videos";
    /**
     * @param request
     * @param file    单个文件
     * @param dir     业务的图片文件夹，值参考com.lawu.eshop.framework.web.constants.
     *                FileDirConstant
     * @return resultFlag参考ResultCode
     */
    public static Map<String, String> uploadOnePic(HttpServletRequest request, MultipartFile file, String dir) {
        Map<String, String> valsMap = new HashMap<>();
        // 设置默认返回类型成功
        valsMap.put("resultFlag", "0");
        String basePath = baseImageDir;// 根路径
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && !"".equals(originalFilename)) {
            String prefix = originalFilename.substring(originalFilename.lastIndexOf("."));
            prefix = prefix.toLowerCase();
            String newfileName = RandomUtil.buildFileName(prefix);
            if (!ValidateUtil.validateImageFormat(prefix)) {
                valsMap.put("resultFlag", "1011");
                valsMap.put("msg", "上传图片格式错误");
                return valsMap;
            }
            // 1M=1024k=1048576字节
            long fileSize = file.getSize();
            if (fileSize > 0.5 * 1048576) {
                valsMap.put("resultFlag", "1015");
                valsMap.put("msg", "图片文件大于500K");
                return valsMap;
            }
            try {
                String imagePath = getUploadImagePath(dir).toString().replace("\\", "/");
                String newFileUploadPath = request.getSession().getServletContext().getRealPath(imagePath);
                File localFile = new File(newFileUploadPath + newfileName);
                if (!localFile.getParentFile().exists()) {
                    localFile.getParentFile().mkdirs();
                }
                file.transferTo(localFile);
                valsMap.put("imgUrl",imagePath + newfileName);
                valsMap.put("resultFlag", "0");

            } catch (Exception e) {
                logger.info("上传图片失败");
                valsMap.put("resultFlag", "1010");
                valsMap.put("msg", "上传图片失败");
            }
        }
        return valsMap;
    }

    /**
     * 获取image上传路径
     *
     * @return
     */
    public static StringBuffer getUploadImagePath(String dir) {
        Calendar toDay = Calendar.getInstance();
        StringBuffer fileUploadPath = new StringBuffer();
        int intMonth = toDay.get(Calendar.MONTH) + 1;
        String strMonth = "";
        if (intMonth < 10) {
            strMonth = "0" + String.valueOf(intMonth);
        } else {
            strMonth = String.valueOf(intMonth);
        }
        fileUploadPath.append(File.separator).append(dir).append(File.separator).append("image").append(File.separator)
                .append(toDay.get(Calendar.YEAR)).append(File.separator).append(strMonth)
                .append(toDay.get(Calendar.DATE)).append(File.separator);

        return fileUploadPath;
    }

    /**
     * 文件上传单张图片
     *
     * @param request
     * @param dir     文件存放目录
     * @return
     */
    public static Map<String, String> uploadOneImage(HttpServletRequest request, String dir) {
        Map<String, String> valsMap = new HashMap<>();
        // 设置默认返回类型成功
        valsMap.put("resultFlag", "0");
        String bashdir = baseImageDir;// 根路径
        FileOutputStream out = null;
        byte b[] = new byte[1024 * 1024];
        Collection<Part> parts;

        String urlImg = "";
        try {
            parts = request.getParts();
            for (Part part : parts) {
                if (part.getContentType() == null) {
                    valsMap.put(part.getName(), request.getParameter(part.getName()));
                } else {

                    String extension = part.getSubmittedFileName();
                    extension = extension.substring(extension.lastIndexOf("."));
                    String newfileName = RandomUtil.buildFileName(extension);
                    if (!ValidateUtil.validateImageFormat(extension)) {
                        valsMap.put("resultFlag", "1011");
                        valsMap.put("msg", "上传图片格式错误");
                        return valsMap;
                    }
                    // 1M=1024k=1048576字节
                    long fileSize = part.getSize();
                    if (fileSize > 0.5 * 1048576) {
                        valsMap.put("resultFlag", "1015");
                        valsMap.put("msg", "图片文件大于500K");
                        return valsMap;
                    }
                    File file = new File(bashdir, dir);
                    if (!file.exists()) {
                        file.mkdirs();
                    }

                    InputStream in = part.getInputStream();
                    out = new FileOutputStream(new File(file, newfileName));

                    int index = 0;
                    while ((index = in.read(b)) != -1) {
                        out.write(b, 0, index);
                    }
                    out.flush();
                    out.close();
                    in.close();

                    //文件路径，文件类型
                    urlImg =   dir + File.separator + newfileName;

                }
            }
            valsMap.put("imgUrl", urlImg);
            valsMap.put("resultFlag", "0");
        } catch (Exception e) {
            valsMap.put("resultFlag", "1010");
            valsMap.put("msg", "上传图片失败");
            logger.error("上传图片失败");
        }
        return valsMap;

    }


    /**
     * 上传多张图片
     *
     * @param request
     * @param dir
     * @param part
     * @return
     */
    public static Map<String, String> uploadImages(HttpServletRequest request, String dir, Part part) {
        Map<String, String> valsMap = new HashMap<>();
        // 设置默认返回类型成功
        valsMap.put("resultFlag", "0");
        String bashdir = baseImageDir;// 根路径
        FileOutputStream out = null;
        byte b[] = new byte[1024 * 1024];
        String urlImg = "";
        if (part.getContentType() == null) {
            valsMap.put(part.getName(), request.getParameter(part.getName()));
        } else {

            String extension = part.getSubmittedFileName();
            extension = extension.substring(extension.lastIndexOf("."));
            String newfileName = RandomUtil.buildFileName(extension);
            if (!ValidateUtil.validateImageFormat(extension)) {
                valsMap.put("resultFlag", "1011");
                valsMap.put("msg", "上传图片格式错误");
                return valsMap;
            }
            // 1M=1024k=1048576字节
            long fileSize = part.getSize();
            if (fileSize > 0.5 * 1048576) {
                valsMap.put("resultFlag", "1015");
                valsMap.put("msg", "图片文件大于500K");
                return valsMap;
            }
            File file = new File(bashdir, dir);
            if (!file.exists()) {
                file.mkdirs();
            }

            InputStream in = null;
            try {
                in = part.getInputStream();
                out = new FileOutputStream(new File(file, newfileName));
                int index = 0;
                while ((index = in.read(b)) != -1) {
                    out.write(b, 0, index);
                }

                out.flush();
                out.close();
                in.close();
                //文件路径，文件类型
                urlImg =   dir + File.separator + newfileName;
            } catch (IOException e) {
                logger.error("上传图片失败");
            }
        }
        valsMap.put("imgUrl", urlImg);
        valsMap.put("resultFlag", "0");

        return valsMap;

    }

    /**
     * 文件上传单张图片
     *
     * @param request
     * @param dir         文件存放目录
     * @param newfileName 文件名
     * @return
     */
    public static Map<String, String> uploadOneImage(HttpServletRequest request, String dir, String newfileName) {
        Map<String, String> valsMap = new HashMap<>();
        // 设置默认返回类型成功
        valsMap.put("resultFlag", "0");
        String bashdir = baseImageDir;// 根路径
        FileOutputStream out = null;
        byte b[] = new byte[1024 * 1024];
        Collection<Part> parts;

        String urlImg = "";
        try {
            parts = request.getParts();
            for (Part part : parts) {
                if (part.getContentType() == null) {
                    valsMap.put(part.getName(), request.getParameter(part.getName()));
                } else {

                    String extension = part.getSubmittedFileName();
                    extension = extension.substring(extension.lastIndexOf("."));
                    newfileName = newfileName + ".jpg";
                    if (!ValidateUtil.validateImageFormat(extension)) {
                        valsMap.put("resultFlag", "1011");
                        valsMap.put("msg", "上传图片格式错误");
                        return valsMap;
                    }
                    // 1M=1024k=1048576字节
                    long fileSize = part.getSize();
                    if (fileSize > 0.5 * 1048576) {
                        valsMap.put("resultFlag", "1015");
                        valsMap.put("msg", "图片文件大于500K");
                        return valsMap;
                    }
                    File file = new File(bashdir, dir);
                    if (!file.exists()) {
                        file.mkdirs();
                    }

                    InputStream in = part.getInputStream();
                    out = new FileOutputStream(new File(file, newfileName));

                    int index = 0;
                    while ((index = in.read(b)) != -1) {
                        out.write(b, 0, index);
                    }
                    out.flush();
                    out.close();
                    in.close();

                    //文件路径，文件类型
                    urlImg = dir + File.separator + newfileName;

                }
            }
            valsMap.put("imgUrl", urlImg);
            valsMap.put("resultFlag", "0");
        } catch (Exception e) {
            valsMap.put("resultFlag", "1010");
            valsMap.put("msg", "上传图片失败");
            logger.info("上传图片失败");
        }
        return valsMap;

    }


    public static Map<String, String> uploadVideo(HttpServletRequest request, String dir) {
        Map<String, String> valsMap = new HashMap<>();
        // 设置默认返回类型成功
        valsMap.put("resultFlag", "0");
        String bashdir = baseVideoDir;// 上传文件根路径
        FileOutputStream out = null;
        byte b[] = new byte[1024 * 1024];
        Collection<Part> parts;

        String videoUrl = "";
        try {
            parts = request.getParts();
            for (Part part : parts) {
                if (part.getContentType() == null) {
                    valsMap.put(part.getName(), request.getParameter(part.getName()));
                } else {

                    String extension = part.getSubmittedFileName();
                    extension = extension.substring(extension.lastIndexOf("."));
                    String newfileName = RandomUtil.buildFileName(extension);

                    // 1M=1024k=1048576字节
                    long fileSize = part.getSize();
                    if (fileSize > 50 * 1048576) {
                        valsMap.put("resultFlag", "1021");
                        valsMap.put("msg", "文件大于50M");
                        return valsMap;
                    }
                    File file = new File(bashdir, dir);
                    if (!file.exists()) {
                        file.mkdirs();
                    }

                    InputStream in = part.getInputStream();
                    out = new FileOutputStream(new File(file, newfileName));

                    int index = 0;
                    while ((index = in.read(b)) != -1) {
                        out.write(b, 0, index);
                    }
                    out.flush();
                    out.close();
                    in.close();

                    //文件路径，文件类型
                    videoUrl =  dir + File.separator + newfileName;

                }
            }
            valsMap.put("videoUrl", videoUrl);
            valsMap.put("resultFlag", "0");
        } catch (Exception e) {
            valsMap.put("resultFlag", "1020");
            valsMap.put("msg", "上传视频失败");
            logger.info("上传视频失败");
        }
        return valsMap;

    }


}
