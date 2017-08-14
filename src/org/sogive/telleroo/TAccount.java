package org.sogive.telleroo;

import com.winterwell.json.JSONObject;

public class TAccount extends TDataObj {
	
	public TAccount(JSONObject jobj) {
		super(jobj);
	}
	
	/**
	 * @return 100x to avoid floating points. E.g. £1 = 100
	 */
	public long getBalance() {
		return base.getLong("balance");
	}
	
	// TODO
	
	public String getSortCode() {
		return base.getString("sort_code");
	}
	public String getAccountNo() {
		return base.getString("account_no");
	}
	public String getCurrencyCode() {
		return base.getString("currency_code");
	}
	
//	,"name":"SoGive","id":"48ed0257-4f0b-4086-a9d1-da70201d6280","tag":null,
//	"sort_code":"2314**","account_no":"174427**","currency_code":"GBP",
//	"status":"initialized"}]

}
