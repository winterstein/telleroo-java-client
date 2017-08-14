/**
 * 
 */
package org.sogive.telleroo;

import java.util.List;
import java.util.Map;

import com.winterwell.json.JSONArray;
import com.winterwell.json.JSONObject;
import com.winterwell.utils.IFn;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.containers.Containers;
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
	 * @param page Starts at 1
	 * http://docs.telleroo.com/#recipients-list
	 */
	public List<TRecipient> getRecipientsList(int page) {
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
		return new TRecipient(jobj);
	}

	private JSONObject get(String endpoint, Map vars) {
		FakeBrowser fb = new FakeBrowser();
		fb.setRequestHeader("Authorization", authorisationToken);
		String json = fb.getPage(server+endpoint, vars);
		return new JSONObject(json);
	}

	private JSONObject post(String endpoint, Map vars) {
		FakeBrowser fb = new FakeBrowser();
		fb.setUserAgent(null);
		fb.setRequestHeader("Authorization", authorisationToken);
		fb.setDebug(true);
		String json = fb.post(server+endpoint, vars);
		return new JSONObject(json);
	}

}
