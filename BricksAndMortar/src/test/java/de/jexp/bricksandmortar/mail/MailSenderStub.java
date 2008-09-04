package de.jexp.bricksandmortar.mail;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mh14 on 06.07.2007 22:41:38
 */
class MailSenderStub implements JavaMailSender {
    public List<SimpleMailMessage> messages = new LinkedList<SimpleMailMessage>();
    private List<MimeMessage> mimeMessages = new LinkedList<MimeMessage>();

    public void send(final SimpleMailMessage simpleMessage) throws MailException {
        sendMailMessage(simpleMessage);
    }

    private void sendMailMessage(final SimpleMailMessage message) {
        messages.add(message);
    }

    public void send(final SimpleMailMessage[] simpleMessages) throws MailException {
        for (final SimpleMailMessage simpleMessage : simpleMessages) {
            send(simpleMessage);
        }
    }

    public List<SimpleMailMessage> getMessages() {
        return messages;
    }

    public List<MimeMessage> getMimeMessages() {
        return mimeMessages;
    }

    JavaMailSender javaMailSender;

    public JavaMailSender getJavaMailSender() {
        if (javaMailSender == null) {
            final JavaMailSenderImpl jms = new JavaMailSenderImpl();
            // jms.setHost("mxlb.ispgateway.de");
            javaMailSender = jms;
        }
        return javaMailSender;
    }

    public MimeMessage createMimeMessage() {
        return getJavaMailSender().createMimeMessage();
    }

    public MimeMessage createMimeMessage(final InputStream contentStream) throws MailException {
        return getJavaMailSender().createMimeMessage(contentStream);
    }

    public void send(final MimeMessage mimeMessage) throws MailException {
        mimeMessages.add(mimeMessage);
    }

    public void send(final MimeMessage[] mimeMessages) throws MailException {
        for (final MimeMessage mimeMessage : mimeMessages) {
            send(mimeMessage);
        }
    }

    public void send(final MimeMessagePreparator mimeMessagePreparator) throws MailException {
    }

    public void send(final MimeMessagePreparator[] mimeMessagePreparators) throws MailException {
    }
}
