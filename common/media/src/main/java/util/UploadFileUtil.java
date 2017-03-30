package util;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.lawu.eshop.utils.RandomUtil;
import com.lawu.eshop.utils.ValidateUtil;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传工具类
 * Created by zhangyong on 2017/3/29.
 */
public class UploadFileUtil {
	/**
	 * @param request
	 * @param file
	 *            单个文件
	 * @param dir
	 *            业务的图片文件夹，值参考com.lawu.eshop.framework.web.constants.
	 *            FileDirConstant
	 * @return resultFlag参考ResultCode
	 */
	public static Map<String, String> uploadOnePic(HttpServletRequest request, MultipartFile file, String dir) {
		Map<String, String> valsMap = new HashMap<>();
		// 设置默认返回类型成功
		valsMap.put("resultFlag", "0");
		String basePath = request.getServletContext().getRealPath("/");// 根路径
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
				valsMap.put("imgUrl", basePath + imagePath + newfileName);
				valsMap.put("resultFlag", "0");

			} catch (Exception e) {
				e.printStackTrace();
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
}
