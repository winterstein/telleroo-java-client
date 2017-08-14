package org.sogive.telleroo;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import com.winterwell.utils.Printer;
import com.winterwell.utils.Utils;
import com.winterwell.utils.io.ArgsParser;
import com.winterwell.utils.io.FileUtils;

public class TellerooTest {

	@Test
	public void testGetAccountsList() throws IOException {
		Telleroo jtelleroo = getTelleroo();
		Object accounts = jtelleroo.getAccountsList();
		System.out.println(accounts);
	}

	@Test
	public void testGetRecipientsList() throws IOException {
		Telleroo jtelleroo = getTelleroo();
		Object accounts = jtelleroo.getRecipients(1);
		System.out.println(accounts);
	}

	@Test
	public void testCreateBankTransfer() throws IOException {
		Telleroo jtelleroo = getTelleroo();		
		TRecipient recipient = jtelleroo.getRecipients(1).get(0);
		TAccount acc = jtelleroo.getAccountsList().get(0);
		TBankTransfer bankTransfer = new TBankTransfer(acc, recipient, 100);		
		TBankTransfer bt = jtelleroo.createBankTransfer(bankTransfer);
		System.out.println(bt);
	}

	@Test
	public void testGetTransactions() throws IOException {
		Telleroo jtelleroo = getTelleroo();		
		TAccount acc = jtelleroo.getAccountsList().get(0);
		List<TBankTransfer> trans = jtelleroo.getTransactions(acc, new Date(), new Date(), 1);
		System.out.println(trans);
	}
	
	@Test
	public void testCreateDeleteRecipient() throws IOException {
		Telleroo jtelleroo = getTelleroo();
		TRecipient recip = jtelleroo.createRecipient("Dan Test", "GBP", "12341234", "010203", 
				KLegalType.PRIVATE);
		System.out.println(recip);
		
		TRecipient recip2 = jtelleroo.getRecipient(recip.getId());
		System.out.println(recip2);
		
		jtelleroo.deleteRecipient(recip.getId());		
	}

	private Telleroo getTelleroo() throws IOException {
		Properties props = new Properties();
		File file = new File("config/telleroo.properties");
		props.load(FileUtils.getReader(file));
		String authToken = props.getProperty("sandbox.authToken");
		Telleroo jtelleroo = new Telleroo(authToken).setSandbox(true);
		return jtelleroo;
	}

}
