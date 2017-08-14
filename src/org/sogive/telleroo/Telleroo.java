/**
 * 
 */
package org.sogive.telleroo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.winterwell.json.JSONArray;
import com.winterwell.json.JSONObject;
import com.winterwell.utils.IFn;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.containers.Containers;
import com.winterwell.utils.time.Time;
import com.winterwell.utils.web.WebUtils;
import com.winterwell.utils.web.WebUtils2;
import com.winterwell.web.FakeBrowser;

/**
 * 
 * See http://docs.telleroo.com/
 * 
 * @author daniel
 *
 */
public class Telleroo {

	/**
	 * Normally leave null for the latest version.
	 * 
	 * e.g. Accept: "application/vnd.telleroo.v1"
	 */
	String apiVersion;
	
	String server = "https://api.telleroo.com/";

	private String authorisationToken;

	public Telleroo(String authToken) {
		this.authorisationToken = authToken;
	}
	
	public Telleroo setSandbox(boolean useSandbox) {
		server = useSandbox? "https://sandbox.telleroo.com/" : "https://api.telleroo.com/";
		return this;
	}

	/**
	 * http://docs.telleroo.com/#accounts-list
	 * @return 
	 */
	public List<TAccount> getAccountsList() {
		JSONObject jobj = get("accounts", null);
		JSONArray accs = jobj.getJSONArray("accounts");
		List<TAccount> accounts = Containers.apply(accs, TAccount::new);
		return accounts;
	}
	

	/**
	 * 
	 * @param account
	 * @param start
	 * @param end
	 * @param page Starts at 1
	 * @return
	 */
	public List<TBankTransfer> getTransactions(TAccount account, Date start, Date end, int page) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Map vars = new ArrayMap(
				"account_id", account.getId(),
				"start_date", sdf.format(start),
				"end_date", sdf.format(end),
				"page", page
				);
		JSONObject jobj = get("transactions", vars);
		JSONArray accs = jobj.getJSONArray("transactions");
		List<TBankTransfer> accounts = Containers.apply(accs, TBankTransfer::new);
		return accounts;
	}
	
	public TBankTransfer getTransaction(String id) {
		JSONObject jobj = get("transactions/"+WebUtils.urlEncode(id), null);
		TBankTransfer bt = new TBankTransfer(jobj.getJSONObject("transaction"));
		return bt;
	}


	/**
	 * @param page Starts at 1
	 * http://docs.telleroo.com/#recipients-list
	 */
	public List<TRecipient> getRecipients(int page) {
		assert page > 0 : page;
		JSONObject jobj = get("recipients", new ArrayMap("page", page));
		JSONArray accs = jobj.getJSONArray("recipients");
		List<TRecipient> accounts = Containers.apply(accs, TRecipient::new);
		return accounts;
	}
	
	/**
	 * See http://docs.telleroo.com/#recipient-creation
	 */
	public TRecipient createRecipient(
			String	name,
			String currency_code,
			String	account_no,
			String sort_code,
			KLegalType personOrBusiness
			) {
		Map vars = new ArrayMap(
				"name", name,
				"currency_code", currency_code,
				"account_no", account_no,
				"sort_code", sort_code,
				"legal_type", personOrBusiness
				);
		JSONObject jobj = post("recipients", vars);
		return new TRecipient(jobj.getJSONObject("recipient"));
	}
	
	public void deleteRecipient(String id) {
		FakeBrowser fb = fb();
		fb.setRequestMethod("DELETE");
		Object json = fb.getPage(server+"recipients/"+WebUtils2.urlEncode(id));
		// no response - json is blank
	}

	public TRecipient getRecipient(String id) {
		FakeBrowser fb = fb();
		String json = fb.getPage(server+"recipients/"+WebUtils2.urlEncode(id));
		JSONObject jobj = new JSONObject(json).getJSONObject("recipient");
		return new TRecipient(jobj);
	}
	
	public TBankTransfer createBankTransfer(TBankTransfer bankTransfer) {
		FakeBrowser fb = fb();
		Map vars = bankTransfer.base.getMap();
		String json = fb.post(server+"bank_transfers", vars);
		JSONObject jobj = new JSONObject(json).getJSONObject("transaction");
		return new TBankTransfer(jobj);
	}

	
	private JSONObject get(String endpoint, Map vars) {
		FakeBrowser fb = new FakeBrowser();
		fb.setRequestHeader("Authorization", authorisationToken);
		String json = fb.getPage(server+endpoint, vars);
		return new JSONObject(json);
	}

	private JSONObject post(String endpoint, Map vars) {
		FakeBrowser fb = fb();
		String json = fb.post(server+endpoint, vars);
		return new JSONObject(json);
	}

	private FakeBrowser fb() {
		FakeBrowser fb = new FakeBrowser();
		fb.setUserAgent(null);
		fb.setRequestHeader("Authorization", authorisationToken);
		fb.setDebug(true);
		return fb;
	}

}
