package util;

import java.io.File;
import java.util.List;

import com.lawu.eshop.utils.RandomUtil;

/**
 * 视频截图工具类
 * 
 * @author zhangrc
 * @date 2017/05/19
 *
 */
public class VideoCutImgUtil {
	
	/**
	 * 获取ffmpeg路径
	 * @return
	 */
	public static String getffmpegPath(){
		String path=VideoCutImgUtil.class.getClassLoader().getResource("ffmpeg.exe").getPath();
		return path;
	}
	
	/**
	 * 视频截图图片
	 * @param veido_path
	 * @param ffmpeg_path
	 * @return
	 */
	public static String processImg(String veido_path,String dir,String baseImageDir) {
		File file = new File(veido_path);
		if (!file.exists()) {
			System.err.println("路径[" + veido_path + "]对应的视频文件不存在!");
			return null;
		}
		String newfileName =RandomUtil.buildFileName(".jpg");
		File fileImg = new File(baseImageDir, dir);
        if (!fileImg.exists()) {
        	fileImg.mkdirs();
        }
		String AD_IMG_VIDEO=baseImageDir+"\\"+dir+"\\"+newfileName;
		List<String> commands = new java.util.ArrayList<String>();
		commands.add(getffmpegPath());
		commands.add("-i");
		commands.add(veido_path);
		commands.add("-y");
		commands.add("-f");
		commands.add("image2");
		commands.add("-ss");
		commands.add("3");// 这个参数是设置截取视频多少秒时的画面
		commands.add("-s");
		commands.add("500x300");
		commands.add(AD_IMG_VIDEO);
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commands);
			builder.start();
			System.out.println("截取成功");
			String video_img=dir+"\\"+newfileName;
			return video_img;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
