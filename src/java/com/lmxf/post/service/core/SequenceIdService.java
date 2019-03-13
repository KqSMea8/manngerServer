package com.lmxf.post.service.core;

/**
 * 
 */

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.commons.putils.service.ServiceException;
import com.lmxf.post.entity.parameter.SequenceId;
import com.lmxf.post.repository.parameter.SequenceIdDao;

/**
 * 
 * @author hsmao
 * 
 */
public class SequenceIdService {
	private static Logger log = Logger.getLogger(SequenceIdService.class);
	private SequenceIdDao sequenceIdDao;

	public SequenceIdDao getSequenceIdDao() {
		return sequenceIdDao;
	}

	public void setSequenceIdDao(SequenceIdDao sequenceIdDao) {
		this.sequenceIdDao = sequenceIdDao;
	}

	/**
	 * @param name
	 * @return
	 */
	public String findNextVal(String corp_id,String name) {
		return findNextVal(corp_id,name, 11);
	}

	public String findNextVal(String corp_id,String name, int len) {
		StringBuffer buf = new StringBuffer();
		try {
			int id = sequenceIdDao.nextval(corp_id,name);
			String tmp = "" + id;
			for (int i = 0; i < len - tmp.length(); i++)
				buf.append("0");
			buf.append(tmp);

		} catch (SQLException e) {
			log.error(e.getMessage());
			throw new ServiceException("查询sequence出错！", e);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ServiceException("查询sequence出错！", e);
		}
		return buf.toString();
	}

	/**
	 * @param obj
	 * @return
	 */
	public int update(SequenceId obj) {
		int count = 0;
			count = sequenceIdDao.update(obj);
		return count;
	}

	/**
	 * @param ids
	 * @return
	 */
	public int delete(String name) {
		int count = 0;
		try {
			count += sequenceIdDao.delete(name);
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw new ServiceException("删除sequence信息出错！", e);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ServiceException("删除sequence信息出错！", e);
		}
		return count;
	}
}
