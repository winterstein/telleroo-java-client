package org.sogive.telleroo;

import java.util.ArrayList;
import java.util.Collections;

import com.winterwell.json.JSONObject;
import com.winterwell.utils.StrUtils;
import com.winterwell.utils.containers.Containers;
import com.winterwell.utils.time.Time;

public class TBankTransfer extends TDataObj {

	/**
	 * Make an outgoing transfer object for posting.
	 * @param from
	 * @param to
	 */
	public TBankTransfer(TAccount from, TRecipient to, long pence) {
		this(new JSONObject());
		base.put("account_id", from.getId());
		base.put("currency_code", from.getCurrencyCode());
		setRecipient(to);
		setAmount(pence);
		// stamp: details + day
		// sort keys for repeatability
		ArrayList<String> list = new ArrayList(base.getMap().keySet());
		Collections.sort(list);
		String s = new Time().ddMMyyyy()+Containers.apply(list,
				k -> k+":"+base.get(k));
		setIdempotentKey(StrUtils.md5(s));
	}
	
	public TBankTransfer(JSONObject jobj) {
		super(jobj);
	}

	public TBankTransfer setAmount(long pence) {
		base.put("amount", pence);
		return this;
	}
	
	public long getAmount() {
		return base.getLong("amount");
	}
	
	public TBankTransfer setRecipient(TRecipient recipient) {
		base.put("recipient_id", recipient.getId());
		return this;
	}
	
	public TBankTransfer setIdempotentKey(String idempotent_key) {
		base.put("idempotent_key", idempotent_key);
		return this;
	}
}
