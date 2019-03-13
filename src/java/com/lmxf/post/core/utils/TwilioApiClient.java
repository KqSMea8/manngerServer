package com.lmxf.post.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;


public class TwilioApiClient {
	private static Logger log = Logger.getLogger(TwilioApiClient.class.getName());

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map sendMsg(String toMsgField, String subject, String content) throws Exception{
		Map result=new HashMap();
		final MessageFactory messageFactory = TwilioRestClientSingle.getInstance().getMessageFactory();
		final List<NameValuePair> messageParams = new ArrayList<NameValuePair>();
		messageParams.add(new BasicNameValuePair("To",  toMsgField));
		messageParams.add(new BasicNameValuePair("From", "+16122551847"));
		messageParams.add(new BasicNameValuePair("Body", content));
		Message message =null;
		try{
			message=messageFactory.create(messageParams);
		}catch (Exception e) {
			e.printStackTrace();
			result.put("retcode", "1111");
			return result;
		}
		log.info("send:"+message.toJSON());
		result.put("sid", message.getSid());
		if (message!=null && message.getSid()!=null) {
				result.put("retcode", "0000");
				return result;
		} else {
			result.put("retcode", "1122");
			return result;
		}
	}

	public static void main(String[] args) {
		try {
			//TwilioApiClient.sendMsg("+8618682263042", "", "You have a parcel delivery, order number $order_id in Smarte Deliver Station $site_id ,locker number $box_id address is $fullname .Please pickup your parcel within 24 hours. Your pick up code is $password .");
			TwilioApiClient.sendMsg("+8618682263042", "", "hello welcome test sms .");
			
		} catch (Exception e) {
	        e.printStackTrace();
        }
	}
}