package org.sogive.telleroo;

import com.winterwell.json.JSONObject;

public class TRecipient extends TDataObj {

	public TRecipient(JSONObject jobj) {
		super(jobj);
	}

	public String getName() {
		return base.getString("name");
	}
		
}
