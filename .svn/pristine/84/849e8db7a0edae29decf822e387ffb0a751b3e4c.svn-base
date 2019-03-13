package com.lmxf.post.security;

import java.util.Properties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import com.lmxf.post.security.crypto.PasswordEncoder;

public class DecryptPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {

	private PasswordEncoder passwordEncoder;

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {

		if (!props.containsValue("development")) {
			String password = props.getProperty("jdbc.password");
			String username = props.getProperty("jdbc.username");
			if (password != null && username != null) {
				String deUsername = null;
				String dePassword = null;
				try {
					deUsername = passwordEncoder.decrypt(username);
					dePassword = passwordEncoder.decrypt(password);
				} catch (Exception e) {
					System.out.println("===========Decryption failure============");
					e.printStackTrace();
				}
				props.setProperty("jdbc.password", dePassword);// 赋值
				props.setProperty("jdbc.username", deUsername);// 赋值
			}
		}
		super.processProperties(beanFactoryToProcess, props);
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

}
