package com.lmxf.post.core.utils;

import com.lmxf.post.entity.parameter.Dict;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.factory.MessageFactory;

public class TwilioRestClientSingle {
    private static TwilioRestClientSingle twilioRestClientSingle;
    private static TwilioRestClient client = null;
    
    public static TwilioRestClientSingle getInstance(){
    	if(twilioRestClientSingle==null){
    		twilioRestClientSingle=new TwilioRestClientSingle();
    	}
    	if(client==null){
    		Dict dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"twilioSMS_sid");
    		Dict dict1=(Dict) CacheUtils.get(CacheUtils.dictCache,"twilioSMS_token");
    		String ACCOUNT_SID=dict.getDictValue();
    		String AUTH_TOKEN=dict.getDictValue();
    		if(ACCOUNT_SID==null || "".equals(ACCOUNT_SID)){
    			ACCOUNT_SID = "AC44dd6aabfc5dacc10925a0588d8cf6c6";
    			AUTH_TOKEN = "77c7238e44ede0ad26fdfaadd4943bd4";
    			
    			ACCOUNT_SID = "ACcfaa8bbbb17cd3f4167bc0f93660a601";
    			AUTH_TOKEN = "e1baacd44599641776f618de87f32758";
    		}
    		client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
    	}
    	return twilioRestClientSingle;
    }
    
    public TwilioRestClient getTwilioRestClient(){
    	if(client==null){
    		getInstance();
    	}
    	return client;
    } 
    
    public MessageFactory getMessageFactory(){
    	if(client==null){
    		getInstance();
    	}
    	return client.getAccount().getMessageFactory();
    }
    
}
