package com.alipay.api.domain;

import java.util.List;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;
import com.alipay.api.internal.mapping.ApiListField;

/**
 * 企业基础信息
 *
 * @author auto create
 * @since 1.0, 2016-08-04 14:32:28
 */
public class BaseInfo extends AlipayObject {

	private static final long serialVersionUID = 5474124632864353837L;

	/**
	 * 如果商户的app需要签约使用移动快捷支付产品，需要上传该app的名称
	 */
	@ApiField("app_name")
	private String appName;

	/**
	 * 企业联系人信息
	 */
	@ApiListField("contact_info")
	@ApiField("contact_person_info")
	private List<ContactPersonInfo> contactInfo;

	/**
	 * 企业logo图片
	 */
	@ApiField("logo_pic")
	private String logoPic;

	/**
	 * 所属MCCCode，详情可参考
<a href="https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.59bgD2&treeId=222&articleId=105364&docType=1#s1
">商家经营类目</a>
	 */
	@ApiField("mcc_code")
	private String mccCode;

	/**
	 * 企业特殊资质图片，可参考
<a href="https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.59bgD2&treeId=222&articleId=105364&docType=1#s1
">商家经营类目</a>
	 */
	@ApiListField("special_license_pic")
	@ApiField("string")
	private List<String> specialLicensePic;

	/**
	 * 企业网址信息
	 */
	@ApiListField("web_address")
	@ApiField("string")
	private List<String> webAddress;

	/**
	 * 网址授权函图片
	 */
	@ApiField("web_auth_pic")
	private String webAuthPic;

	public String getAppName() {
		return this.appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}

	public List<ContactPersonInfo> getContactInfo() {
		return this.contactInfo;
	}
	public void setContactInfo(List<ContactPersonInfo> contactInfo) {
		this.contactInfo = contactInfo;
	}

	public String getLogoPic() {
		return this.logoPic;
	}
	public void setLogoPic(String logoPic) {
		this.logoPic = logoPic;
	}

	public String getMccCode() {
		return this.mccCode;
	}
	public void setMccCode(String mccCode) {
		this.mccCode = mccCode;
	}

	public List<String> getSpecialLicensePic() {
		return this.specialLicensePic;
	}
	public void setSpecialLicensePic(List<String> specialLicensePic) {
		this.specialLicensePic = specialLicensePic;
	}

	public List<String> getWebAddress() {
		return this.webAddress;
	}
	public void setWebAddress(List<String> webAddress) {
		this.webAddress = webAddress;
	}

	public String getWebAuthPic() {
		return this.webAuthPic;
	}
	public void setWebAuthPic(String webAuthPic) {
		this.webAuthPic = webAuthPic;
	}

}