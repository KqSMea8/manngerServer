package com.lmxf.post.core.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.lmxf.post.core.utils.Constants;
import org.apache.commons.putils.web.*;

/**
 * MVC基类
 * 
 * @author xufeng
 * 
 */
public class BaseController extends MultiActionController {
	protected Page pager;
	protected Message message;

	/**
	 * 从页面获取分页点击时的当前页
	 * 
	 * @param req
	 * @return grid分页的当前页
	 */
	protected int getCurrentPage(HttpServletRequest req) {
		String pages_currentPage = req.getParameter("pages_currentPage");
		int currentPage = 1;
		if (null != pages_currentPage && !pages_currentPage.equals("")) {
			currentPage = Integer.parseInt(pages_currentPage);
		}
		return currentPage;
	}

}
