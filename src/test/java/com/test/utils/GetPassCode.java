package com.test.utils;





import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeMultipart;

public class GetPassCode {

	static List<String> messageList = new ArrayList<String>();
	static String messageContent=null;
	static String host = "pop.gmail.com";// change accordingly
	static String mailStoreType = "pop3";
	static String username = "usaidtest3@gmail.com";// change accordingly
	static String password = "Azraq1984@#!";// change accordingly
	static String passCode;
	
//	static String username = "fatimatest4@gmail.com";// change accordingly
//	static String password = "@214395974As";// change accordingly


public static void main(String[] args) {
	check();
}
	public static String check() {
		try {
			
			
			// create properties field
			Properties properties = new Properties();

			properties.put("mail.pop3.host", host);
			properties.put("mail.pop3.port", "995");
			properties.put("mail.pop3.starttls.enable", "true");
			Session emailSession = Session.getDefaultInstance(properties);

			// create the POP3 store object and connect with the pop server
			Store store = emailSession.getStore("pop3s");

			store.connect(host, username, password);

			// create the folder object and open it
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);

			// retrieve the messages from the folder in an array and print it
			Message[] messages = emailFolder.getMessages();
			System.out.println("messages.length---" + messages.length);

			
			
			 for (int i = messages.length-1, n = messages.length-1; i <= n; i++) {
		         Message message = messages[i];
		         //System.out.println("---------------------------------");
		         //System.out.println("Email Number " + (i + 1));
		         //System.out.println("Subject: " + message.getSubject());
		         //System.out.println("From: " + message.getFrom()[0]);
		         System.out.println("Text: " + message.getContent().toString());
		         messageContent=getTextFromMessage(message);
					//System.out.println(message.getReceivedDate());
				//	messageList.add(messageContent);
					System.out.println("The returned string is "+messageContent);
					System.out.println("The passcode is"+ messageContent.substring(0, 6));
					System.out.println("The passcode is"+ messageContent.split(" ")[1]);
					 passCode=messageContent.split(" ")[1];

					if (messageContent.contains("Use this to continue signing in to your account")) {
				//	System.out.println(messageContent);
					return passCode;

					}else {
						System.out.println("message other than passocde");
					}

		      }

			
			//System.out.println(messageList);

			

			// close the store and folder objects
			emailFolder.close(false);
			store.close();

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return passCode;
	}

	private static String getTextFromMessage(Message message) throws IOException, MessagingException {
		String result = "";
		if (message.isMimeType("text/plain")) {
			result = message.getContent().toString();
		} else if (message.isMimeType("multipart/*")) {
			MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
			result = getTextFromMimeMultipart(mimeMultipart);
		}
		// System.out.println("getTextFromMessage"+result);
		return result;
	}

	private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws IOException, MessagingException {

		int count = mimeMultipart.getCount();
		if (count == 0)
			throw new MessagingException("Multipart with no body parts not supported.");
		boolean multipartAlt = new ContentType(mimeMultipart.getContentType()).match("multipart/alternative");
		if (multipartAlt)
			// alternatives appear in an order of increasing
			// faithfulness to the original content. Customize as req'd.
			return getTextFromBodyPart(mimeMultipart.getBodyPart(count - 1));
		String result = "";
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			result += getTextFromBodyPart(bodyPart);
		}
		// System.out.println("getTextFromMimeMultipart"+result);
		return result;
	}

	private static String getTextFromBodyPart(BodyPart bodyPart) throws IOException, MessagingException {

		String result = "";
		if (bodyPart.isMimeType("text/plain")) {
			result = (String) bodyPart.getContent();
		} else if (bodyPart.isMimeType("text/html")) {
			String html = (String) bodyPart.getContent();
			result = org.jsoup.Jsoup.parse(html).text();
		} else if (bodyPart.getContent() instanceof MimeMultipart) {
			result = getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
		}
		return result;
	}

	
}