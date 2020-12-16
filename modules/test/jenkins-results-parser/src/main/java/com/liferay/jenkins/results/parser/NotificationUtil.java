/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class NotificationUtil {

	public static void sendEmail(
		String body, String senderName, String subject,
		String recipientEmailAddress) {

		sendEmail(
			JenkinsResultsParserUtil.combine(
				senderName, "@", JenkinsResultsParserUtil.getHostName(null)),
			senderName, recipientEmailAddress, subject, body);
	}

	public static void sendEmail(
		String senderEmailAddress, String senderName,
		String recipientEmailAddress, String subject, String body) {

		sendEmail(
			senderEmailAddress, senderName, recipientEmailAddress, subject,
			body, null);
	}

	public static void sendEmail(
		String senderEmailAddress, String senderName,
		String recipientEmailAddress, String subject, String body,
		String attachmentFileName) {

		Properties sessionProperties = System.getProperties();

		sessionProperties.put("mail.smtp.auth", "true");
		sessionProperties.put("mail.smtp.port", 587);
		sessionProperties.put("mail.smtp.starttls.enable", "true");
		sessionProperties.put("mail.transport.protocol", "smtp");

		Session session = Session.getDefaultInstance(sessionProperties);

		MimeMessage mimeMessage = new MimeMessage(session);

		try {
			mimeMessage.setFrom(
				new InternetAddress(senderEmailAddress, senderName));
			mimeMessage.setRecipients(
				Message.RecipientType.TO, recipientEmailAddress);
			mimeMessage.setSubject(subject);

			Multipart multipart = new MimeMultipart();

			BodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setContent(body, "text/plain");

			multipart.addBodyPart(messageBodyPart);

			if ((attachmentFileName != null) &&
				!attachmentFileName.equals("")) {

				BodyPart attachmentBodyPart = new MimeBodyPart();

				DataSource source = new FileDataSource(attachmentFileName);

				attachmentBodyPart.setDataHandler(new DataHandler(source));

				File attachmentFile = new File(attachmentFileName);

				attachmentBodyPart.setFileName(attachmentFile.getName());

				multipart.addBodyPart(attachmentBodyPart);
			}

			mimeMessage.setContent(multipart);

			mimeMessage.saveChanges();

			Transport transport = session.getTransport();

			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			transport.connect(
				buildProperties.getProperty("email.smtp.server"),
				buildProperties.getProperty("email.smtp.username"),
				buildProperties.getProperty("email.smtp.password"));

			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());

			System.out.println("Email sent to: " + recipientEmailAddress);

			transport.close();
		}
		catch (IOException | MessagingException exception) {
			System.out.println("Unable to send email.");
			System.out.println(exception.getMessage());

			exception.printStackTrace();
		}
	}

	public static void sendSlackNotification(
		String body, String channelName, String subject) {

		sendSlackNotification(
			body, channelName, ":liferay-ci:", subject, "Liferay CI", false);
	}

	public static void sendSlackNotification(
		String body, String channelName, String subject,
		Boolean sendBodyAsThreadReply) {

		sendSlackNotification(
			body, channelName, ":liferay-ci:", subject, "Liferay CI",
			sendBodyAsThreadReply);
	}

	public static void sendSlackNotification(
		String body, String channelName, String iconEmoji, String subject,
		String username) {

		sendSlackNotification(
			body, channelName, iconEmoji, subject, username, false);
	}

	public static void sendSlackNotification(
		String body, String channelName, String iconEmoji, String subject,
		String username, Boolean sendBodyAsThreadReply) {

		String text = body;

		if (sendBodyAsThreadReply) {
			text = subject;
		}
		else {
			subject = subject.trim();

			if (!subject.isEmpty()) {
				subject = JenkinsResultsParserUtil.combine(
					"*", subject, "*\n\n");

				text = JenkinsResultsParserUtil.combine(
					subject, "> ", body.replaceAll("\n", "\n> "));
			}
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("channel", channelName);
		jsonObject.put("icon_emoji", iconEmoji);
		jsonObject.put("text", text);
		jsonObject.put("username", username);

		try {
			Properties properties = JenkinsResultsParserUtil.getBuildProperties(
				true);

			JSONObject responseJSONObject = new JSONObject(
				JenkinsResultsParserUtil.toString(
					properties.getProperty("slack.webhook.url"),
					jsonObject.toString()));

			if (sendBodyAsThreadReply) {
				jsonObject.put("text", body.replaceAll("\n", "\n> "));
				jsonObject.put("thread_ts", responseJSONObject.get("ts"));

				JenkinsResultsParserUtil.toString(
					properties.getProperty("slack.webhook.url"),
					jsonObject.toString());
			}
		}
		catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	static {
		Thread thread = Thread.currentThread();

		thread.setContextClassLoader(Message.class.getClassLoader());
	}

}