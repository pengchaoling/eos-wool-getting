package io.bigbearbro.eos4j.eosio.chain.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * jsonpath: get_transaction#trx.trx
 * @author wangyan
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignedTransaction extends Transaction {
	@JsonProperty("signatures")
	private List<String> signatures;
	
	@JsonProperty("context_free_data")
	private List<Object> contextFreeData;

	public List<Object> getContextFreeData() {
		return contextFreeData;
	}

	public void setContextFreeData(List<Object> contextFreeData) {
		this.contextFreeData = contextFreeData;
	}

	public List<String> getSignatures() {
		return signatures;
	}

	public void setSignatures(List<String> signatures) {
		this.signatures = signatures;
	}

}
