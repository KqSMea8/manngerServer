package com.lmxf.post.service.core;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.lmxf.post.core.utils.CacheUtils;
import com.lmxf.post.entity.config.ErrorCode;
import com.lmxf.post.entity.parameter.Dict;
import com.lmxf.post.entity.product.ProductClassify;
import com.lmxf.post.entity.product.ProductInfo;
import com.lmxf.post.repository.config.ErrorCodeDao;
import com.lmxf.post.repository.parameter.DictDao;
import com.lmxf.post.repository.product.ProductClassifyDao;
import com.lmxf.post.repository.product.ProductInfoDao;
import com.manage.project.system.index.vo.OneMonthReviewVo;
import com.manage.project.system.index.vo.OperateReviewVo;

import net.sf.ehcache.Cache;
public class CacheBusinessService {
	private final static Log log = LogFactory.getLog(CacheBusinessService.class);
	private DictDao dictDao;
	private ErrorCodeDao errorCodeDao;
	private ProductInfoDao productInfoDao;
	private ProductClassifyDao productClassifyDao;
	public void setDictDao(DictDao dictDao) {
		this.dictDao = dictDao;
	}

	public void setErrorCodeDao(ErrorCodeDao errorCodeDao) {
		this.errorCodeDao = errorCodeDao;
	}

	public void setProductInfoDao(ProductInfoDao productInfoDao) {
		this.productInfoDao = productInfoDao;
	}

	public void setProductClassifyDao(ProductClassifyDao productClassifyDao) {
		this.productClassifyDao = productClassifyDao;
	}
	
	public void init(){
		initDict();
		initError();
		initProduct();
		initProductClassifly();
		initBoard();
	}

	/**
	 * 数据字典初始化
	 */
	@PostConstruct //初始化方法的注解方式  等同与init-method=init 
	public void initDict() {
		log.debug("-------初始化数据字典缓存------");
		List<Dict> dictList=dictDao.findAll();
	    for (Dict dict : dictList) {
	    	CacheUtils.put(CacheUtils.dictCache, dict.getDictKey(), dict);
		}
		System.out.println("----------初始化数据字典缓存共:"+dictList.size()+" 记录");
	}
	
	/**
	 * 数据字典初始化
	 */
	@PostConstruct //初始化方法的注解方式  等同与init-method=init 
	public void initError() {
		log.debug("-------初始错误代码缓存------");
		List<ErrorCode> dictList=errorCodeDao.findAll();
	    for (ErrorCode dict : dictList) {
	    	CacheUtils.put(CacheUtils.errorCodeCache, dict.getRetCode(), dict);
		}
		System.out.println("----------初始错误代码缓存共:"+dictList.size()+" 记录");
	}
	
	/**
	 * 数据字典初始化
	 */
	@PostConstruct //初始化方法的注解方式  等同与init-method=init 
	public void initProduct() {
		log.debug("-------初始商品信息缓存------");
		List<ProductInfo> dictList=productInfoDao.findAll();
	    for (ProductInfo dict : dictList) {
	    	CacheUtils.put(CacheUtils.productInfoCache,dict.getProductId(), dict);
		}
		System.out.println("----------初始商品信息缓存共:"+dictList.size()+" 记录");
	}
	
	/**
	 * 数据字典初始化
	 */
	@PostConstruct //初始化方法的注解方式  等同与init-method=init 
	public void initProductClassifly() {
		log.debug("-------初始商品分类信息缓存------");
		List<ProductClassify> dictList=productClassifyDao.findAll();
	    for (ProductClassify dict : dictList) {
	    	CacheUtils.put(CacheUtils.classifyCache,dict.getClassifyId(), dict);
		}
		System.out.println("----------初始商品分类信息缓存共:"+dictList.size()+" 记录");
	}
	
	/**
	 * 数据字典初始化
	 */
	@PostConstruct //初始化方法的注解方式  等同与init-method=init 
	public void initBoard() {
//		OperateReviewVo operateReviewVo=(OperateReviewVo) CacheUtils.get("serverCache",CacheUtils.INDEX_SUMMARY_TOTALREVIEW_CACHE_+"8886");
//	    log.debug("-------------首页运营总览缓存:"+operateReviewVo);
//	    OneMonthReviewVo oneMonthReviewVo=(OneMonthReviewVo) CacheUtils.get("defaultCache",CacheUtils.INDEX_SUMMARY_TODAYSALE_CACHE_+"8886");
//	    log.debug("-------------初始化首页缓存近一月总览数据:"+oneMonthReviewVo);
	}

}
