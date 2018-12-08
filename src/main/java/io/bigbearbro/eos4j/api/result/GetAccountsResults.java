package io.bigbearbro.eos4j.api.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAccountsResults {
	@JsonProperty("account_names")
	private List<String> accountNames;

	public List<String> getAccountNames() {
		return accountNames;
	}

	public void setAccountNames(List<String> accountNames) {
		this.accountNames = accountNames;
	}

	@Override
	public String toString() {
		return "GetAccountsResults{" +
				"accountNames=" + accountNames +
				'}';
	}
}
