package com.alipay.api.response;

import java.util.List;
import com.alipay.api.internal.mapping.ApiField;
import com.alipay.api.internal.mapping.ApiListField;
import com.alipay.api.domain.FeeRecords;

import com.alipay.api.AlipayResponse;

/**
 * ALIPAY API: alipay.ecapiprod.drawndn.feerecord.query response.
 * 
 * @author auto create
 * @since 1.0, 2016-03-29 11:34:27
 */
public class AlipayEcapiprodDrawndnFeerecordQueryResponse extends AlipayResponse {

	private static final long serialVersionUID = 3671849829948263338L;

	/** 
	 * 费用交易流水
	 */
	@ApiListField("fee_records")
	@ApiField("fee_records")
	private List<FeeRecords> feeRecords;

	/** 
	 * 唯一标识这次请求
	 */
	@ApiField("request_id")
	private String requestId;

	public void setFeeRecords(List<FeeRecords> feeRecords) {
		this.feeRecords = feeRecords;
	}
	public List<FeeRecords> getFeeRecords( ) {
		return this.feeRecords;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getRequestId( ) {
		return this.requestId;
	}

}