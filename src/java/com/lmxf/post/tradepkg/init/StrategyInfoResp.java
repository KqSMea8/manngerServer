package com.lmxf.post.tradepkg.init;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.entity.increment.AdvertMStrategy;
import com.lmxf.post.entity.increment.AdvertStrategy;
import com.lmxf.post.entity.vending.Vending;
import com.lmxf.post.entity.vending.VendingState;
import com.lmxf.post.tradepkg.common.RespHead;

public class StrategyInfoResp extends RespHead {


	public String CreateJson(Object... parm) {
		List<AdvertStrategy> advertStrategyList = (List<AdvertStrategy>) parm[4];
		try {
			JSONObject rootObject = super.getJSONObject((String) parm[0], (String) parm[1], (Integer) parm[2], (Integer) parm[3]);
			JSONObject JSONBody = new JSONObject();
			JSONArray advertStrategyArrayJson = new JSONArray();
			for(AdvertStrategy advertStrategy:advertStrategyList){
				JSONObject advertStrategyJson = new JSONObject();
				advertStrategyJson.put("StrategyId",advertStrategy.getStrategyId());
				advertStrategyJson.put("StrategyPoint",advertStrategy.getStrategyPoint());
				advertStrategyJson.put("StrategyType",advertStrategy.getStrategyType());
				advertStrategyJson.put("PlaySTime",advertStrategy.getPlaySTime());
				advertStrategyJson.put("PlayEtime",advertStrategy.getPlayEtime());
				if(advertStrategy.getAdvertMStrategyList()!=null && advertStrategy.getAdvertMStrategyList().size()>0){
					JSONArray advertMStrategyArrayJson = new JSONArray();
			        for(AdvertMStrategy advertMStrategy:advertStrategy.getAdvertMStrategyList()){
			        	JSONObject advertMStrategyJson = new JSONObject();
			        	advertMStrategyJson.put("MstrategyId", advertMStrategy.getMstrategyId());
			        	advertMStrategyJson.put("SeqId", advertMStrategy.getSeqId());
			        	advertMStrategyJson.put("MediaUrl",advertMStrategy.getMediaUrl());
			        	advertMStrategyJson.put("MediaType",advertMStrategy.getMediaType());
			        	advertMStrategyJson.put("PlayerTime", advertMStrategy.getPlayerTime());
			        	advertMStrategyJson.put("PlayerTimes", advertMStrategy.getPlayerTimes());
			        	advertMStrategyArrayJson.add(advertMStrategyJson);
			        }
			        advertStrategyJson.put("MediaInfo", advertMStrategyArrayJson);
				}
				advertStrategyArrayJson.add(advertStrategyJson);
			}
			JSONBody.put("StrategyInfo", advertStrategyArrayJson);
			JSONObject JSONObject = (JSONObject) rootObject.get(GConstent.ZxmlRoot);
			JSONObject.put(GConstent.ZxmlBody, JSONBody);
			return rootObject.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

}
