package de.jexp.bricksandmortar.mail;

import de.jexp.bricksandmortar.WorkflowContext;
import de.jexp.bricksandmortar.WorkflowStepTest;
import de.jexp.bricksandmortar.results.FileStepResult;
import de.jexp.bricksandmortar.results.TextStepResult;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class SendMailStepTest extends WorkflowStepTest<SendMailStep> {
    private static final String TO_EMAIL = "mesirii@nicht-suechtig.de";
    private static final String FROM_EMAIL = "mesirii@nicht-suechtig.de";
    private static final String SUBJECT = "Testmail";
    private static final String TEXT = "Text Body Text";
    private static final String TEXT_STEP = "textStep";
    private static final String EXCEL_STEP = "excelStep";
    private static final byte[] TEST_BYTES = new byte[]{0, 1, 2};
    private static final String FILE_NAME = EXCEL_STEP + ".xls";

    public void testSendMailStep() {
        final MailSenderStub mailSenderStub = new MailSenderStub();
        step.setMailSender(mailSenderStub);
        step.setMailTemplate(createMailTemplate());
        step.setParamName(TEXT_STEP);
        final WorkflowContext workflowContext = new WorkflowContext();
        workflowContext.addResult(new TextStepResult(TEXT_STEP, TEXT));
        step.runStep(workflowContext);
        checkSentMail(mailSenderStub);

    }

    public void testSendMimeMailStep() throws MessagingException, UnsupportedFlavorException, IOException {
        final MailSenderStub mailSenderStub = new MailSenderStub();
        step.setMailSender(mailSenderStub);
        step.setMailTemplate(createMailTemplate());
        step.setParamName(EXCEL_STEP);
        step.setSendMetaData(true);
        final WorkflowContext workflowContext = new WorkflowContext();
        workflowContext.addResult(new FileStepResult(EXCEL_STEP, TEST_BYTES, "xls"));
        step.runStep(workflowContext);
        checkSentMimeMail(mailSenderStub, "Attached File " + FILE_NAME + "\n");
    }

    public void testSendMimeTextMailStep() throws MessagingException, UnsupportedFlavorException, IOException {
        final MailSenderStub mailSenderStub = new MailSenderStub();
        step.setMailSender(mailSenderStub);
        step.setMailTemplate(createMailTemplate());
        step.setParamNames(EXCEL_STEP, TEXT_STEP);
        final WorkflowContext workflowContext = new WorkflowContext();
        workflowContext.addResult(new TextStepResult(TEXT_STEP, TEXT));
        workflowContext.addResult(new FileStepResult(EXCEL_STEP, TEST_BYTES, "xls"));
        step.runStep(workflowContext);
        checkSentMimeMail(mailSenderStub, TEXT + "\n");
    }

    /*
        public void testSendSendRealdMail() throws MessagingException, UnsupportedFlavorException, IOException {
            final JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
            javaMailSender.setHost("server.mesirii.de");
            step.setMailSender(javaMailSender);
            step.setMailTemplate(createMailTemplate());
            step.setParamName(EXCEL_STEP);
            final WorkflowContext workflowContext = new WorkflowContext();
            workflowContext.addResult(EXCEL_STEP, new FileStepResult(EXCEL_STEP, TEST_BYTES, "xls"));
            step.runStep(workflowContext);
        }
    */
    private void checkSentMail(final MailSenderStub mailSenderStub) {
        final List<SimpleMailMessage> messageList = mailSenderStub.getMessages();
        assertEquals("mail count", 1, messageList.size());
        final SimpleMailMessage mailMessage = messageList.get(0);
        assertEquals("mail from", FROM_EMAIL, mailMessage.getFrom());
        final String[] toAddresses = mailMessage.getTo();
        assertEquals("one receiver", 1, toAddresses.length);
        assertEquals("mail to", TO_EMAIL, toAddresses[0]);
        assertEquals("mail subject", SUBJECT, mailMessage.getSubject());
        assertEquals("mail body", TEXT + "\n", mailMessage.getText());
    }

    private void checkSentMimeMail(final MailSenderStub mailSenderStub, final String expectedMessageText) throws MessagingException, UnsupportedFlavorException, IOException {
        final List<MimeMessage> messageList = mailSenderStub.getMimeMessages();
        assertEquals("mail count", 1, messageList.size());
        final MimeMessage mailMessage = messageList.get(0);
        final InternetAddress[] fromAddresses = (InternetAddress[]) mailMessage.getFrom();
        assertEquals("one sender", 1, fromAddresses.length);
        assertEquals("mail from", FROM_EMAIL, fromAddresses[0].getAddress());
        final InternetAddress[] toAddresses = (InternetAddress[]) mailMessage.getRecipients(MimeMessage.RecipientType.TO);
        assertEquals("one receiver", 1, toAddresses.length);
        assertEquals("mail to", TO_EMAIL, toAddresses[0].getAddress());
        assertEquals("mail subject", SUBJECT, mailMessage.getSubject());
        final MimeMultipart rootMultiPart = (MimeMultipart) mailMessage.getContent();
        assertEquals("root multipart size 2", 2, rootMultiPart.getCount());
        final MimeMultipart textMultiPart = (MimeMultipart) rootMultiPart.getBodyPart(0).getContent();
        assertEquals("textmultipart size 1", 1, textMultiPart.getCount());
        assertEquals("mail body", expectedMessageText, textMultiPart.getBodyPart(0).getContent());
        final BodyPart fileBodyPart = rootMultiPart.getBodyPart(1);
        assertEquals("file multipart file name", FILE_NAME, fileBodyPart.getFileName());
        final byte[] bytes = new byte[3];
        assertEquals("bytes read", 3, ((InputStream) fileBodyPart.getContent()).read(bytes));
        assertTrue("byte arrays", Arrays.equals(TEST_BYTES, bytes));
    }

    private SimpleMailMessage createMailTemplate() {
        final SimpleMailMessage mailTemplate = new SimpleMailMessage();
        mailTemplate.setTo(TO_EMAIL);
        mailTemplate.setFrom(FROM_EMAIL);
        mailTemplate.setSubject(SUBJECT);
        return mailTemplate;
    }

    protected void setUp() {
        step = new SendMailStep();
        step.setName("sendMail");
    }

    protected void tearDown() {
    }

}
