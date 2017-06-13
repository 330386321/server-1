package util;

import com.lawu.eshop.utils.RandomUtil;
import com.lawu.eshop.utils.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传工具类
 * Created by zhangyong on 2017/3/29.
 */
public class UploadFileUtil {
    private static Logger logger = LoggerFactory.getLogger(UploadFileUtil.class);

    private static final  String mapKey = "resultFlag";
    private static final  String mapValue = "0";
    private UploadFileUtil(){

    }

    /**
     * 文件上传单张图片
     *
     * @param request
     * @param dir     文件存放目录
     * @return
     */
    public static Map<String, String> uploadOneImage(HttpServletRequest request, String dir, String baseImageDir) {
        Map<String, String> valsMap = new HashMap<>();
        // 设置默认返回类型成功
        valsMap.put(mapKey, mapValue);
        //String bashdir = baseImageDir;// 根路径
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
                        valsMap.put(mapKey, "1011");
                        valsMap.put("msg", "上传图片格式错误");
                        return valsMap;
                    }
                    // 1M=1024k=1048576字节
                    long fileSize = part.getSize();
                    if (fileSize > 5 * 1048576) {
                        valsMap.put(mapKey, "1015");
                        valsMap.put("msg", "图片文件大于5M");
                        return valsMap;
                    }
                    File file = new File(baseImageDir, dir);
                    if (!file.exists()) {
                        file.mkdirs();
                    }

                    InputStream in = part.getInputStream();
                    out = new FileOutputStream(new File(file, newfileName));

                    while (in.read(b) != -1) {
                        out.write(b, 0, in.read(b));
                    }

                    in.close();

                    //文件路径，文件类型
                    urlImg =   dir + File.separator + newfileName;

                }
            }
            valsMap.put("imgUrl", urlImg);
            valsMap.put(mapKey, mapValue);
        } catch (Exception e) {
            valsMap.put(mapKey, "1010");
            valsMap.put("msg", "上传图片失败");
            logger.error("上传图片失败");
        }finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                logger.error("out stream close exception");
            }

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
    public static Map<String, String> uploadImages(HttpServletRequest request, String dir, Part part, String baseImageDir) {
        Map<String, String> valsMap = new HashMap<>();
        // 设置默认返回类型成功
        valsMap.put(mapKey, mapValue);
        //String bashdir = baseImageDir;// 根路径
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
                valsMap.put(mapKey, "1011");
                valsMap.put("msg", "上传图片格式错误");
                return valsMap;
            }
            // 1M=1024k=1048576字节
            long fileSize = part.getSize();
            if (fileSize > 0.5 * 1048576) {
                valsMap.put(mapKey, "1015");
                valsMap.put("msg", "图片文件大于500K");
                return valsMap;
            }
            File file = new File(baseImageDir, dir);
            if (!file.exists()) {
                file.mkdirs();
            }

            InputStream in = null;
            try {
                in = part.getInputStream();
                out = new FileOutputStream(new File(file, newfileName));
                while (in.read(b) != -1) {
                    out.write(b, 0, in.read(b));
                }
                in.close();

                //文件路径，文件类型
                urlImg =   dir + File.separator + newfileName;
            } catch (IOException e) {
                logger.error("上传图片失败");
            }
            finally {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    logger.error("out stream close exception");
                }

            }
        }
        valsMap.put("imgUrl", urlImg);
        valsMap.put(mapKey, mapValue);

        return valsMap;

    }

    public static Map<String, String> uploadVideo(HttpServletRequest request, String dir, String baseVideoDir) {
        Map<String, String> valsMap = new HashMap<>();
        // 设置默认返回类型成功
        valsMap.put(mapKey, mapValue);
        //String bashdir = baseVideoDir;// 上传文件根路径
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
                        valsMap.put(mapKey, "1021");
                        valsMap.put("msg", "文件大于50M");
                        return valsMap;
                    }
                    File file = new File(baseVideoDir, dir);
                    if (!file.exists()) {
                        file.mkdirs();
                    }

                    InputStream in = part.getInputStream();
                    out = new FileOutputStream(new File(file, newfileName));

                    while (in.read(b) != -1) {
                        out.write(b, 0, in.read(b));
                    }
                    in.close();

                    //文件路径，文件类型
                    videoUrl =  dir + File.separator + newfileName;

                }
            }
            valsMap.put("videoUrl", videoUrl);
            valsMap.put(mapKey, mapValue);
        } catch (Exception e) {
            valsMap.put(mapKey, "1020");
            valsMap.put("msg", "上传视频失败");
            logger.info("上传视频失败");
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                logger.error("out stream close exception");
            }

        }
        return valsMap;

    }
    

}
