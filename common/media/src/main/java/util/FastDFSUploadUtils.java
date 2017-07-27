/**
 * 
 */
package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.csource.fastdfs.ClientParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.lawu.eshop.utils.RandomUtil;

/**
 * FastDFS上传工具
 * 
 * @author lihj
 * @date 2017年7月25日
 */
public class FastDFSUploadUtils {

	private static Logger log = LoggerFactory.getLogger(FastDFSUploadUtils.class);

	public static FastDFSResult upload(HttpServletRequest request, UploadParam uparam) {
		FastDFSResult result = new FastDFSResult();
		Map<String, String> map = new HashMap<String, String>();
		Collection<Part> parts = null;
		try {
			parts = request.getParts();
		} catch (Exception e) {
			log.error("获取文件信息异常" + e.getMessage());
			result.setFenum(FastDFSResultEnum.FD_FILE_ERROR);
			return result;
		}
		for (Part part : parts) {
			InputStream in = null;
			if (part.getContentType() != null) {
				long fileSize = part.getSize();
				if (fileSize > 0 && fileSize < 50 * 1048576) {
					String fileName = part.getSubmittedFileName();
					String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
					try {
						in = part.getInputStream();
						ClientParams param = uparam.getCparam();
						if (uparam.getType().equals("img")) {// 图片文件
							if (fileSize > 5 * 1048576) {
								result.setFenum(FastDFSResultEnum.FD_FILE_IMG_BIG);
								return result;
							}
							String fileUrl = FastDFSClient.getInstance(param)
									.uploadFile(FastDFSClient.getFileBuffer(in, fileSize), extName);
							result.setFileUrl(fileUrl);
						} else if (uparam.getType().equals("viedo")) {// 视频文件
							if (StringUtils.isEmpty(uparam.getFfmpegUrl())) {
								result.setFenum(FastDFSResultEnum.FD_FILE_ERROR);
								return result;
							}
							String newName = RandomUtil.buildFileName(fileName);
							saveLocalFile(part, uparam, newName);
							String fileUrl = FastDFSClient.getInstance(param)
									.uploadFile(FastDFSClient.getFileBuffer(in, fileSize), extName);
							map.put("videoUrl", fileUrl);
							result.setFileUrl(fileUrl);
							String tmpVideoUrl = uparam.getBaseImageDir() + File.separator + uparam.getDir()
									+ File.separator + newName;
							String cutUrl = uparam.getBaseImageDir() + File.separator + VideoCutImgUtil.processImg(
									tmpVideoUrl, uparam.getDir(), uparam.getBaseImageDir(), uparam.getFfmpegUrl());
							String cutImgUrl = FastDFSClient.getInstance(param).uploadFile(cutUrl);
							if (null == cutImgUrl) {
								result.setFenum(FastDFSResultEnum.FD_FILE_CUT_ERROR);
								return result;
							}
							result.setCutImgUrl(cutImgUrl);
							File redeoFile = new File(tmpVideoUrl);
							redeoFile.delete();
							File cutImgFile = new File(cutUrl);
							cutImgFile.delete();
						} else {// 其他文件
							String fileUrl = FastDFSClient.getInstance(param)
									.uploadFile(FastDFSClient.getFileBuffer(in, fileSize), extName);
							result.setFileUrl(fileUrl);
						}
						in.close();
						result.setFenum(FastDFSResultEnum.FD_UPLOAD_SUCCESS);
						return result;
					} catch (Exception e) {
						log.error("FastDFS上传文件异常" + e.getMessage());
						try {
							in.close();
						} catch (IOException e1) {
							log.error("关闭流异常" + e.getMessage());
						}
						result.setFenum(FastDFSResultEnum.FD_FILE_ERROR);
						return result;
					}
				} else {
					log.error("上传文件大于50M");
					result.setFenum(FastDFSResultEnum.UPLOAD_SIZE_BIGER);
					return result;
				}
			}
		}
		result.setFenum(FastDFSResultEnum.FD_FILE_ERROR);
		return result;
	}

	/**
	 * 存储本地文件
	 * 
	 * @param part
	 * @throws IOException
	 */
	private static void saveLocalFile(Part part, UploadParam uparam, String newName) throws IOException {
		File file = new File(uparam.getBaseImageDir(), uparam.getDir());
		if (!file.exists()) {
			file.mkdirs();
		}
		byte[] b = new byte[1024 * 1024];
		FileOutputStream out = new FileOutputStream(new File(file, newName));
		int index = 0;
		InputStream fileIn = part.getInputStream();
		while ((index = fileIn.read(b)) != -1) {
			out.write(b, 0, index);
		}
		out.close();
	}

}
