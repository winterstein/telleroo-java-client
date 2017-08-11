package org.sogive.telleroo;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Currency;
import java.util.Properties;

import org.junit.Test;

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
		Object accounts = jtelleroo.getRecipientsList(1);
		System.out.println(accounts);
	}

	
	@Test
	public void testCreateRecipient() throws IOException {
		Telleroo jtelleroo = getTelleroo();
		TRecipient recip = jtelleroo.createRecipient("Dan Test", "GBP", "12341234", "010203", 
				KLegalType.PRIVATE);
		System.out.println(recip);
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
