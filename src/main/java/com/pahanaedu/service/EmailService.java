package com.pahanaedu.service;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.mail.util.ByteArrayDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Properties;
import jakarta.activation.*;
import java.io.ByteArrayInputStream;

public class EmailService {

	private static Properties emailConfig;

	// Load the properties once
	static {
		emailConfig = new Properties();
		try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("email.properties")) {
			if (in == null) {
				throw new IllegalStateException("email.properties not found on classpath");
			}
			emailConfig.load(in);
		} catch (IOException ex) {
			throw new RuntimeException("Failed to load email.properties", ex);
		}
	}

	public boolean sendEmailWithInlineImage(String to, String subject, String date, String time, String imageBase64) {
	    try {
	        Session session = createSession();

	        MimeMessage message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(emailConfig.getProperty("mail.username")));
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
	        message.setSubject(subject);

	        // multipart/alternative to hold both plain text and html parts
	        MimeMultipart alternativeMultipart = new MimeMultipart("alternative");

	        // Plain text part
	        MimeBodyPart textPart = new MimeBodyPart();
	        String textContent = "Dear customer,\nThis is your invoice on " + date + " at " + time + ".";
	        textPart.setText(textContent, "utf-8");
	        alternativeMultipart.addBodyPart(textPart);

	        // multipart/related for html + inline image
	        MimeMultipart relatedMultipart = new MimeMultipart("related");

	        // HTML part referencing the inline image
	        MimeBodyPart htmlPart = new MimeBodyPart();
	        String cid = "billImageCID";
	        String htmlWithImage =
	            "<p>Dear customer,<br>this is your invoice on " + date + " at " + time + ".</p>" +
	            "<br>" +
	            "<div style=\"text-align:center;\">" +
	            "<img src=\"cid:" + cid + "\" alt=\"Bill Preview\" " +
	            "style=\"max-width:500px; width:100%; height:auto; border: 2px solid #808080; border-radius: 4px;\" />" +
	            "</div>";
	        htmlPart.setContent(htmlWithImage, "text/html; charset=utf-8");
	        relatedMultipart.addBodyPart(htmlPart);

	        // Image part
	        if (imageBase64 != null && !imageBase64.isEmpty()) {
	            if (imageBase64.contains(",")) {
					imageBase64 = imageBase64.split(",")[1]; // remove prefix if any
	            }

	            byte[] imageBytes = Base64.getDecoder().decode(imageBase64);
	            MimeBodyPart imagePart = new MimeBodyPart();

	            DataSource ds = new ByteArrayDataSource(new ByteArrayInputStream(imageBytes), "image/png");
	            imagePart.setDataHandler(new DataHandler(ds));
	            imagePart.setHeader("Content-ID", "<" + cid + ">");
	            imagePart.setDisposition(MimeBodyPart.INLINE);

	            relatedMultipart.addBodyPart(imagePart);
	        }

	        // Wrap the related multipart in a body part, then add it to alternative multipart
	        MimeBodyPart relatedBodyPart = new MimeBodyPart();
	        relatedBodyPart.setContent(relatedMultipart);
	        alternativeMultipart.addBodyPart(relatedBodyPart);

	        // Set the multipart/alternative as the message content
	        message.setContent(alternativeMultipart);

	        Transport.send(message);
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	private Session createSession() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", emailConfig.getProperty("mail.smtp.auth", "true"));
		props.put("mail.smtp.starttls.enable", emailConfig.getProperty("mail.smtp.starttls.enable", "true"));
		props.put("mail.smtp.host", emailConfig.getProperty("mail.smtp.host"));
		props.put("mail.smtp.port", emailConfig.getProperty("mail.smtp.port"));

		return Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailConfig.getProperty("mail.username"),
						emailConfig.getProperty("mail.password"));
			}
		});
	}

	// Utility method to validate email format
	public boolean isValidEmail(String email) {
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
			return true;
		} catch (AddressException e) {
			return false;
		}
	}
}
