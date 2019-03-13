package com.lmxf.post.core.utils;

import java.util.Comparator;

import com.lmxf.post.entity.parameter.Dict;

/**
 * 数据字典排序比较器
 * @author liaoyl
 *
 */
public class DictComparator implements Comparator<Dict> {

	
	public int compare(Dict o1, Dict o2) {
		try {
			int one = Integer.parseInt(o1.getDictValue());
			int two = Integer.parseInt(o2.getDictValue());
			if(one > two){
				return 1;
			}else{
				return -1;
			}			
		}
		catch (NumberFormatException e) {
			return 0;
		}
	}

}
