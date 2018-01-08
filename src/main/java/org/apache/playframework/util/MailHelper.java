package org.apache.playframework.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * 发送邮件的工具类
 * 
 * @author zxj
 * 
 */
public class MailHelper {

	/**
	 * 发送邮件
	 * 
	 * @param smtpHost
	 * @param smtpPort
	 * @param smtpAuth
	 * @param smtpUsername
	 * @param smtpPassword
	 * @param receivers
	 *            收件人
	 * @param subject
	 *            主题
	 * @param body
	 * @throws MessagingException
	 */
	public static void send(String smtpHost, String smtpPort, String smtpAuth, String smtpUsername, String smtpPassword, String[] receivers, String subject, String body) throws MessagingException {
		send(smtpHost, smtpPort, smtpAuth, smtpUsername, smtpPassword, receivers, null, subject, body, null);
	}

	/**
	 * 发送邮件 有抄送人 没有附件
	 * 
	 * @param smtpHost
	 * @param smtpPort
	 * @param smtpAuth
	 * @param smtpUsername
	 * @param smtpPassword
	 * @param receivers
	 * @param cc
	 *            抄送
	 * @param subject
	 * @param body
	 * @throws MessagingException
	 */
	public static void send(String smtpHost, String smtpPort, String smtpAuth, String smtpUsername, String smtpPassword, String[] receivers, String[] cc, String subject, String body)
			throws MessagingException {
		send(smtpHost, smtpPort, smtpAuth, smtpUsername, smtpPassword, receivers, cc, subject, body, null);
	}

	/**
	 * 发送邮件主方法 有抄送人 有附件
	 * 
	 * @param smtpHost
	 * @param smtpPort
	 * @param smtpAuth
	 * @param smtpUsername
	 * @param smtpPassword
	 * @param receivers
	 * @param cc
	 * @param subject
	 * @param body
	 * @param attachFiles
	 * @throws MessagingException
	 */
	public static void send(String smtpHost, String smtpPort, String smtpAuth, final String smtpUsername, final String smtpPassword, String[] receivers, String[] cc, String subject, String body,
			String[] attachFiles) throws MessagingException {
		Authenticator auth = null;
		if ("true".equals(smtpAuth)) {
			auth = new Authenticator() {
				@Override
                protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(smtpUsername, smtpPassword);
				}
			};
		}

		Properties prop = new Properties();
		prop.setProperty("mail.smtp.host", smtpHost);
		prop.setProperty("mail.smtp.port", smtpPort);
		prop.setProperty("mail.smtp.auth", smtpAuth);
		prop.setProperty("smtpUsername", smtpUsername);
		prop.setProperty("smtpPassword", smtpPassword);

		Session sendMailSession = Session.getInstance(prop, auth);
		sendMailSession.setDebug(true);
		Message msg = new MimeMessage(sendMailSession);

		msg.setHeader("Content-Transfer-Encoding", "utf-8");
		// 创建邮件发送者地址
		Address from = new InternetAddress(prop.getProperty("smtpUsername"));
		msg.setFrom(from);
		// 创建邮件的接收者地址，并设置到邮件消息中
		if (receivers != null) {
			if (receivers.length > 0) {
				Address[] to = new Address[receivers.length];
				for (int i = 0; i < receivers.length; i++) {
					to[i] = new InternetAddress(receivers[i]);
				}
				msg.setRecipients(Message.RecipientType.TO, to);
			}
		}
		if (cc != null) {
			if (cc.length > 0) {
				Address[] adds = new Address[cc.length];
				for (int i = 0; i < cc.length; i++) {
					adds[i] = new InternetAddress(cc[i]);
				}
				msg.setRecipients(Message.RecipientType.CC, adds);
			}
		}
		// 设置邮件消息的主题
		msg.setSubject(subject);
		// 设置邮件消息发送的时间
		msg.setSentDate(new Date());
		// 设置邮件消息的主要内容
		String mailContent = body;
		// msg.setText(mailContent);
		// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
		Multipart mainPart = new MimeMultipart();
		// 创建一个包含HTML内容的MimeBodyPart
		BodyPart html = new MimeBodyPart();
		// 设置HTML内容
		html.setContent(mailContent, "text/html; charset=utf-8");
		mainPart.addBodyPart(html);
		// 附件
		if (attachFiles != null && attachFiles.length > 0) {
			for (String filePath : attachFiles) {
				try {
					BodyPart fujian = new MimeBodyPart();
					File file = new File(filePath);
					FileDataSource fds = new FileDataSource(file);
					fujian.setDataHandler(new DataHandler(fds));
					// fujian.setFileName(fds.getName());
					fujian.setFileName(MimeUtility.encodeText(fds.getName()));
					mainPart.addBodyPart(fujian);
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			}
		}
		// 附件end
		// 将MiniMultipart对象设置为邮件内容
		msg.setContent(mainPart);
		// 发送邮件
		Transport.send(msg);
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		try {
			send("smtp.anjuke.com", "25", "true", "lcjiao@anjuke.com", "jcl7744781", new String[] { "xiaojiezhu@anjuke.com" }, "mail_test", "ceshi");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
