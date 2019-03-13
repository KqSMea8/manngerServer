package com.lmxf.post.core.utils;

import java.util.HashMap;
import java.util.Map;

import com.lmxf.post.entity.order.OrderApply;

public class GParameter {

	public static Map<String, OrderApply> payOrderNoticeMap = new HashMap<String, OrderApply>();

	/* http protocol access log */
	public static String accessState_normal = "1";
	public static String accessState_invalid = "0";
	public static String accessState_key = "accessState"; // 访问状态

	public static String boxState_key = "boxState"; // 箱子状态

	public static String boxFlag_normal = "0";
	public static String boxFlag_error = "1";
	public static String boxFlag_pause = "2";
	public static String boxFlag_key = "boxFlag"; // 箱子使用标识

	public static String stateFlag_normal = "1";
	public static String stateFlag_invalid = "0";

	public static String orderCurState_nomarl = "01";// 正常
	public static String orderCurState_occupyOver = "02";// 预留超期
	public static String orderCurState_invalid = "03";// 失效
	public static String orderCurState_exception = "04";// 异常
	public static String orderCurState_cancel = "05";// 取消

	public static String orderOrderType_nomarl = "1";// 正常
	public static String orderOrderType_finsh = "2";// 完成
	// 0:投递码 1:取件码
	public static String pincodeType_deliver = "0";// 投递码
	public static String pincodeType_fetch = "1";// 取件码

	public static String userType_customer = "1";// 客户
	public static String userType_maintainer = "2";// 商品维护员
	public static String userType_admin = "3";// 终端管理员

	public static String orderSource_owner = "1";
	public static String orderSource_website = "2";
	public static String orderSource_input = "3";
	public static String orderSource_report = "4";
	public static String orderSource_freebox = "5";

	public static String Corporation_Partner = "0";// 合作方公司
	public static String Corporation_Express = "1";// 快递物流公司
	public static String Corporation_All = "2";// 全部的公司

	public static String user_corp_key = "userCorp";// 用户所属公司

	public static String mailServer_key = "mailServer"; // 邮件服务参数

	public static String pushMemCachedOF_key = "pushMemCachedOF";// 是否开启推送服务缓存key
	public static String pushMemCachedOF_On_key = "pushMemCachedOF_On";// 开启推送服务缓存key
	public static String pushMemCachedOF_On = "01";// 开启推送服务缓存key

	public static String encodeType_none = "00";
	public static String encodeType_3Des = "01";
	public static String encodeType_aes = "02";
	public static String encodeType_rsa = "03";

	public static String validFlag_normal = "1";
	public static String validFlag_invalid = "0";

	public static String curFlag_normal = "0";
	public static String curFlag_invalid = "1";

	public static String siteNetState_key = "siteUseState";// 地点状态
	public static String siteNetState_online = "0";// 在线
	public static String siteNetState_offline = "1";// 离线
	public static String siteUseState_unavailable = "2";// 不可用

	public static String issued_require_state = "0";// 需要要下发的状态
	public static String issued_already_state = "1";// 已下发
	public static String issued_confirm_state = "2";// 已下发确认

	public static String siteState_startBox = "05";
	public static String siteState_invalid = "06";
	public static String siteState_lockBox = "06";

	public static String ipType_static = "1";
	public static String ipType_dynamic = "2";

	public static int User_Query_Page_Num = 20;

	public static String commProtocal_udp = "1";
	public static String commProtocal_http = "2";
	public static String commProtocal_soap = "3";
	public static String commProtocal_demo = "4";
	public static String commProtocal_network = "5";

	public static String siteControlLogState_sheart = "01";// 心跳通知开始
	public static String siteControlLogState_dataNoIuessed = "02";// 数据未下发
	public static String siteControlLogState_dataAlIuessed = "03";// 数据已下发
	public static String siteControlLogState_dataAlAffirm = "04";// 数据已确认
	public static String siteControlLogState_dataAffirmError = "05";// 数据确认错误
	public static String siteControlLogState_dataRepeartAffirm = "06";// 数据重复确认
	public static String siteControlLogState_dataAffirmNoOrder = "07";// 数据未确认
	public static String siteControlLogState_eheart = "08";// 心跳通知结束

	public static String siteControlConfigState_nomarl = "01";// 地点下发配置正常
	public static String siteControlConfigLog_on = "01";// 打开地点下发日志

	public static String deverifiState_normal = "01";// 需要解密

	public static String tradeAuthState_normal = "01";// 正常状态

	public static String tradeAuthAlgo_3DES = "3DES";

	public static String securityState_decrypt = "01";// 解密类型
	public static String securityState_encryption = "02";// 加密类型

	public static String fieldLogState_differentText = "01";// 解密不符合规则

	public static String siteSignState_normal = "01";// 签名算法状态
	public static String siteSignState_invalid = "02";// 签名算法状态

	public static String siteSignIsLog_normal = "01";// 记录日志

	public static String fieldAuthIsLog_normal = "01";// 记录日志

	public static String pro_auth_name = "auth_name";
	public static String pro_auth_id = "auth_id";

	public static String issuedPageSize_key = "issuedPage";// 已经确认

	public static String issued_update_success = "01";// 修改下发状态成功
	public static String isssued_update_fail = "02";// 修改下发状态失败
	public static String isssued_no_confirm = "03";// 下发状态未确认
	public static String invalid_single_number = "04";// 无效单号或数据
	public static String isssued_already_ok = "05";// 已经确认
	public static String isssued_new_data = "06";// 重新需要获取最新数据
	public static String isssued_confrim_error_golocker = "07";// golocker服务器引起的确认失败
	public static String isssued_confrim_error_zhilai = "08";// zhilai服务器引起的确认失败

	public static String issuedDelayTime_key = "issuedDelay";// 已经确认

	// 系统人员状态
	public static String userState_normal = "1";
	public static String userState_invalid = "2";

	public static String success_retcode = "0";
	public static String repeat_retcode = "1";
	public static String fail_retcode = "2";

	public static String issued_sitePerson_trade_code = "1221";// 人员查询
	public static String issued_sitePerson_trade_desc = "issuedSitePerson";// 下发人员信息
	public static String issued_orderApply_trade_code = "1223";// 订单查询
	public static String issued_orderApply_trade_desc = "issuedPartnerOrder";// 确认图片
	public static String issued_orderBarter_trade_code = "1225";// 换货订单查询
	public static String issued_orderBarter_trade_desc = "issuedBarterOrder";// 换货订单描述
	public static String issued_siteCmd_trade_code = "1227";// 站点命令下发
	public static String issued_siteCmd_trade_desc = "issuedSiteCmd";// 站点命令描述
	public static String issued_recovery_trade_code = "1229";// 回收信息下发
	public static String issued_recovery_trade_desc = "issuedRecoveryOrder";// 回收信息描述
	public static String issued_orderCheck_trade_code = "1235";// 回收信息下发
	public static String issued_orderCheck_trade_desc = "issuedCheckOrder";// 回收信息描述
	public static String issued_orderReplenishment_trade_code = "1237";// 补货信息下发
	public static String issued_orderReplenishment_trade_desc = "issuedReplenishmentOrder";// 补货信息描述
	public static String issued_siteLogFile_trade_code = "1239";// 补货信息下发
	public static String issued_siteLogFile_trade_desc = "issuedSiteLogFileOrder";// 补货信息描述

	public static String zhilai_copy_id = "8888";
	public static String zhilai_copy_name = "zhilai system";

	public static String partnerOrderState_nomarl = "1";// 正常
	public static String partnerOrderState_cancel = "2";// 取消
	public static String partnerOrderState_ban = "3";// 禁止投递

	// 级别程度
	public static String eventOperLevel_01 = "01";// 级别程度
	// 操作对象
	public static String eventOperObj_box = "BOX";// 箱子
	// 紧急打开箱子 操作码
	public static String eventOperCode_BoxOpen = "0001";// 紧急打开箱
	public static String eventOperCode_BoxClean = "0002";// 设置清洁箱子
	public static String eventOperCode_BoxDamage = "0003";// 设置脏箱
	public static String eventOperCode_BoxSoiled = "0004";// 损坏

	// 终端事件操作动作
	public static String eventOperAction_open = "OPEN";// 打开
	public static String eventOperAction_clean = "CLEAN";// 清洁
	public static String eventOperAction_soiled = "SOILED";// 脏箱
	public static String eventOperAction_damage = "DAMAGE";// 损坏

	// 设置允许投递员紧急取走
	public static String partnerOrderEmergency_yes = "1";// 允许投递员紧急取走
	public static String partnerOrderEmergency_no = "0";// 不允许投递员紧急取走

	// 设置允许投递员紧急取走
	public static String cameraPicUpload_yes = "1";// 已上传
	public static String cameraPicUpload_no = "0";// 未上传
	public static String cameraPicUpload_delete = "2";// 站点已删除

	public static String superCompany_id = "8888";// 站点已删除

	public static String site_version_invalid = "02";// 终端对于平台版本信息不可用
	public static String site_version_normal = "01";// 终端对于的平台信息可用

	public static String plat_version_invalid = "02";// 平台版本信息不可用
	public static String plat_version_normal = "01";// 平台版本信息可用

	public static String plat_version_log_record = "01";

	/** 查询01 **/
	public static String UpgradeType_query = "01";// 查询
	public static String UpgradeType_begin = "02";// 准备升级
	public static String UpgradeType_ing = "03";// 正在升级
	public static String UpgradeType_success = "04";// 查询升级成功
	public static String UpgradeType_fail = "05";// 查询升级失败
	public static String UpgradeType_down = "06";// 查询文件下载失败
	public static String UpgradeType_pro = "07";// 终端进程无法结束
	public static String UpgradeType_startNoPro = "08";// 终端进程无法启动
	public static String UpgradeType_gost = "09";// 恢复为原版本

	public static String SiteVersionUpgradeDown = "upgradeDown";// 阿里云密匙key
	public static String SiteVersionUpgradeDown_key = "upgradeDown_key";// 阿里云密匙
	public static String SiteVersionUpgradeDown_Security = "dkxncvjhsh12jdkxbxbv_23fds";// 默认key

	public static String ParcelValidateInfo = "ParcelValidateInfo";
	public static String ParcelValidateInfo_validateDays = "ParcelValidateInfo_validateDays";

	// ========================golocker======================
	public static String authState_nomarl = "1";
	public static String authState_invalid = "0";

	// 订单状态
	public static String orderState_apply = "1";// 申请
	public static String orderState_advanceFetch = "2";// 提前取货
	public static String orderState_customerfetch = "3";// 客户已取货
	public static String orderState_recovery = "4";// 已回收
	public static String orderState_cancel = "5";// 取消

	public static String OrderBoxCurState_waitFetch = "01";// 待取货
	public static String OrderBoxCurState_alFetch = "02";// 已取货
	public static String OrderBoxCurState_alrecovery = "03";// 已回收
	public static String OrderBoxCurState_cancel = "04";// 取消

	// 不是订单主状态
	public static String orderState_reserveSuccess = "20";
	public static String orderState_reserveFail = "21";
	// 锁箱状态
	public static String lockState_success = "1";// 预留成功
	public static String lockState_fail = "2";// 预留失败

	public static String enforceType_register = "0";// 否强制注册

	public static String zhilai_pay_auth_id = "zhilai_web";
	public static String zhilai_pay_auth_name = "zhilai_web";
	public static String zhilai_pay_corp_id = "201710";
	public static String zhilai_pay_corp_name = "深圳智莱";

	public static String orderImageCurState_noupload = "0";// 未上传

	public static String siteBarterCurState_finsh = "1";// 完成
	public static String boxReplenishmentCurState_finsh = "1";// 完成

	public static String noticeObj_key = "noticeObj"; // 通知对象
	public static String noticeObj_order = "01"; // 订单
	public static String noticeObj_site = "02"; // 地点

	public static String notice_type_key = "noticeType";// 通知方式
	public static String noticeType_mobile = "01";// 短信
	public static String noticeType_email = "02";// 邮件

	public static String procFlag_key = "procFlag";// 通知处理状态
	public static String procFlag_no = "0";// 未处理
	public static String procFlag_success = "1";// 发送成功
	public static String procFlag_failed = "2";// 发送失败

	public static String replenishment_key = "replenishment"; // 是否需要补货
	public static String replenishment_yes = "0"; // 是
	public static String replenishment_no = "1"; // 否

	public static String isLack_key = "isLack"; // 是否需要补货
	public static String isLack_no = "0"; // 否
	public static String isLack_yes = "1"; // 是

	public static String noticeFlag_key = "noticeFlag"; // 是否已通知
	public static String noticeFlag_no = "0"; // 否
	public static String noticeFlag_yes = "1"; // 是

	public static String OrderAbnormalTarget_order = "01";
	public static String OrderAbnormalTarget_site = "02";

	public static String OrderAbnormalType_DeliveryBoxNoOpen = "0101";
	public static String OrderAbnormalType_FetchBoxNoOpen = "0102";
	public static String OrderAbnormalType_RecoveryBoxNoOpen = "0103";
	public static String OrderAbnormalType_BoxFault = "0201";

	public static String OrderAbnormalType_None = "0";

	public static String boxState_normal = "1";
	public static String boxState_damage = "2";
	public static String boxState_stop = "3";

	public static String platCode_partner = "01";

	public static String orderType_normal = "1";// 正常
	public static String orderType_close = "2";// 关闭

	public static String OrderChangeProc_waitPush = "1";// 等待推送
	public static String OrderChangeProc_Pushed = "2";// 已推送
	
	public static String OrderChangeProState_waitHandle = "1";// 等待处理
	public static String OrderChangeProState_waitFinsh = "2";// 处理完成

	public static String LockState_success = "1";// 预留成功

	// 订单支付状态
	public static String payState_wait = "1"; // 待支付
	public static String payState_success = "2"; // 支付成功
	public static String payState_failed = "3"; // 支付失败

	// 订单退款状态
	public static String returnType_none = "0"; // 无
	public static String returnType_wait = "1"; // 等待退款
	public static String returnType_success = "2"; // 成功退款
	public static String returnType_failed = "3"; // 退款失败

	// 订单异常类型
	public static String abnormalType_none = "0"; // 无
	public static String abnormalType_fetchFailed = "1"; // 取货故障
	public static String abnormalType_customerCancel = "2"; // 客户取消

	public static String supplyOrderState_wait = "1";// 等待补货
	public static String supplyOrderState_finish = "2";// 补货完成

	public static String supplyOrderFinishState_normal = "1";// 正常完成
	public static String supplyOrderFinishState_over = "2";// 超期完成

	public static String supplyOrderType_allSupply = "1";// 全补齐
	public static String supplyOrderType_warnSupply = "2";// 阈值补齐

	public static String supplyOrderStockState_unchecked = "1";// 未审核
	public static String supplyOrderStockState_alreadyOut = "2";// 已出库

	public static String supplyVproductFinishState_in = "1"; // 未出柜
	public static String supplyVproductFinishState_out = "2"; // 已出柜

	public static String barterOrderState_wait = "1";

	// 订单回收状态
	public static String recoveryType_normal = "0"; // 正常
	public static String recoveryType_wait = "1"; // 待回收
	public static String recoveryType_recovered = "2";// 已回收

	public static String pushflag_wait = "01"; // 等待推送
	public static String pushflag_success = "02"; // 已推送
	public static String pushflag_failed = "03"; // 推送失败
	// 图片类型 01:终端 02:微信 03:android 04:ios
	public static String productImageChanelType_terminal = "01";
	public static String productImageChanelType_wechat = "02";
	public static String productImageChanelType_android = "03";
	public static String productImageChanelType_ios = "04";
	public static String productImageChanelType_maintenance = "05";

	// 单据类型 1:正常补货 2:换货补货
	public static String orderType_replenishment = "1";
	public static String orderType_barter = "2";

	// 盘盈盘亏类型 1:盘盈 2:盘亏
	public static String checkType_overage = "1";
	public static String checkType_loss = "2";
	public static String checkType_normal = "3";

	// 1:维修员 2:补货员3:管理员
	public static String sitePersonEmpType_enginer = "1";
	public static String sitePersonEmpType_replen = "2";
	public static String sitePersonEmpType_admin = "3";
	// 01:无 02:机械故障 03:货道故障04:缺货 05:未知
	public static String siteWarnType_normal = "01";

	// 订单来源渠道 1:微信 2:android 3:ios 4:终端
	public static String orderSaleChannel_wechat = "1";
	public static String orderSaleChannel_android = "2";
	public static String orderSaleChannel_ios = "3";
	public static String orderSaleChannel_terminal = "4";

	// 补货状态1:等待补货 2:补货完成
	public static String supplyVproductState_wait = "1";
	public static String supplyVproductState_finish = "2";

	// 换货状态1:等待换货 2:换货完成
	public static String Barter_wait = "1";
	public static String Barter_finish = "2";

	public static String payTime_over = "30";// 支付超期时间(默认30分钟）
	public static String fetchTime_over = "2";// 取件超期时间（小时）

	// 库存状态 1：正常 2：缺货 3：无货
	public static String inventoryState_normal = "1";
	public static String inventoryState_lack = "2";
	public static String inventoryState_none = "3";

	public static String unitId = "unit_id"; // 计量单位

	// 警告类型 01:无 02:机械故障 03:货道故障04:缺货 05:未知
	public static String warnType_none = "01";
	public static String warnType_mechanical = "02";
	public static String warnType_goods = "03";
	public static String warnType_lack = "04";
	public static String warnType_other = "05";

	// 警报状态 1:正常 2:报警
	public static String warnState_normal = "1";
	public static String warnState_invalid = "2";

	// 设备参数运行状态
	public static String siteEnvState_normal = "1";
	public static String siteEnvState_invalid = "2";
	public static String siteEnvState_error = "3";

	// 柜型
	public static String siteCabinetKind_big = "1"; // 大型售卖机
	public static String siteCabinetKind_mini = "2"; // MINI柜
	public static String siteCabinetKind_rfid = "3"; // RFID柜
	public static String siteCabinetKind_basic = "4"; // 基本款售卖机
	public static String siteCabinetKind_smallScreen = "5"; // 小屏售卖机
	public static String siteCabinetKind_bigScreen = "6"; // 大屏售卖机

	public static String commtype_key = "commtype";// 通讯方式
	public static String commtype_http = "1";// http
	public static String commtype_soap = "2";// soap
	public static String commtype_socket = "3";// socket
	public static String commtype_mqtt = "4";// mqtt

	// 平台代码
	public static String platCode_Platform = "01"; // 管理台
	public static String platCode_WeChatOfficialAccountPurchase = "02"; // 微信公众号购买端
	public static String platCode_Terminal = "03"; // 终端
	public static String platCode_WeChatAppletPurchase = "04"; // 微信小程序购买端
	public static String platCode_WeChatOfficialAccountReplenishment = "05"; // 微信公众号补货端
	public static String platCode_AndroidPurchase = "06"; // 安卓购买App
	public static String platCode_AndroidReplenishment = "07"; // 安卓补货App端
	public static String platCode_MessageTransfer = "08"; // 消息中转站

	// 平台名称
	public static String platName_Platform = "platform"; // 管理台
	public static String platName_WeChatOfficialAccountPurchase = "weChatOfficialAccountPurchase"; // 微信公众号购买端
	public static String platName_Terminal = "terminal"; // 终端
	public static String platName_WeChatAppletPurchase = "weChatAppletPurchase"; // 微信小程序购买端
	public static String platName_WeChatOfficialAccountReplenishment = "weChatOfficialAccountReplenishment"; // 微信公众号补货端
	public static String platName_AndroidPurchase = "androidPurchase"; // 安卓购买App
	public static String platName_AndroidReplenishment = "androidReplenishment"; // 安卓补货App端
	public static String platName_MessageTransfer = "messageTransfer"; // 消息中转站

	// 01:微信售货机购买公众号  02:mini柜微信购买小程序 03:大屏款安卓售货终端  04:小屏款安卓售货终端 05:基本款款安卓售货终端
	public static String saleChannel_weChatGTerminal = "01"; // 微信售货机购买公众号
	public static String saleChannel_miniWeChatAppTerminal = "02"; // mini柜微信购买小程序
	public static String saleChannel_bigScreenAndroidTerminal = "03"; // 大屏款安卓售货终端
	public static String saleChannel_smallScreenAndroidTerminal = "04"; // 小屏款安卓售货终端
	public static String saleChannel_baseScreenSCMTerminal = "05"; // 基本款款安卓售货终端

	// 是否立即出柜
	public static String outState_yes = "1"; // 是
	public static String outState_no = "2"; // 否

	// 优惠方式
	public static String favWay_discount = "01"; // 购买折扣
	public static String favWay_reduce = "02"; // 消费立减

	// 优惠对象
	public static String favType_vending = "1"; // 整机
	public static String favType_product = "2"; // 单品

	// 订单状态变化
	public static String orderChange_apply = "01"; // 申请
	public static String orderChange_paySuccess = "02"; // 支付成功
	public static String orderChange_payFailed = "03"; // 支付失败
	public static String orderChange_advanceFetch = "04"; // 提前取货
	public static String orderChange_customerFetch = "05"; // 客户已取货
	public static String orderChange_customerCancel = "06"; // 客户取消
	public static String orderChange_fetchFailed = "07"; // 出货故障
	public static String orderChange_recovery = "08"; // 已回收
	public static String orderChange_refund = "09"; // 退款
	public static String orderChange_end = "10"; // 完结

	// 补货账单售出明细对账状态
	public static String statementProductCurState_in = "1"; // 未出柜
	public static String statementProductCurState_out = "2"; // 已出柜

	// 补货账单销售状态
	public static String statementSupplySaleState_unSale = "1"; // 未开始销售
	public static String statementSupplySaleState_inSale = "2"; // 销售中
	public static String statementSupplySaleState_saleOut = "3"; // 销售完成

	// 补货账单对账状态
	public static String statementSupplyState_waitSaleOut = "1"; // 等待售完
	public static String statementSupplyState_waitStatement = "2"; // 等待对账

	// 补货账单结算状态
	public static String statementSupplyCurState_unSubmit = "1"; // 未提交
	public static String statementSupplyCurState_alreadySubmit = "2"; // 已提交

	// 商品下架状态
	public static String productUnderState_wait = "1"; // 等待下架
	public static String productUnderState_doing = "2"; // 下架中
	public static String productUnderState_finish = "3"; // 已下架

	public static int MsgServer_Request_Success = 200;

	// 订单商品货道出柜状态
	public static String orderBoxOutState_in = "1"; // 未出柜(代表商品还在柜子中)
	public static String orderBoxOutState_normalOut = "2"; // 正常已出柜(代表商品已存放在出货口)
	public static String orderBoxOutState_abnormalOut = "3"; // 异常已出柜(货道出货失败可以另外货道代替出货)
	public static String orderBoxOutState_outFailed = "4"; // 出柜失败

	// 商品是否过期
	public static String productOverState_yes = "1"; // 是
	public static String productOverState_no = "2"; // 否

	// 补货账单售出明细出柜类型
	public static String outType_sale = "1"; // 销售
	public static String outType_under = "2"; // 下架

	// 订单推送状态
	public static String pushState_unpush = "1"; // 未推送
	public static String pushState_pushed = "2"; // 已推送
	public static String pushState_pushSuccess = "3"; // 推送成功
	public static String pushState_pushFailed = "4"; // 推送失败

	// 售货机命令执行结果
	public static String state_executing = "0"; // 执行中
	public static String vendingCmd_executing = "0"; // 执行中
	public static String vendingCmd_success = "1"; // 执行成功
	public static String vendingCmd_failed = "2"; // 执行失败
	

	// 支付方式
	public static String payType_Alipay = "01"; // 支付宝扫码
	public static String payType_weChat = "02"; // 微信扫码
	public static String payType_weChatG = "03"; // 微信公众号
	public static String payType_weChatApp = "04"; // 微信小程序支付
	public static String payType_noPay = "99"; // 无需支付默认支付成功

	// 申请订单支付方式
	public static String orderApplyPayType_Alipay = "01"; // 支付宝扫码
	public static String orderApplyPayType_weChat = "02"; // 微信扫码

	// 服务器异步通知页面路径
	public static String notify_url = "/aliNotify";
	// 需http://格式的完整路径，不能加?id=123这类自定义参数
	// 页面跳转同步通知页面路径
	public static String return_url = "return_url.action";
	// 需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
	// 交易完成后跳转的URL
	public static String qrcode_return_url = "alipayQrcode_return_url.action";
	// 接收支付宝扫码通知的URL
	public static String aliQrcode_notify_url = "/aliNotify";
	// 接收财付通通知的URL
	public static String qrcode_notify_url = "qrcode_notify_url.action";

	public static String vendingCmdSate_buy = "1";// 售卖
	public static String vendingCmdSate_rep = "2";// 补货

	public static String vendingCmdLazy_no = "1";// 即時推送
	public static String vendingdCmdType_door = "05";// 门对象
	public static String vendingdCmd_doorOpenSale = "0501";// 门打开售卖
	public static String vendingdCmd_doorOpenRep = "0502";// 门打开补货

	public static String vendingdCmdType_box = "04";// 箱门
	public static String vendingdCmd_boxOpen = "0401";// 开启箱门
	// 商品状态 1:可售 2:已过期 4:等待下架 3:已下架
	public static String vendingLanePProductSate_sale = "1";// 可售
	public static String vendingLanePProductSate_over = "2";// 已过期
	public static String vendingLanePProductSate_under = "3";// 已下架
	public static String vendingLanePProductSate_wunder = "4";// 等待下架

	public static String vendingCurState_yesValidate = "1"; // 已认证
	public static String vendingCurState_noValidate = "2"; // 未认证

	public static String vendingSupplyState_noSupply = "0";// 无补货
	public static String vendingSupplyState_waitSupply = "1";// 等待补货
	public static String vendingSupplyState_finshSupply = "2";// 补货完成

	public static String vendingUnderState_noUnder = "0";// 无下架
	public static String vendingUnderState_waitUnder = "1";// 等待下架
	public static String vendingUnderState_finshUnder = "2";// 完成下架

	public static String statementProductCurState_noOut = "1";// 未出柜
	public static String statementProductCurState_yesOut = "2";// 已出柜

	public static String productLunderCurState_wait = "1";// 等待下架
	public static String productLunderCurState_alUnder = "2";// 已下架

	public static String productVunderCurState_wait = "1";// 等待下架
	public static String productVunderCurState_alUnder = "2";// 已下架

	public static String payTradeType_order = "01";// 支付订单类型

	public static String payCurrencyType_rmb = "0001";// 人民币

	public static String payFeeType_saleProduct = "01";// 售买单个产品金额

	public static String payTradeTypeSuccess_pay = "1";// 支付成功
	public static String payTradeTypeSuccess_refund = "2";// 退款成功

	// 补货状态1:补货已过期 2:补货未过期
	public static String supplyVproductInvalidState_yesOver = "1";
	public static String supplyVproductInvalidState_noOver = "2";
	// 缓存名称
	public static String cacheServer_name="serverCache";
	// 缓存名称
	public static String supplyOrderDType_nomarl="1";
	public static String supplyOrderDType_diff="2";
	
	public static String vendingLanepLaneState_nomarl="1";//货道正常
	public static String vendingLanepLaneState_damage="2";//货道损坏
	public static String vendingLanepLaneState_stop="3";//货道停用
	
	//1:入库 2:出库如果当前库存量大于设置后的库存量则是出库，如果当前库存量小于设置后的库存量则出入库出库代表某些订单出货失败要更正为出货成功入库代表默写订单出库成功要更正为出库失败
	public static String VendingLsdifferHandleType_inbound="1";
	public static String VendingLsdifferHandleType_outbound="2";
	//状态 1:待处理 2:超期 2:已处理
	public static String VendingLsdifferCurState_waithandle="1";
	public static String VendingLsdifferCurState_over="2";
	public static String VendingLsdifferCurState_alhandle="3";
	
	public static String OrderBoxCorrectState_yes="2";//已矫正
	public static String OrderBoxCorrectState_no="1";//未矫正
	
	public static String platType_android="01";//安卓机型
	public static String platType_windows="02";//windows机型
	public static String platType_SCM="03";//单片机机型
	
	public static int outLaneProductReplace_no=1;//未替换
	public static int outLaneProductReplace_yes=2;//已替换
	
	public static String advertDeviceCurState_witeExecute="1";//等待执行
	public static String advertDeviceCurState_executing="2";//执行中
	public static String advertDeviceCurState_finshExecute="3";//执行完成
}
