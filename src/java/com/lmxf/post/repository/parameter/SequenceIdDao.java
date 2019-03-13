/**
 * 
 */
package com.lmxf.post.repository.parameter;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.putils.repository.BaseDao;
import org.apache.commons.putils.service.ServiceException;

import com.lmxf.post.entity.parameter.SequenceId;

/**
 * 
 * @author hsmao
 * 
 */
public class SequenceIdDao extends BaseDao {
	private static Calendar nowCal = Calendar.getInstance();
    static{
		nowCal.set(2019, 0, 1,0,0,0);
    }
	public int currval(String name) throws SQLException, Exception {
		int id = super.getSqlSession().selectOne("SequenceId.currval", name);
		return id;
	}

	public int nextval(String corp_id, String name) throws SQLException, Exception {
		SequenceId sequenceId = new SequenceId();
		sequenceId.setName(name);
		sequenceId.setCorpId(corp_id);
		int id = super.getSqlSession().selectOne("SequenceId.nextval", sequenceId);
		return id;
	}

	public SequenceId findOne(String corp_id, String name)  {
		SequenceId sequenceId = new SequenceId();
		sequenceId.setName(name);
		sequenceId.setCorpId(corp_id);
		SequenceId sequenceIdR = super.getSqlSession().selectOne("SequenceId.findOne", sequenceId);
		return sequenceIdR;
	}

	public int update(SequenceId obj) {
		int count = super.getSqlSession().update("SequenceId.update", obj);
		return count;
	}

	public int delete(String name) throws SQLException, Exception {
		int count = super.getSqlSession().delete("SequenceId.delete", name);
		return count;
	}

	public String findNextVal(String corp_id, String name, int len) {
		StringBuffer buf = new StringBuffer();
		try {
			int id = nextval(corp_id, name);
			String tmp = "" + id;
			for (int i = 0; i < len - tmp.length(); i++)
				buf.append("0");
			buf.append(tmp);
		} catch (SQLException e) {
			throw new ServiceException("查询sequence出错！", e);
		} catch (Exception e) {
			throw new ServiceException("查询sequence出错！", e);
		}
		Calendar calendar=Calendar.getInstance();
		long before=calendar.getTimeInMillis();
		long after=nowCal.getTimeInMillis();
		return buf.toString()+"_"+(before-after);
	}
}
