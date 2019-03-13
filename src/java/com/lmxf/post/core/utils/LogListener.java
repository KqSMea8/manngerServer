package com.lmxf.post.core.utils;
 
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

/**
 * 日志监听器
 * @author xufeng
 *
 */
public class LogListener implements InitializingBean  {
	private static Logger log = Logger.getLogger(LogListener.class);
	
	/**
	 * 初始化日志监听，使控制台可以打印sql日志信息
	 */
	
	public void afterPropertiesSet() throws Exception
	{
		log.info("org.apache.commons.putils.listener.LogListener start ...");
		
		org.apache.ibatis.logging.LogFactory.useSlf4jLogging(); 
		org.apache.ibatis.logging.LogFactory.useLog4JLogging(); 
		org.apache.ibatis.logging.LogFactory.useJdkLogging(); 
		org.apache.ibatis.logging.LogFactory.useCommonsLogging(); 
		org.apache.ibatis.logging.LogFactory.useStdOutLogging(); 
		
//		org.apache.ibatis.logging.LogFactory.useStdOutLogging();
	}
}
