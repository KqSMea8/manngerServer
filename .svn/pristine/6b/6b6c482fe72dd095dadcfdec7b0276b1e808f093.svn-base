package com.lmxf.post.core.utils.pay;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import com.lmxf.post.core.utils.CacheUtils;
import com.lmxf.post.entity.parameter.Dict;

public class PrintUtils {
	private final static Log log = LogFactory.getLog(PrintUtils.class);
	/**
	 * 打印控制台记录
	 * 
	 * @param start
	 * @param formart
	 * @return
	 */
	public static void OUT(Object obj) {
		Dict dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"print_confirm");
		String print=dict.getDictValue();
		if ("ok".equals(print)) {
			System.out.println(obj.toString());
		}

	}

	/**
	 * 短信发送规则 true是同步发送，false是异步发送
	 * 
	 * @param start
	 * @param formart
	 * @return
	 */
	public static boolean MSGRULE() {
		Dict dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"msgrule_confirm");
		String print=dict.getDictValue();
		if ("ok".equals(print)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否测试环境 true是生产环境，false是测试环境，默认是测试环境， no 测试环境
	 */
	public static boolean ISTEST() {
		Dict dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"test_confirm");
		String print=dict.getDictValue();
		if ("no".equals(print)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 默认是true,要对IP访问进行记录。
	 * 
	 * @return
	 */
	public static boolean IsInterceptIp() {
		Dict dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"ip_intercept");
		String saveLog=dict.getDictValue();
		boolean isIntercept = true;
		if ("ok".equals(saveLog)) {
			// 开
			isIntercept = true;
		} else if ("no".equals(saveLog)) {
			// 关
			isIntercept = false;
		}
		return isIntercept;

	}

	/**
	 * 默认是拦截的 即不设置任何参数时返回true 是否拦截支付宝扫码 ok返回true，执行拦截规则
	 * 
	 * @return
	 */
	public static boolean IsInterceptIpAlipayQrcode() {
		Dict dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"ip_intercept");
		String flag=dict.getDictValue();
		
		boolean isIntercept = true;
		if ("ok".equals(flag)) {
			// 开
			isIntercept = true;
		} else if ("no".equals(flag)) {
			// 关
			isIntercept = false;
		}
		dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"ip_alipayqrcode");
		 flag=dict.getDictValue();
		if ("ok".equals(flag) && isIntercept) {
			// 开
			isIntercept = true;
		} else if ("no".equals(flag)) {
			// 关
			isIntercept = false;
		}
		return isIntercept;

	}

	/**
	 * 默认是拦截的 即不设置任何参数时返回true 是否拦截支付宝网页 ok返回true，执行拦截规则
	 * 
	 * @return
	 */
	public static boolean IsInterceptIpAlipayWeb() {
		Dict dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"ip_intercept");
		 String flag=dict.getDictValue();
		boolean isIntercept = true;
		if ("ok".equals(flag)) {
			// 开
			isIntercept = true;
		} else if ("no".equals(flag)) {
			// 关
			isIntercept = false;
		}
		dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"ip_alipayweb");
		flag=dict.getDictValue();
		if ("ok".equals(flag) && isIntercept) {
			// 开
			isIntercept = true;
		} else if ("no".equals(flag)) {
			// 关
			isIntercept = false;
		}
		return isIntercept;

	}

	/**
	 * 默认是拦截的 即不设置任何参数时返回true 是否拦截财会通网页 ok返回true，执行拦截规则
	 * 
	 * @return
	 */
	public static boolean IsInterceptIpTenpayWeb() {
		Dict dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"ip_intercept");
		String flag=dict.getDictValue();
		boolean isIntercept = true;
		if ("ok".equals(flag)) {
			// 开
			isIntercept = true;
		} else if ("no".equals(flag)) {
			// 关
			isIntercept = false;
		}
		dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"ip_tenpayweb");
		flag=dict.getDictValue();
		if ("ok".equals(flag) && isIntercept) {
			// 开
			isIntercept = true;
		} else if ("no".equals(flag)) {
			// 关
			isIntercept = false;
		}
		return isIntercept;

	}

	/**
	 * 默认是拦截的 即不设置任何参数时返回true 是否拦截财会通网页 ok返回true，执行拦截规则
	 * 
	 * @return
	 */
	public static boolean IsInterceptIpWeixin() {
		Dict dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"ip_intercept");
		String flag=dict.getDictValue();
		boolean isIntercept = true;
		if ("ok".equals(flag)) {
			// 开
			isIntercept = true;
		} else if ("no".equals(flag)) {
			// 关
			isIntercept = false;
		}
		dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"ip_weixin");
		flag=dict.getDictValue();
		if ("ok".equals(flag) && isIntercept) {
			// 开
			isIntercept = true;
		} else if ("no".equals(flag)) {
			// 关
			isIntercept = false;
		}
		return isIntercept;

	}

	/**
	 * 记录查询帐户接口的ip情况
	 * 
	 * @return
	 */
	public static boolean getIsSaveAccessLog() {
		Dict dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"saveLog_confirm");
		String flag=dict.getDictValue();
		
		if ("ok".equals(flag)) {
			return true;
		} else if ("no".equals(flag)) {
			return false;
		}
		dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"accessLog_confirm");
		flag=dict.getDictValue();
		
		if ("no".equals(flag)) {
			return false;
		}
		return true;

	}

	/**
	 * 保存第三方支付接口IP日志
	 * 
	 * @return
	 */
	public static boolean IsSaveIPLog() {
		Dict dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"saveLog_confirm");
		String flag=dict.getDictValue();
		if ("ok".equals(flag)) {
			return true;
		} else if ("no".equals(flag)) {
			return false;
		}
		dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"ipLog_confirm");
		 flag=dict.getDictValue();
		if ("no".equals(flag)) {
			return false;
		}
		return true;
	}

	public static boolean IsSaveCutFeeLog() {
		Dict dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"saveLog_confirm");
		String flag=dict.getDictValue();
		if ("ok".equals(flag)) {
			return true;
		} else if ("no".equals(flag)) {
			return false;
		}
		dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"cutFeeLog_confirm");
		 flag=dict.getDictValue();

		if ("no".equals(flag)) {
			return false;
		}
		return true;
	}

	public static boolean IsSavePkgLog() {
		Dict dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"saveLog_confirm");
		String flag=dict.getDictValue();
		if ("ok".equals(flag)) {
			return true;
		} else if ("no".equals(flag)) {
			return false;
		}
		dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"pkgLog_confirm");
		 flag=dict.getDictValue();
		if ("no".equals(flag)) {
			return false;
		}
		return true;
	}

	public static boolean IsSaveRechargeLog() {
		Dict dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"saveLog_confirm");
		String flag=dict.getDictValue();
		if ("ok".equals(flag)) {
			return true;
		} else if ("no".equals(flag)) {
			return false;
		}
		dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"rechargeLog_confirm");
		 flag=dict.getDictValue();
		if ("no".equals(flag)) {
			return false;
		}
		return true;
	}

	/**
	 * 系统用户操作日志
	 * 
	 * @return
	 */
	public static boolean IsSaveSysLog() {
		Dict dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"saveLog_confirm");
		String flag=dict.getDictValue();
		if ("ok".equals(flag)) {
			return true;
		} else if ("no".equals(flag)) {
			return false;
		}
		dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"sysLog_confirm");
		 flag=dict.getDictValue();
		if ("no".equals(flag)) {
			return false;
		}
		return true;
	}

	/**
	 * 交易日志
	 * 
	 * @return
	 */
	public static boolean IsSaveTradeLog() {
		Dict dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"saveLog_confirm");
		String flag=dict.getDictValue();
		if ("ok".equals(flag)) {
			return true;
		} else if ("no".equals(flag)) {
			return false;
		}
		dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"tradeLog_confirm");
		 flag=dict.getDictValue();
		if ("no".equals(flag)) {
			return false;
		}
		return true;
	}

	public static boolean IsSaveReturnPayInfo() {
		Dict dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"saveLog_confirm");
		String flag=dict.getDictValue();
		if ("ok".equals(flag)) {
			return true;
		} else if ("no".equals(flag)) {
			return false;
		}
		dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"returnPayInfo_confirm");
		 flag=dict.getDictValue();
		if ("no".equals(flag)) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @return
	 */
	public static boolean IsSaveTradeInfo() {
		Dict dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"saveLog_confirm");
		String flag=dict.getDictValue();
		if ("ok".equals(flag)) {
			return true;
		} else if ("no".equals(flag)) {
			return false;
		}
		dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"tradeInfo_confirm");
		 flag=dict.getDictValue();
		if ("no".equals(flag)) {
			return false;
		}
		return true;
	}

	public static boolean IsSaveTradeFishOrder() {
		Dict dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"saveLog_confirm");
		String flag=dict.getDictValue();
		if ("ok".equals(flag)) {
			return true;
		} else if ("no".equals(flag)) {
			return false;
		}
		dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"tradeFishOrder_confirm");
		 flag=dict.getDictValue();
		if ("no".equals(flag)) {
			return false;
		}
		return true;
	}
	
	   /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //conn.setRequestProperty("contentType", "utf-8");
            //conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("Pragma", "no-cache");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
            
            //String xml=new String(param.toString().getBytes("utf-8"),"iso8859-1") ;
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            //out.print(param.getBytes("UTF-8"));
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result.toString();
    }
    
    /**
     * 请求，只请求一次，不做重试
     * @param domain
     * @param urlSuffix
     * @param uuid
     * @param data
     * @param connectTimeoutMs
     * @param readTimeoutMs
     * @param useCert 是否使用证书，针对退款、撤销等操作
     * @return
     * @throws Exception
     */
    public static String requestOnce(final String url,String data)  {
    	try{
	        BasicHttpClientConnectionManager connManager;
	        connManager = new BasicHttpClientConnectionManager(
	                    RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.getSocketFactory())
	                            .register("https", SSLConnectionSocketFactory.getSocketFactory()).build(),null,null,null);        
	
	        HttpClient httpClient = HttpClientBuilder.create().setConnectionManager(connManager).build();
	        HttpPost httpPost = new HttpPost(url);
	        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
	        httpPost.setConfig(requestConfig);
	        StringEntity postEntity = new StringEntity(data, "UTF-8");
	        httpPost.addHeader("Content-Type", "text/xml");
	        httpPost.addHeader("User-Agent", "wxpay sdk java v1.0 ");// + config.getMchID());  // TODO: 很重要，用来检测 sdk 的使用情况，要不要加上商户信息？
	        httpPost.setEntity(postEntity);
	
	        HttpResponse httpResponse = httpClient.execute(httpPost);
	        HttpEntity httpEntity = httpResponse.getEntity();
	        return EntityUtils.toString(httpEntity, "UTF-8");
    	}catch (Exception e) {
    		e.printStackTrace();
			log.error("发送微信公众号支付地址:"+url+" data:"+data+" 错误:"+e.getMessage());
		}
    	return "";
    }

}
