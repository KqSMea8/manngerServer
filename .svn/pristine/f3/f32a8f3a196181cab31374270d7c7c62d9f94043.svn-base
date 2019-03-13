package com.lmxf.post.core.utils.pay;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lmxf.post.core.utils.DateUtils;

public class DownUtil {
	private final static Log log = LogFactory.getLog(DownUtil.class);
	/***下载文件到本地
	 * @param urlString被下载的文件地址
	 * @param filename本地文件名
	 * @param context项目名称
	 */
	public static void download(String urlString, String filename,String context,String pngPath) {
		OutputStream os = null;
		InputStream is = null;
		byte[] qrcode = null;
		URL url = null;
		URLConnection con = null;
		try {
			if (null == urlString || null == filename) {
				return;
			}
			url = new URL(urlString);// 构造URL			
			con = url.openConnection();// 打开连接			
			is = con.getInputStream();// 输入流			
			qrcode = new byte[1024];// 1K的数据缓冲			
			int len;// 读取到的数据长度
			if(null==pngPath||"".equals(pngPath.trim())){
				pngPath = new File("").getAbsolutePath().split("bin")[0] + "webapps"+context+"/qrcode/";	
			}
			// 输出的文件流 os = new FileOutputStream("D:/Users/apache-tomcat-6.0.44/webapps/payplat/findQrcode/"+filename);
			os = new FileOutputStream(pngPath + filename);
			// 开始读取
			while ((len = is.read(qrcode)) != -1) {
				os.write(qrcode, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 完毕，关闭所有链接
				if (os != null) {
					os.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			qrcode = null;
		}
	}
	
	
	public static void ClearLog(final String pngPath,final String logPath,final boolean flg){
		System.out.println("运行定时任务");
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				//System.out.println("开始运行定时任务"+flg);
				if(flg){
					//System.out.println("开始运行定时任务成功开启第三方连接"+flg);
					//connect();
					
				}
				if(DateUtils.getCurrenHour()<=24&&DateUtils.getCurrenHour()>=0){
					if(null!=pngPath&&!"".equals(pngPath.trim())){
						DeleteFileUtil.deleteFiles(pngPath,1);
						log.info(DateUtils.DateToString()+"清理图片");
					}					
					if(null!=logPath&&!"".equals(logPath.trim())){
						DeleteFileUtil.deleteFiles(logPath,8);
						log.info(DateUtils.DateToString()+"清理日志");
					}					
					
				}
			}
		}, 5, 30, TimeUnit.MINUTES);
	}
	
	private void init2() {
		ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(10);

		executor.schedule(new Runnable() {
			@Override
			public void run() {
				if(DateUtils.getCurrenHour()<=5&&DateUtils.getCurrenHour()<=2){
					//DeleteFileUtil.deleteFiles(pngPath);
				}
				
			}
		}, 12, TimeUnit.HOURS);

	}

}
