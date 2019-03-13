package com.lmxf.post.core.utils;

public class GConstent {
	// 1000~1020 xml解析方面的错误代码
	public static final int Xml_Parse_Error = 1000;
	public static final int Xml_No_Field_ZMSG = 1001;
	public static final int Xml_No_Field_ZHEAD = 1002;
	public static final int Xml_No_Field_ZBODY = 1003;
	public static final int Xml_No_Field_bcode = 1004;
	public static final int Xml_No_Field_tcode = 1005;
	public static final int Xml_No_Field_istart = 1006;
	public static final int Xml_No_Field_iend = 1007;
	public static final int Xml_No_Field_iflag = 1008;
	public static final int Xml_No_Field_auth_name = 1009;
	public static final int Xml_No_Field_auth_id = 1010;
	public static final int Xml_No_Field = 1011;
	public static final int Auth_Name_No_Same = 1012;
	public static final int Auth_Id_No_Same = 1013;
	public static final int Apply_Xml_Illegal = 1014;
	
	public static final int BoxType_No_Define = 1106;
	public static final int cabinet_Not_Exist = 1126;
	public static final int siteCabinet_Not_Exist = 1127;
	public static final int Dispatch_No_Define = 1107;
	public static final int box_Not_Exist = 1125;
	public static final int SiteState_Length_Error = 1128;
	public static final int TerminalSite_No_Match = 1129;
	public static final int TerminalSite_No_Box = 1404;
	public static final int SiteState_Refresh_Execp = 1405;
	public static final int InventoryGoods_No_Exist = 1406;
	
	public static final int Site_PlatVersion_Invalid_Error = 1120;// 版本平台已经禁用
	public static final int Site_No_PlatVersion_Upgrade_Error = 1121;// 终端版本没有更新信息
	public static final int PlatVersion_No_Config_Error = 1122;// 没有平台版本信息
	public static final int PlatVersion_Invalid_Error = 1123;// 平台版本信息已经禁用
	public static final int PlatVersion_OldToNew_Error = 1124;// 平台版本老版本更新新版本错误
	public static final int Site_No_PlatVersion_Error = 1321;// 没有终端平台信息

	public static final int Message_Exist_Same = 1400;
	public static final int Message_No_Exist = 1401;
	public static final int TradeCode_No_Allocate = 1402;
	public static final int Emp_Info_No_Found = 1403;
	public static final int Site_User_Info_Exsit = 1910;
	public static final int SitePerson_Insert_Error = 1911;
	public static final int Emp_Info_Exsit = 1912;
	public static final int User_Info_No_Found = 1913;
	public static final int Site_User_No_Found = 1914;
	public static final int Site_User_Key_Error = 1915;//密匙错误
	public static final int SitePerson_Delete_Error = 1916;
	public static final int User_Delete_Error = 1917;
	public static final int Emp_Delete_Error = 1918;
	public static final int DeliveryOrder_Info_No_Found = 1919;
	public static final int DeliveryOrder_Update_Error = 1920;
	public static final int DeliveryOrderLock_Info_No_Found = 1921;
	public static final int DeliveryOrderLock_Update_Error = 1922;
	public static final int DeliveryOrderChange_Insert_Error = 1923;
	public static final int SiteState_Info_No_Found = 1924;
	public static final int SiteState_Update_Error = 1925;
	public static final int Box_Update_Error = 1926;
	public static final int Box_Info_No_Found = 1927;
	public static final int DepositOrder_Info_No_Found = 1928;
	public static final int DepositOrder_Update_Error = 1929;
	public static final int DepositOrderLock_Info_No_Found = 1930;
	public static final int depositOrderLock_Update_Error = 1931;
	public static final int DepositOrderChange_Insert_Error = 1932;
	public static final int SiteControl_Update_Error = 1933;
	public static final int Site_Corp_No_Carrier = 1934;
	public static final int Site_GeAdmin_No_Found = 1935;
	public static final int Site_Corp_No_Admin = 1936;
	public static final int User_Type_error = 1937;
	public static final int Vending_Cmd_No_Found = 1938;
	public static final int Vending_Event_Exsit = 1939;
	public static final int Vending_Cmd_Exsit = 1940;

	// 1020～1050 通用授权和日志方面的错误代码
	public static final int Partner_Invalid_Auth_Name = 1020;
	public static final int Partner_Invalid_Auth_Id = 1021;
	public static final int Partner_Invalid_State = 1022;
	public static final int Partner_Invalid_Period = 1023;
	public static final int Network_No_Config = 1024;
	public static final int Network_Invalid_Ip = 1025;
	public static final int Network_Invalid_Mac = 1026;
	public static final int Network_Invalid_State = 1027;
	public static final int Trade_Code_No_Define = 1028;
	public static final int Trade_Code_Invalid_State = 1029;
	public static final int Plat_Code_No_Define = 1030;
	public static final int Terminal_No_Auth_Name = 1031;
	public static final int Terminal_Error_Auth_Id = 1032;
	public static final int Terminal_Site_State_Error = 1033;
	public static final int Terminal_Site_Ip_Error = 1034;
	public static final int Terminal_Site_Mac_Error = 1035;
	public static final int Terminal_Site_No_Match = 1036;
	public static final int Terminal_Site_No_Sign = 1037;// 地点没有签名加密信息
	public static final int Terminal_Pro_Over_Time = 1038;// 签名中报文发送时间超时
	public static final int Terminal_Pro_Different_Algo = 1039;// 协议中的算法类型与服务器的不同
	public static final int Terminal_Pro_Sign_Error = 1040;// 报文签名解密不一致
	public static final int Terminal_No_Field_Auth = 1041;// 服务器没有字段加密信息
	public static final int Terminal_Field_Security_Different = 1042;// 服务器没有字段加密信息
	public static final int Terminal_Field_Length_Different = 1043;// 字段解密与规定的长度不一致
	public static final int Customer_Is_Exist = 1130;
	public static final int Terminal_Sign_No_Time = 1044;// 签名开始时间错误
	public static final int Terminal_Potocol_Is_Null = 1045;// 报文为null
	public static final int Terminal_Field_Security_Error = 1046;// 字段解密出错
	public static final int Request_parameter_SiteId_No_Define=1047;
	
	public static final int Site_No_Define = 1104;
	public static final int Delivery_Order_No_LockNumber = 1601;//投递订单无琐箱编号
	public static final int Site_AptNumber_No_Customer = 1602;//地点门牌号没有用户绑定
	public static final int Order_No_Exsit = 1603;//订单不存在
	public static final int Order_attr_No_Exsit = 1604;//订单属性表不存在
	public static final int Order_No_Customer = 1605;//订单没有此账户
	public static final int Site_No_Owner = 1606;//地点没有所属公司
	public static final int Site_Carrier_No_Found = 1607;//地点下没有此投递员
	public static final int Order_Is_Exsit = 1608;//订单已存在
	public static final int OrderApplyChange_No_Exist=1609;//无订单状态信息
	public static final int Product_Num_Lack=1610;//商品可售卖数不足
	public static final int Pay_Total_Error=1611;//支付金额错误
	public static final int Sale_Channel_Error=1612;//订单来源错误
	public static final int BarterOrder_No_Exsit = 1613;//换货单不存在
	public static final int Goods_waitFetch_Exsit = 1614;//货道存在预留商品
	public static final int OrderState_No_Change = 1615;//订单状态不可改变
	public static final int CheckOrder_Is_Exsit = 1616;//盘点单已存在
	/**换货单已存在**/
	public static final int BarterOrder_Is_Exsit = 1617;
	/**订单已支付**/
	public static final int OrderPayState_AlPay = 1618;
	/**订单已失效**/
	public static final int OrderPayState_Invalid = 1619;
	/**商品可售卖数为零或没有该商品了**/
	public static final int Product_NumZero=1620;
	
	public static final int SupplyOrder_No_Exsit = 1621;//补货单不存在或未存在最新的补货完成的单子
	/**交易号为空**/
	public static final int Out_TradeNo_Null = 1621;
	public static final int OrderCancel_Time_Error = 1622;//在规定的时间内不能取消 30 分钟 
	public static final int OrderCancel_Normal_Error = 1623;//商品已出货不能取消 商品只有出货失败或者30分钟后有等待出货的才能取消退款
	/**退款失败**/
	public static final int Refund_Error = 7777;
	/**换货数量大于最大库存**/
	public static final int Barter_Num_More = 1622;
	public static final int Unpaid_Order_Exist = 1623;//存在未支付订单
	public static final int Product_Under_No_Exist = 1624;//商品下架信息不存在
	public static final int Product_Lunder_Exist = 1625;//站点货道商品下架记录已存在
	public static final int Response_MsgServer_URL_Error = 1626;//消息服务器相应错误
	public static final int Config_MsgServer_URL_Error = 1627;//消息服务器地址未定义
	public static final int Order_Box_No_Exsit = 1628;//订单商品货道信息不存在
	public static final int Repeat_Confirm_Error = 1629;//重复确认
	public static final int Confirm_Failed_Error = 1630;//确认失败
	public static final int Payconfig_Wechat_No_Exist = 1631;//公司微信支付配置不存在
	/**非法公司名称**/
	public static final int Partner_Invalid_corp = 1632;
	/**创建订单失败**/
	public static final int Create_Order_fail = 1633;
	public static final int Payconfig_Alipay_No_Exist = 1634;//公司支付宝支付配置不存在
	public static final int Pay_State_No_Change = 1635;//支付状态不可改变
	public static final int Order_Type_No_Normal = 1636;
	
	public static final int Stock_No_Exsit = 1637;//库存不足或没有补货记录
	public static final int UnderNum_Over_Capacity = 1638;//下架数量大于最大货道库存容量数
	public static final int Site_Lane_No_Found = 1639;//站点无货道信息
	public static final int Supply_Vending_No_Found = 1640;//补货配置站点无数据
	public static final int Supply_Product_No_Found = 1641;//补货配置商品无数据
	public static final int Supply_VProduct_No_Found = 1642;//补货配置商品货道无数据
	public static final int OrderApply_PayInfo_Error = 1643;//获取支付信息失败
	public static final int Refund_Pay_Error = 1645;//订单状态不是已支付退款状态不是无的订单
	public static final int Refund_Pay_Server_Error = 1646;//支付系统扣款失败
	public static final int Site_NetState_OffLine = 1647;//站点已离线
	public static final int Supply_Order_No_Found=1648;//没有找到补货单
	public static final int Supply_VOrder_No_Found=1648;//没有找到补货站点
	public static final int SupplyVProduct_No_Product=1649;//货道补货记录没有足够的库存
	public static final int VendingLsdiffer_No_Found=1650;//库存差异没炸到
	public static final int VendingLsdiffer_NoSupply_EROOR=1651;//货道补货记录没有足够的库存模拟入库
	// 1800~5000 input error
	public static final int Request_parameter_No_Define = 1800;
	public static final int Request_parameter_Format_Error = 1801;
	public static final int Request_UserFullName_No_Define = 1702;
	public static final int Request_UserAccessCode_No_Define = 1748;
	public static final int Request_EncryptType_No_Define = 1703;
	public static final int Request_Salt_No_Define = 1704;
	public static final int Request_StationNumber_No_Define = 1705;
	public static final int Request_StationSerialNumber_No_Define = 1706;
	public static final int Request_CompanyName_No_Define = 1707;
	public static final int Request_CompanyId_No_Define = 1708;
	public static final int Request_Unable_time_No_Define = 1709;
	public static final int Request_DateTimeStamp_No_Define = 1710;
	public static final int Request_PhoneNumber_No_Define = 1711;
	public static final int Request_EmailAddress_No_Define = 1712;
	public static final int Request_SuiteAptNumber_No_Define = 1713;
	public static final int Request_NotificationType_No_Define = 1714;
	public static final int Request_SourceType_No_Define = 1715;
	public static final int Request_UnableTime_No_Define = 1716;
	public static final int Request_UserId_No_Define = 1717;
	public static final int Request_CorpId_No_Define=1718;
	public static final int Request_CorpName_No_Define=1719;
	public static final int Request_OrderType_No_Define=1720;
	public static final int Request_DeliverName_No_Define=1721;
	public static final int Request_BarCode_No_Define=1722;
	public static final int Request_PassWord_No_Define=1723;
	public static final int Request_LockNumber_No_Define=1724;
	public static final int Request_CabinetId_No_Define=1725;
	public static final int Request_BoxType_No_Define=1726;
	public static final int Request_BoxTypeId_No_Define=1727;
	public static final int Request_LayerType_No_Define=1728;
	public static final int Request_LocationClear_No_Define=1729;
	public static final int Request_TakerId_No_Define=1730;
	public static final int Request_TakerName_No_Define=1731;
	public static final int Request_OrderId_No_Define=1732;
	public static final int Request_CorpType_No_Define=1733;
	public static final int Request_AdminId_No_Define=1734;
	public static final int Request_CarrierName_No_Define=1735;
	public static final int Request_LockType_No_Define=1736;
	public static final int Request_IssuedId_No_Define=1737;
	public static final int Request_OpenFlag_No_Define=1738;
	public static final int Request_LoginId_No_Define=1739;
	public static final int Request_StateTime_No_Define=1740;
	public static final int Request_BoxId_No_Define=1740;
	public static final int Request_AbnormalTarget_No_Define=1741;
	public static final int Request_AbnormalType_No_Define=1742;
	public static final int Product_Info_No_Found=1743;
	public static final int Request_LockTime_No_Define=1744;
	public static final int Request_InvalidTime_No_Define=1745;
	public static final int Request_UserType_No_Define=1746;
	public static final int Request_BillUrl_No_Define=1747;
	
	public static final int Request_SiteId_No_Define=1748;
	public static final int Request_LoginName_No_Define=1749;

	public static final int Pay_Server_Trade_Execp=1750;//调用支付服务器出现异常
	
	public static final int Check_Info_No_Found=1751;
	public static final int Replen_Info_No_Found=1752;
	public static final int Inventory_Info_No_Found=1753;
	public static final int Clean_Info_No_Found=1754;
	
	public static final int Request_CmdId_No_Define=1755;
	public static final int Request_State_No_Define=1756;
	public static final int Request_Result_No_Define=1757;
	public static final int WechatPayInfo_No_Found=1758;
	public static final int VendingLanePProductState_full =1759 ;// 货道已满
	public static final int VendingLanePProductState_over =1760 ;// 当前库存加实际补货库存大于最大容量
	public static final int VendingLsdifferCurStockDiffer =1761 ;// 当前库存不相等

	// 1900~1999代表通用的错误和数据库方面
	public static final int Program_App_Execp = 1900;
	public static final int Program_Query_Execp = 1901;
	public static final int Program_Insert_Execp = 1902;
	public static final int Program_Update_Execp = 1903;
	public static final int Program_Delete_Execp = 1904;
	public static final int Terminal_Trade_Execp = 1905;
	public static final int Compile_Json_Error = 1906;
	public static final int Query_No_Data = 1999;

	public static final int Monitor_No_SiteId = 1938;// 站点初始化为传输站点
	public static final int Device_Info_Not_Found = 1939;// 站点初始化为传输站点
	
	public static final int Site_ID_NO = 1940;

	// marco define
	public static final String ZxmlRoot = "ZMSG";
	public static final String ZxmlHead = "ZHEAD";
	public static final String ZxmlBody = "ZBODY";
	public static final String ZxmlSpec = "cabinet_column";
	
	public static final String MxmlSMsgId = "SMsgId";
	public static final String MxmlFactoryId = "FactoryId";
	public static final String MxmlAppId = "AppId";
	public static final String MxmlDeviceId = "DeviceId";
	public static final String MxmlNonce = "Nonce";
	public static final String MxmlSessionId= "SessionId";
	public static final String MxmlTimeStamp = "TimeStamp";
	public static final String MxmlSign = "Sign";
	public static final String MxmlContent = "Content";
	
	public static final String StationNumber = "SiteId";
	public static final String StationSerialNumber = "StationSerialNumber";
	public static final String AuthId = "AuthId";
	public static final String AuthName = "AuthName";
	public static final String bcode = "BCode";
	public static final String tcode = "TCode";
	public static final String istart = "IStart";
	public static final String version = "Version";
	public static final String SUCCESS_CODE = "0000";
	public static final String SUCCESS_MSG = "SUCCESS";
	public static final int SUCCESS_TOTNUM = 1;
	public static final int SUCCESS_CURNUM = 1;

	public static final String DATE_TIME_CN = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_CN = "yyyy-MM-dd";
	public static final String DATE_TIME_US = "MM/dd/yyyy HH:mm:ss";
	public static final String DATE_US = "MM/dd/yyyy";
	
	// marco define
	public static final String ZxmlZsign = "ZSIGN";
	public static final String ZxmlHead_TCODE = "tcode";
	public static final String ZxmlZsign_algo = "algo";
	public static final String ZxmlZsign_time = "time";
	public static final String ZxmlZsign_sign = "sign";
	public static final String ZxmlZsign_msgid = "msgid";
	public static final String ZxmlZsign_salt = "salt";
	
	// MQTT
	public static final int msg_type_error = 8900;// type值不正确
	
	// 开箱
	public static final int OPEN_NO_STATE = 8000;// 开箱状态未定义
	public static final int Parameter_incomplete = 8001;// 参数不全
	public static final int No_Site_Door_State = 8002;// 站点门状态
	
	

	//不正确的手机号码
	public static final int Invalid_Mobile = 9000;
	private GConstent() {
	}
}
