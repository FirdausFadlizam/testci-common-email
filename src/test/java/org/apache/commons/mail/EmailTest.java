package org.apache.commons.mail;

import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.subethamail.smtp.server.SMTPServer;

public class EmailTest {
	
	private Email email; 
	
	@Before 
	public void setup () {
		email = new EmailDummy();
	}
	
	
	//test email
	@Test
	public void addBccTest() {
	
		try {
			String emailTemp = "";
			Email emailAddress = email.addBcc("firdausfadlizam@gmail.com");
			for(InternetAddress x : emailAddress.getBccAddresses()) {
				emailTemp = x.getAddress();
			}
			assertEquals("firdausfadlizam@gmail.com", emailTemp );
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//test multiple email
	@Test
	public void addBccTestWithoutMultipleEmail() {
		
		String firstEmail = "firdaus@gmail.com";
		String secondEmail = "max@yahoo.com";
		String thirdEmail = "alex@umich.edu";
		String fourthEmail = "jay@hotmail";
		
		List<String>testVar = new ArrayList<String>();
		testVar.add(firstEmail);
		testVar.add(secondEmail);
		testVar.add(thirdEmail);
		testVar.add(fourthEmail);
	
		List<String> hostName = new ArrayList<String>();
		
		try {
			email.addBcc(firstEmail, secondEmail, thirdEmail, fourthEmail);
			List <InternetAddress> emailList = email.getBccAddresses();

		   for(InternetAddress x: emailList) {
			   hostName.add(x.getAddress());   
		   }
			assertEquals(testVar, hostName);
			
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//test empty email 
	@Test 
	public void addBCCTestwithEmptyEmail()  {
		
		String empty[] = {};

				try {
					email.addBcc(empty);
				} catch (EmailException e) {
					assertEquals("Address List provided was invalid",e.getMessage());

					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
	}
	
	//test single email
	@Test
	public void addCCTest() {
		
		String address = "firdausfadlizam@gmail.com";
		List<InternetAddress>hostName = new ArrayList<InternetAddress>();
		String retrievedAddress = "";
		try {
			email.addCc(address);
			List<InternetAddress> getInput = email.getCcAddresses();
			for(InternetAddress x : getInput) {
				retrievedAddress = x.toString();
				
			}
	
			assertEquals(retrievedAddress,address);
			
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	//test empty email
	@Test 
	public void addCcTestwithEmptyEmail()  {
		
		String empty[] = {};

				try {
					email.addCc(empty);
				} catch (EmailException e) {
					assertEquals("Address List provided was invalid",e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
	}
	
	//test multiple email
	@Test
	public void addCcTestWithMultipleEmails() {
		
		String firstEmail = "firdaus@gmail.com";
		String secondEmail = "max@yahoo.com";
		String thirdEmail = "alex@umich.edu";
		String fourthEmail = "jay@hotmail";
		
		List<String>testVar = new ArrayList<String>();
		testVar.add(firstEmail);
		testVar.add(secondEmail);
		testVar.add(thirdEmail);
		testVar.add(fourthEmail);
	
		List<String> hostName = new ArrayList<String>();
		
		try {
			email.addCc(firstEmail, secondEmail, thirdEmail, fourthEmail);
			List <InternetAddress> emailList = email.getCcAddresses();

		   for(InternetAddress x: emailList) {
			   hostName.add(x.getAddress());   
		   }
			assertEquals(testVar, hostName);
			
		} catch (EmailException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

	//test header without any exception
	@Test
	public void addHeaderTest() {
		String header = "CIS 376 Assignment 3";
		email.addHeader("1", header);
		Map<String, String> getHeader = email.headers;
		assertEquals(header, getHeader.get("1").toString());
	}
	
	//test null header
	@Test(expected = IllegalArgumentException.class)
	public void addHeaderTestWithNullName() {
		
		
		email.addHeader(null, "1");
		
	
	}
	
	//test empty key 
	@Test(expected = IllegalArgumentException.class)
	public void addHeaderTestWithEmptyValue() {
		
		String value = "";
		email.addHeader("Hii", value);
		
	}
	
	//test without personal name
	@Test
	public void addReplyToTestWithoutName()  {
		
		try {
			List<InternetAddress> emailInfo = new ArrayList<InternetAddress>();
			
			email.addReplyTo("firdausfadlizam@gmail.com");
			emailInfo = email.getReplyToAddresses();
			
			String emailAddressTest = "";
			
			for(InternetAddress x : emailInfo) {

				emailAddressTest = x.getAddress();
			}
			
			assertEquals(emailAddressTest, "firdausfadlizam@gmail.com");
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//test with personal name
	@Test
	public void addReplyToTestWithName()  {
		
		try {
			List<InternetAddress> emailInfo = new ArrayList<InternetAddress>();
			email.addReplyTo("firdausfadlizam@gmail.com", "Firdaus");
			emailInfo = email.getReplyToAddresses();
			String getEmail = "";
			String getName = "";
			for(InternetAddress x : emailInfo) {
				getEmail = x.getAddress();
				getName = x.getPersonal();
			}
			
			assertEquals(getEmail,"firdausfadlizam@gmail.com");
			assertEquals(getName, "Firdaus");
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	//test executing if statements without email body if-statement
	//assert to ensure we receive a mime message that is not null

	@Test 
	public void buildMimeMessageTestWithoutEmailBody() {
		
		email.message = null; 
		try {
			String content = new String();
			content = "Hello";
			Session session = Session.getInstance(new Properties());
			email.setMailSession(session);
			email.setSubject("Greeting");
			email.setContent(content,"text/plain");
			email.setFrom("firdausfadlizam@gmail.com");
			
			List<InternetAddress> addressList = new ArrayList<InternetAddress>();
			InternetAddress address = new InternetAddress();
			address.setAddress("Alice@gmail.com");
			addressList.add(address);
			email.setTo(addressList);
			
			List<InternetAddress> ccAddressList = new ArrayList<InternetAddress>();
			InternetAddress ccAddress = new InternetAddress();
			ccAddress.setAddress("Bob@gmail.com");
			ccAddressList.add(ccAddress);
			email.setCc(ccAddressList);
			
			List<InternetAddress> bccAddressList = new ArrayList<InternetAddress>();
			InternetAddress bccAddress = new InternetAddress();
			bccAddress.setAddress("Charles@gmail.com");
			bccAddressList.add(bccAddress);
			email.setBcc(bccAddressList);
			
			List<InternetAddress> replyAddressList = new ArrayList<InternetAddress>();
			InternetAddress replyAddress = new InternetAddress();
			replyAddress.setAddress("David@gmail.com");
			replyAddressList.add(replyAddress);
			email.setReplyTo(replyAddressList);

			String header = "CIS 376 Assignment 3";
			email.addHeader("1", header);
		
			
			email.buildMimeMessage();
			
			assertNotNull(email.getMimeMessage());			
			
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//test executing if statements including email body if-statement
	//assert to ensure we receive a mime message that is not null
	@Test
	public void buildMimeMessageTestWithEmailBody() {
		
		try {
			
			double content = 123;
			Session session = Session.getInstance(new Properties());
			email.setMailSession(session);
			email.setSubject("CIS 376");
			email.setCharset("utf-8");
			email.setContent(content,"text/plain");
			email.emailBody = new MimeMultipart();
			email.setFrom("firdausfadlizam@gmail.com");
			List<InternetAddress> addressList = new ArrayList<InternetAddress>();
			InternetAddress address = new InternetAddress();
			address.setAddress("Alice@gmail.com");
			addressList.add(address);
			email.setTo(addressList);

			email.buildMimeMessage();
			assertNotNull(email.getMimeMessage());

		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//test null content 
	//assert to ensure we receive the correct exception message
	@Test
	public void buildMimeMessageTestWithNullContent() {
		
		try {
			Session session = Session.getInstance(new Properties());
			email.setMailSession(session);
			email.setSubject("CIS 376");
			email.setCharset("utf-8");
			email.emailBody = new MimeMultipart();
			
			email.buildMimeMessage();
		} catch (EmailException e) {
			  assertEquals(e.getMessage(),"From address required");

			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	//test without null content type
	//assert to ensure that we receive the correct email exception message.
	@Test
	public void buildMimeMessageTestWithoutNullContentType() {
		
		try{
			
		Session session = Session.getInstance(new Properties());
		email.setMailSession(session);
		email.setSubject("CIS 376");
		email.setCharset("utf-8");
		email.contentType = "1";
		email.emailBody = new MimeMultipart();
		email.setFrom("firdausfadlizam@gmail.com");
		email.buildMimeMessage();
		
		} catch(EmailException e) {
			// TODO Auto-generated catch block
		  assertEquals(e.getMessage(),"At least one receiver address required");
		  e.printStackTrace();
		 
		}
	}
	
	//test without null message
	@Test(expected = IllegalStateException.class)
	public void buildMimeMessageTestWithoutNullMessage(){
		
		try {
			
			Session session = Session.getInstance(new Properties());
			email.message = new MimeMessage(session);
			email.buildMimeMessage();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//test with executing set text if-statement
	@Test
	public void buildMimeMessageWithSetText() {
		
		try{
			
			Session session = Session.getInstance(new Properties());
			email.setMailSession(session);
			email.setSubject("CIS 376");
			email.setCharset("utf-8");
			email.setFrom("firdausfadlizam@gmail.com");
			
			List<InternetAddress> addressList = new ArrayList<InternetAddress>();
			InternetAddress address = new InternetAddress();
			address.setAddress("Alice@gmail.com");
			addressList.add(address);
			email.setTo(addressList);
			email.setPopBeforeSmtp(true,null,null,null);
			email.buildMimeMessage();
			
			} catch(EmailException e) {
				
				// TODO Auto-generated catch block
				 e.printStackTrace();
			}
	}
	
	//test with a session
	@Test
	public void getHostNameTest() {
		
		Properties property = new Properties();
		property.setProperty(EmailConstants.MAIL_HOST,"Firdaus");
		Session session = Session.getInstance(property);
		email.setMailSession(session);
		String hostNameTest = email.getHostName();
		assertEquals("Firdaus", hostNameTest);
	}
	
	//test without a session
	@Test
	public void getHostNameTestWithoutSession() {
		
		email.setHostName("Firdaus");
		assertEquals("Firdaus",email.getHostName());
	
	}
	
	//test without a session and hostname
	@Test
	public void getHostNameTestWithoutSessionAndHostName() {
		
		String hostName = email.getHostName();
		assertNull(hostName);
	}
	
	//test with null hostname
	//assert to ensure that we receive the correct message
	@Test
	public void getMailSessionTest() {
		
		
		try {
			email.setHostName(null);
			Session session = email.getMailSession();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			assertEquals(e.getMessage(),"Cannot find valid hostname for mail session");
			e.printStackTrace();
		}
	}
	
	//test with hostname
	@Test
	public void getMailSessionTestWithHostName() {
		
		//create a property variable and assign them with the same values as what the function does.
		Properties property = new Properties(System.getProperties());
		property.setProperty(EmailConstants.MAIL_TRANSPORT_PROTOCOL, EmailConstants.SMTP);
		property.setProperty("mail.smtp.port", "25");
        property.setProperty("mail.smtp.host", "Firdaus");
        property.setProperty("mail.debug", String.valueOf(false));
		property.setProperty(EmailConstants.MAIL_TRANSPORT_STARTTLS_ENABLE, "false");
		property.setProperty(EmailConstants.MAIL_TRANSPORT_STARTTLS_REQUIRED, "false");
		property.setProperty(EmailConstants.MAIL_SMTP_SEND_PARTIAL, "false");
		property.setProperty(EmailConstants.MAIL_SMTPS_SEND_PARTIAL, "false");
		property.setProperty(EmailConstants.MAIL_SMTP_AUTH, "true");
		property.setProperty(EmailConstants.MAIL_PORT, "465");
        property.setProperty("mail.smtp.socketFactory.port", "465");
        property.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        property.setProperty("mail.smtp.socketFactory.fallback", "false");
        property.setProperty(EmailConstants.MAIL_SMTP_SSL_CHECKSERVERIDENTITY, "true");
        property.setProperty("mail.smtp.from", "firdausfadlizam@gmail.com");
        property.setProperty("mail.smtp.timeout", Integer.toString(EmailConstants.SOCKET_TIMEOUT_MS));
        property.setProperty( "mail.smtp.connectiontimeout", Integer.toString(EmailConstants.SOCKET_TIMEOUT_MS));

        
		email.setHostName("Firdaus");
		email.setAuthentication("FirdausFadlizam", "12345");
		email.setSSLOnConnect(true);
		email.setSSLCheckServerIdentity(true);
		email.setBounceAddress("firdausfadlizam@gmail.com");
		
		try {
			//check if the property of the session is equal
			Session session = email.getMailSession();
			assertEquals(session.getProperties(), property);
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	//test with isTLSEnabled and isTLSRequired equal to true. 
	@Test
	public void getMailSessionTestWithTrueTLSEnabledAndRequired() {
		
		Properties property = new Properties(System.getProperties());
		property.setProperty(EmailConstants.MAIL_TRANSPORT_PROTOCOL, EmailConstants.SMTP);
		property.setProperty("mail.smtp.port", "25");
        property.setProperty("mail.smtp.host", "Firdaus");
        property.setProperty("mail.debug", String.valueOf(false));
		property.setProperty(EmailConstants.MAIL_TRANSPORT_STARTTLS_ENABLE, "true");
		property.setProperty(EmailConstants.MAIL_TRANSPORT_STARTTLS_REQUIRED, "true");
		property.setProperty(EmailConstants.MAIL_SMTP_SEND_PARTIAL, "true");
		property.setProperty(EmailConstants.MAIL_SMTPS_SEND_PARTIAL, "true");
        property.setProperty("mail.smtp.timeout", Integer.toString(EmailConstants.SOCKET_TIMEOUT_MS));
        property.setProperty( "mail.smtp.connectiontimeout", Integer.toString(EmailConstants.SOCKET_TIMEOUT_MS));

        email.setStartTLSEnabled(true);
        email.setStartTLSRequired(true);
        email.setSendPartial(true);
		email.setHostName("Firdaus");
		email.setSSLOnConnect(false);
		email.setSSLCheckServerIdentity(false);
		
		try {
			Session session = email.getMailSession();
			assertEquals(session.getProperties(), property);
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	//test with null date. 
	@Test
	public void getSentDateTestWithNullDate() {
		
		Date date = email.getSentDate();
		assertEquals(new Date(), date);
	
	}
	
	//test without null date
	@Test
	public void getSentDateTestWithoutNullDate() {
		
		Date date = new Date();
		email.setSentDate(date);
		Date receivedDate = email.getSentDate();
		
		assertEquals(date, receivedDate);
	}
	
	//test socket connection timeout that has been initialized with 20
	@Test
	public void getSocketConnectionTimeoutTest() {
		
		email.setSocketConnectionTimeout(20);
		int timeout = email.getSocketConnectionTimeout();
		assertEquals(20, timeout);

	}
	
	//test the sender's email
	@Test 
	public void setFromTest() {
		try {
			email.setFrom("firdausfadlizam@gmail.com");
			InternetAddress emailInfo = email.getFromAddress();
			assertEquals("firdausfadlizam@gmail.com", emailInfo.getAddress());
			
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//no teardown since there is no memory deallocation required
	@After
	public void tearDown() {
	}

}
