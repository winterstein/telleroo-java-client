package org.sogive.telleroo;

import com.winterwell.json.JSONObject;

public class TDataObj {

	public TDataObj(JSONObject jobj) {
		base = jobj;
	}

	JSONObject base;

	public String getId() {
		return base.getString("id");
	}

	@Override
	public String toString() {
		return base.toString();
	}
	
	
}
