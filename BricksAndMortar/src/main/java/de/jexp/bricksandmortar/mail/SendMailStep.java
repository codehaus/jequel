package de.jexp.bricksandmortar.mail;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import java.util.Collection;

import de.jexp.bricksandmortar.NamedWorkflowStep;
import de.jexp.bricksandmortar.WorkflowContext;
import de.jexp.bricksandmortar.results.FileStepResult;
import de.jexp.bricksandmortar.results.TextStepResult;

/**
 * Created by mh14 on 06.07.2007 22:19:03
 */
public class SendMailStep extends NamedWorkflowStep {
    private MailSender mailSender;
    private SimpleMailMessage mailTemplate;
    private boolean sendMetaData;
    public SendMailStep() {
    }

    public SendMailStep(final MailSender mailSender, final SimpleMailMessage mailTemplate) {
        this.mailSender = mailSender;
        this.mailTemplate = mailTemplate;
    }

    public void runStep(final WorkflowContext workflowContext) {
        final Collection<FileStepResult> filesToSend = filterParams(workflowContext, getParamNames(), FileStepResult.class).values();
        final Collection<TextStepResult> textsToSend = filterParams(workflowContext, getParamNames(), TextStepResult.class).values();
        if (filesToSend.isEmpty()) {
            sendTextMessage(textsToSend);
        } else {
            sendMimeMessage(textsToSend, filesToSend);
        }
    }

    private void sendMimeMessage(final Collection<TextStepResult> textsToSend, final Collection<FileStepResult> filesToSend) {
        final JavaMailSender javaMailSender = (JavaMailSender) mailSender;
        try {
            final MimeMessageHelper mineMessage = createMimeMessageFromTemplate(javaMailSender);
            String messageText = createMessageText(textsToSend);
            messageText += addFilesToMimeMessage(mineMessage, filesToSend);
            mineMessage.setText(messageText);
            javaMailSender.send(mineMessage.getMimeMessage());
        } catch (MessagingException e) {
            throw new MailPreparationException("error creating mime message", e);
        }
    }

    protected String createMessageText(final Collection<TextStepResult> textsToSend) {
        String result = "";
        for (final TextStepResult textStepResult : textsToSend) {
            if (isSendMetaData())
                result += String.format("Result: %s\n", textStepResult.getName());
            result += textStepResult.getResult() + "\n";
        }
        return result;
    }

    protected String addFilesToMimeMessage(final MimeMessageHelper mineMessage, final Collection<FileStepResult> filesToSend) throws MessagingException {
        String messageText = "";
        for (final FileStepResult fileStepResult : filesToSend) {
            if (isSendMetaData())
                messageText += "Attached File " + fileStepResult.getFileName() + "\n";
            addFileToMimeMessage(mineMessage, fileStepResult);
        }
        return messageText;
    }

    protected void addFileToMimeMessage(final MimeMessageHelper mineMessage, final FileStepResult fileStepResult) throws MessagingException {
        final ByteArrayResource fileResource = new ByteArrayResource(fileStepResult.getResult());
        mineMessage.addAttachment(fileStepResult.getFileName(), fileResource); //, fileStepResult.getContentType());
    }

    protected MimeMessageHelper createMimeMessageFromTemplate(final JavaMailSender javaMailSender) throws MessagingException {
        final MimeMessageHelper helper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
        final SimpleMailMessage template = getMailTemplate();
        helper.setTo(template.getTo());
        helper.setFrom(template.getFrom());
        helper.setSubject(template.getSubject());
        return helper;
    }

    public void sendTextMessage(final Collection<TextStepResult> textsToSend) {
        final SimpleMailMessage msg = new SimpleMailMessage(mailTemplate);
        msg.setText(createMessageText(textsToSend));
        mailSender.send(msg);
    }

    public void setMailSender(final MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public SimpleMailMessage getMailTemplate() {
        return mailTemplate;
    }

    public void setMailTemplate(final SimpleMailMessage mailTemplate) {
        this.mailTemplate = mailTemplate;
    }

    public boolean isSendMetaData() {
        return sendMetaData;
    }

    public void setSendMetaData(final boolean sendMetaData) {
        this.sendMetaData = sendMetaData;
    }
}
