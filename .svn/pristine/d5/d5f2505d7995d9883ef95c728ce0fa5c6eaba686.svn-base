package com.lmxf.post.core.utils.pay;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DeleteFileUtil {

	private final static Log logger = LogFactory.getLog(DeleteFileUtil.class);

	// private ServerProperties serverProperties;
	// @Scheduled(cron="0 1 23 ? * 5L")
	public void cleanTimer() {
		logger.info("开始清理tomcat log");
		String logPath = "";// serverProperties.getTomcat().getBasedir().getAbsolutePath()+"/logs";
		if (new File(logPath).isDirectory()) {
			// 获取文件夹中的文件集合
			File[] logs = new File(logPath).listFiles();
			// 设置系统这里设置的日期格式,和配置文件里的参数保持一致
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			// 遍历集合
			for (int i = 0; i < logs.length; i++) {
				File log = logs[i];
				// 获取到第i个日志的名称，截取中间的日期字段,转成long型s
				int start = log.getName().indexOf(".") + 1;
				int end = log.getName().lastIndexOf(".");
				// 获取到的日志名称中的时间（2016-12-16）
				String dateStr = log.getName().substring(start, end);
				// 将字符串型的（2016-12-16）转换成long型
				long lonInt = 0;
				try {
					lonInt = dateFormat.parse(dateStr).getTime();
					// 系统时间减去日志名字中获取的时间差大于配置文件中设置的时间删除
					if ((System.currentTimeMillis() - lonInt)
							/ (1000 * 60 * 60 * 24) > 30) {
						log.delete();
						System.out.println(log.getName());
					}
				} catch (ParseException e) {
					logger.error("删除log出错", e);
				}
			}
		}
	}

	/**
	 * 删除文件，可以是文件或文件夹
	 * 
	 * @param fileName
	 *            要删除的文件名
	 * @return 删除成功返回true，否则返回false
	 */
	public static boolean delete(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			System.out.println("删除文件失败:" + fileName + "不存在！");
			return false;
		} else {
			if (file.isFile())
				return deleteFile(fileName);
			else
				return deleteDirectory(fileName);
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 *            要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				System.out.println("删除单个文件" + fileName + "成功！");
				return true;
			} else {
				System.out.println("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			System.out.println("删除单个文件失败：" + fileName + "不存在！");
			return false;
		}
	}

	/**
	 * 删除目录及目录下的文件
	 * 
	 * @param dir
	 *            要删除的目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator))
			dir = dir + File.separator;
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
			System.out.println("删除目录失败：" + dir + "不存在！");
			return false;
		}

		boolean flag = true;
		// 删除文件夹中的所有文件包括子目录
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = DeleteFileUtil.deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
			// 删除子目录
			else if (files[i].isDirectory()) {
				flag = DeleteFileUtil.deleteDirectory(files[i]
						.getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
			System.out.println("删除目录失败！");
			return false;
		}
		// 删除当前目录
		if (dirFile.delete()) {
			System.out.println("删除目录" + dir + "成功！");
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		// // 删除单个文件
		// String file = "c:/test/test.txt";
		// DeleteFileUtil.deleteFile(file);
		// System.out.println();
		// 删除一个目录
		//String dir = "D:/home/web/upload/upload/files";
		
		//DeleteFileUtil.deleteDirectory(dir);
		// System.out.println();
		// // 删除文件
		// dir = "c:/test/test0";
		// DeleteFileUtil.delete(dir);
		String filePath ="D:\\Users\\apache-tomcat-7\\webapps\\ZhilaiPayPlat\\findQrcode";
		DeleteFileUtil.deleteFiles(filePath,1);
	}

 
	/***
	 * 删除旧文件或图片 20180816 zzw add
	 * @param filePath
	 */
	public static void deleteFiles(String filePath,int day) {
		if(null==filePath||"".equals(filePath.trim())){
			return;
		}
		File srcFolder = new File(filePath);
		if (!srcFolder.isDirectory()) {
			return;
		}
		File[] arrayFile = srcFolder.listFiles();
		if (null==arrayFile||arrayFile.length<1) {
			return;
		}
		if(day<=0){
			day=1;
		}
		for (File file : arrayFile) {
			// 不是目录
			if (!file.isDirectory()&&diffDay(file.lastModified())>=day) {
				file.delete();
			}
		}
	}

	/**
	 * 读取文件创建时间
	 */
	public static long diffDay(long time) {
		long l = new Date().getTime() - time;
		long day = l / (24 * 60 * 60 * 1000);
		return day;
	}
	
	/**
	 * 读取文件创建时间
	 */
	public static long getDiffTime(String path) {
		String filePath = "D:\\Users\\apache-tomcat-7\\logs\\localhost_access_log.2018-06-01.txt";
		File f = new File(filePath);
		long time = f.lastModified();
		Date now = new Date();
		long l = now.getTime() - time;
		System.out.println(" l=" + l);
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		System.out.println("" + day + "天" + hour + "小时" + min + "分" + s + "秒");
		return day;
	}

}
