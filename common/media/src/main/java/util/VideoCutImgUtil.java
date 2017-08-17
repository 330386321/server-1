package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lawu.eshop.utils.RandomUtil;

/**
 * 视频截图工具类
 * 
 * @author zhangrc
 * @date 2017/05/19
 *
 */
public class VideoCutImgUtil {
	
	 private static Logger logger = LoggerFactory.getLogger(VideoCutImgUtil.class);
	
	/**
	 * 视频截图图片
	 * @param veido_path
	 * @param ffmpeg_path
	 * @return
	 */
	public static String processImg(String veido_path,String dir,String baseImageDir,String ffmpegUrl) {
		File file = new File(veido_path);
		if (!file.exists()) {
			logger.debug("路径[" + veido_path + "]对应的视频文件不存在!");
			return null;
		}
		String newfileName =RandomUtil.buildFileName(".jpg");
		File fileImg = new File(baseImageDir, dir);
        if (!fileImg.exists()) {
        	fileImg.mkdirs();
        }
		String AD_IMG_VIDEO=baseImageDir+"/"+dir+"/"+newfileName;
		List<String> commands = new java.util.ArrayList<String>();
		commands.add(ffmpegUrl);
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
			logger.debug("截取图片成功");
			String video_img=dir+"/"+newfileName;
			return video_img;
		} catch (Exception e) {
			logger.debug("截取图片失败");
			return null;
		}
	}
	
	
    /** 
     * 获取视频总时间 
     * @param viedo_path    视频路径 
     * @param ffmpeg_path   ffmpeg路径 
     * @return 
     */  
    public static String getVideoTime(String video_path, String ffmpeg_path) {  
        List<String> commands = new java.util.ArrayList<String>();  
        commands.add(ffmpeg_path);  
        commands.add("-i");  
        commands.add(video_path);  
        try {  
            ProcessBuilder builder = new ProcessBuilder();  
            builder.command(commands);  
            final Process p = builder.start();  
              
            //从输入流中读取视频信息  
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));  
            StringBuffer sb = new StringBuffer();  
            String line = "";  
            while ((line = br.readLine()) != null) {  
                sb.append(line);  
            }  
            br.close();  
              
            //从视频信息中解析时长  
            String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";  
            Pattern pattern = Pattern.compile(regexDuration);  
            Matcher m = pattern.matcher(sb.toString());  
            if (m.find()) {  
            	String time=m.group(1);  
            	time=time.substring(0,time.length()-3);
                return time;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
          
        return "0";  
    }  
      
   

}
