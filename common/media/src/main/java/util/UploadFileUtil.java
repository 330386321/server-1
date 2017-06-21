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

    private static final  String MAP_KEY = "resultFlag";
    private static final  String MAP_KEY_MSG = "msg";
    private static final  String mapValue = "0";
    private static final String IMAGE_FORMAT_WRONG_UPLOAD = "1011";
    private static final String IMAGE_WRONG_UPLOAD = "1010";
    private static final String IMAGE_SIZE_ERROR = "1015";
    private static final String UPLOAD_SIZE_BIGER = "1021";
    private static final String UPLOAD_VEDIO_FAIL = "1020";

    private static final String IMAGE_FORMAT_WRONG_UPLOAD_MSG = "上传图片格式错误";
    private static final String UPLOAD_SIZE_BIGER_MSG = "图片文件太大";
    private static final String UPLOAD_FAIL = "上传失败";
    private static final String UPLOAD_VIDEO_URL = "videoUrl";
    private static final String UPLOAD_IMG_URL = "imgUrl";
    private UploadFileUtil(){

    }

    /**
     * 文件上传单张图片
     *
     * @param request
     * @param dir     文件存放目录
     * @return
     */
    public static Map<String, String> uploadOneImage(HttpServletRequest request,
                                                     String dir, String baseImageDir) {
        Map<String, String> valsMap = new HashMap<>();
        // 设置默认返回类型成功
        valsMap.put(MAP_KEY, mapValue);
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
                        valsMap.put(MAP_KEY, IMAGE_FORMAT_WRONG_UPLOAD);
                        valsMap.put(MAP_KEY_MSG, IMAGE_FORMAT_WRONG_UPLOAD_MSG);
                        return valsMap;
                    }
                    // 1M=1024k=1048576字节
                    long fileSize = part.getSize();
                    if (fileSize > 5 * 1048576) {
                        valsMap.put(MAP_KEY, IMAGE_SIZE_ERROR);
                        valsMap.put(MAP_KEY_MSG, UPLOAD_SIZE_BIGER_MSG);
                        return valsMap;
                    }
                    File file = new File(baseImageDir, dir);
                    if (!file.exists()) {
                        file.mkdirs();
                    }

                    InputStream in = part.getInputStream();
                    out = new FileOutputStream(new File(file, newfileName));
                    int index = 0;
                    while ((index = in.read(b)) != -1) {
                        out.write(b, 0, index);
                    }
                    in.close();
                    out.flush();
                    //文件路径，文件类型
                    urlImg =   dir + File.separator + newfileName;

                }
            }
            valsMap.put(UPLOAD_IMG_URL, urlImg);
            valsMap.put(MAP_KEY, mapValue);
        } catch (Exception e) {
            valsMap.put(MAP_KEY, IMAGE_WRONG_UPLOAD);
            valsMap.put(MAP_KEY_MSG, UPLOAD_FAIL);
            logger.info(UPLOAD_FAIL);
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                logger.info("out stream close exception");
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
        valsMap.put(MAP_KEY, mapValue);
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
                valsMap.put(MAP_KEY, IMAGE_FORMAT_WRONG_UPLOAD);
                valsMap.put(MAP_KEY_MSG, IMAGE_FORMAT_WRONG_UPLOAD_MSG);
                return valsMap;
            }
            // 1M=1024k=1048576字节
            long fileSize = part.getSize();
            if (fileSize > 0.5 * 1048576) {
                valsMap.put(MAP_KEY, IMAGE_SIZE_ERROR);
                valsMap.put(MAP_KEY_MSG, UPLOAD_SIZE_BIGER_MSG);
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
                int index = 0;
                while ((index = in.read(b)) != -1) {
                    out.write(b, 0, index);
                }
                in.close();
                out.flush();
                //文件路径，文件类型
                urlImg =   dir + File.separator + newfileName;
            } catch (IOException e) {
                logger.info(UPLOAD_FAIL);
            }
            finally {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.info("out stream close exception");
                }

            }
        }
        valsMap.put(UPLOAD_IMG_URL, urlImg);
        valsMap.put(MAP_KEY, mapValue);

        return valsMap;

    }

    public static Map<String, String> uploadVideo(HttpServletRequest request, String dir, String baseVideoDir) {
        Map<String, String> valsMap = new HashMap<>();
        // 设置默认返回类型成功
        valsMap.put(MAP_KEY, mapValue);
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
                        valsMap.put(MAP_KEY, UPLOAD_SIZE_BIGER);
                        valsMap.put(MAP_KEY_MSG, UPLOAD_SIZE_BIGER_MSG);
                        return valsMap;
                    }
                    File file = new File(baseVideoDir, dir);
                    if (!file.exists()) {
                        file.mkdirs();
                    }

                    InputStream in = part.getInputStream();
                    out = new FileOutputStream(new File(file, newfileName));

                    int index = 0;
                    while ((index = in.read(b)) != -1) {
                        out.write(b, 0, index);
                    }
                    in.close();
                    out.flush();
                    //文件路径，文件类型
                    videoUrl =  dir + File.separator + newfileName;

                }
            }
            valsMap.put(UPLOAD_VIDEO_URL, videoUrl);
            valsMap.put(MAP_KEY, mapValue);
        } catch (Exception e) {
            valsMap.put(MAP_KEY, UPLOAD_VEDIO_FAIL);
            valsMap.put(MAP_KEY_MSG, UPLOAD_FAIL);
            logger.info(UPLOAD_FAIL);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                logger.info("out stream close exception");
            }

        }
        return valsMap;

    }
    

}
