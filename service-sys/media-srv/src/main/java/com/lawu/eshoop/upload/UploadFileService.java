package com.lawu.eshoop.upload;

import com.lawu.eshop.utils.RandomUtil;
import com.lawu.eshop.utils.ValidateUtil;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.lawu.eshop.utils.RandomUtil;
import com.lawu.eshop.utils.ValidateUtil;

/**
 *
 * Created by zhangyong on 2017/3/27.
 */
public class UploadFileService {

    public static Map<String, String> uploadStoreImages(HttpServletRequest request) {
        Map<String, String> valsMap = new HashMap<>();
        valsMap.put("resultFlag", "OK");
        String basePath = request.getServletContext().getRealPath("/");
        System.out.println(basePath);
        StringBuffer idNumberUrls = new StringBuffer();        //身份证照
        StringBuffer storeUrls = new StringBuffer();        //门店照
        StringBuffer storeEnvUrls = new StringBuffer();        //店内环境照
        StringBuffer storeLogoUrls = new StringBuffer();    //店铺logo
        StringBuffer licenseUrls = new StringBuffer();        //营业执照
        StringBuffer otherUrls = new StringBuffer();        //其他许可证
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    String originalFilename = file.getOriginalFilename();
                    String fieldName = file.getName();
                    if (originalFilename != null && !"".equals(originalFilename)) {
                        String prefix = originalFilename.substring(originalFilename.lastIndexOf("."));
                        prefix = prefix.toLowerCase();
                        String newfileName = RandomUtil.buildFileName(prefix);
                        if (!ValidateUtil.validateImageFormat(prefix)) {
                            valsMap.put("resultFlag", "false");
                            valsMap.put("msg", "上传图片格式错误");
                            return valsMap;
                        }
                        try {
                            //设置门店图片路径
                            String imagePath = "";//StringUtil.getUploadImagePath("store").toString().replace("\\", "/");
                            String newFileUploadPath = request.getSession().getServletContext().getRealPath(imagePath);
                            File localFile = new File(newFileUploadPath + newfileName);
                            if (!localFile.getParentFile().exists()) {
                                localFile.getParentFile().mkdirs();
                            }
                            file.transferTo(localFile);
                            if (fieldName.contains("store")) {
                                storeUrls.append(basePath + imagePath + newfileName + "|").append(basePath + imagePath + newfileName + ",");
                                valsMap.put("storeUrl", storeUrls.toString());
                            } else if (fieldName.contains("environment")) {
                                storeEnvUrls.append(basePath + imagePath + newfileName + "|").append(basePath + imagePath + newfileName + ",");
                                valsMap.put("environmentUrl", storeEnvUrls.toString());
                            } else if (fieldName.contains("logo")) {
                                storeLogoUrls.append(basePath + imagePath + newfileName + "|").append(basePath + imagePath + newfileName + ",");
                                valsMap.put("logoUrl", storeLogoUrls.toString());
                            } else if (fieldName.contains("idcard")) {
                                idNumberUrls.append(basePath + imagePath + newfileName + "|").append(basePath + imagePath + newfileName + ",");
                                valsMap.put("idcardUrl", idNumberUrls.toString());
                            } else if (fieldName.contains("licence")) {
                                licenseUrls.append(basePath + imagePath + newfileName + "|").append(basePath + imagePath + newfileName + ",");
                                valsMap.put("licenceUrl", licenseUrls.toString());
                            } else if (fieldName.contains("other")) {
                                otherUrls.append(basePath + imagePath + newfileName + "|").append(basePath + imagePath + newfileName + ",");
                                valsMap.put("otherUrl", otherUrls.toString());

                            }
                            valsMap.put("resultFlag", "OK");

                        } catch (Exception e) {
                            e.printStackTrace();
                            valsMap.put("resultFlag", "false");
                            valsMap.put("msg", "上传图片失败");

                        }
                    }
                }
            }
        }
        return valsMap;
    }

    /**
     *
     * @param request
     * @param file
     * @param dir	业务的图片文件夹，值参考FileDirConstant
     * @return
     */
    public Map<String, String> uploadOnePic(HttpServletRequest request,MultipartFile file,String dir) {
        Map<String, String> valsMap = new HashMap<>();
        valsMap.put("resultFlag", "0");
        String basePath = request.getServletContext().getRealPath("/");//根路径
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
            if(fileSize>0.5*1048576){
                valsMap.put("resultFlag", "1015");
                valsMap.put("msg", "图片文件大于500K");
                return valsMap;
            }
            try {
                String imagePath = "";//StringUtil.getUploadImagePath(dir).toString().replace("\\", "/");
                String newFileUploadPath = request.getSession().getServletContext().getRealPath(imagePath);
                File localFile = new File(newFileUploadPath + newfileName);
                if (!localFile.getParentFile().exists()) {
                    localFile.getParentFile().mkdirs();
                }
                file.transferTo(localFile);
                valsMap.put("imgUrl",basePath + imagePath + newfileName );
                valsMap.put("resultFlag", "0");

            } catch (Exception e) {
                e.printStackTrace();
                valsMap.put("resultFlag", "1010");
                valsMap.put("msg", "上传图片失败");
            }
        }
        return valsMap;
    }


}
