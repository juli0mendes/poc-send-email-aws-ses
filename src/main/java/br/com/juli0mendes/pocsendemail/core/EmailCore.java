package br.com.juli0mendes.pocsendemail.core;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailCore {

	private static final Logger log = LoggerFactory.getLogger(EmailCore.class);

	@Value("${spring.properties.mail.transport.protocol}")
	private String protocol;

	@Value("${spring.properties.mail.smtp.port}")
	private String port;

	@Value("${spring.properties.mail.smtp.starttls.enable}")
	private String isStarttls;

	@Value("${spring.properties.mail.smtp.auth}")
	private String isAuth;

	@Value("${osklen.email.from}")
	private String from;

	@Value("${osklen.email.fromname}")
	private String fromName;

	@Value("${osklen.email.to}")
	private String to;

	@Value("${osklen.email.subject}")
	private String subject;

	@Value("${osklen.email.host}")
	private String host;

	@Value("${osklen.email.smtpusername}")
	private String smtpUsername;

	@Value("${osklen.email.smtpassword}")
	private String smtpPassword;

	public void sendEmail() throws Exception {

		Properties props = this.configureProperties();

		Session session = Session.getDefaultInstance(props);

		MimeMessage msg = this.configureMessage(session);

		Transport transport = session.getTransport();

		try {
			transport.connect(host, smtpUsername, smtpPassword);
			transport.sendMessage(msg, msg.getAllRecipients());
		} catch (Exception e) {
			log.error("The email was not sent.");
			log.error("Error message: " + e.getMessage());
		} finally {
			transport.close();
		}
	}

	private Properties configureProperties() {
		Properties props = System.getProperties();
		props.put("mail.transport.protocol", protocol);
		props.put("mail.smtp.port", 587);
		props.put("mail.smtp.starttls.enable", isStarttls);
		props.put("mail.smtp.auth", isAuth);

		return props;
	}

	private MimeMessage configureMessage(Session session) throws UnsupportedEncodingException, MessagingException {
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(from, fromName));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		msg.setSubject(subject);
		msg.setContent("Teste message.<br> Tks", "text/html");

		return msg;
	}
}
