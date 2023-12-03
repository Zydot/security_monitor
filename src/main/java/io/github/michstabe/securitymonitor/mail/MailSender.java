package io.github.michstabe.securitymonitor.mail;

import io.github.michstabe.securitymonitor.config.ConfigLoader;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @projectName: SecurityMonitor
 * @package: io.github.michstabe.securitymonitor.mail
 * @className: MailSender
 * @author: Zydot
 * @description: TODO
 * @date: 2023/12/3 21:57
 */
public class MailSender {

    final String SMTP_HOST = ConfigLoader.getConfigYaml().getConfig().getString("mail.smtp"); // SMTP 服务器地址
    final String USERNAME = ConfigLoader.getConfigYaml().getConfig().getString("mail.send"); // 使用的邮箱
    final String PASSWORD = ConfigLoader.getConfigYaml().getConfig().getString("mail.code"); // 授权码或密码
    final String SUBJECT = ConfigLoader.getConfigYaml().getConfig().getString("mail.title");

    public void mailSender(String content) {
        CompletableFuture.runAsync(() -> {
            Properties properties = new Properties();
            properties.setProperty("mail.transport.protocol", "smtp");
            properties.setProperty("mail.smtp.host", SMTP_HOST);
            properties.setProperty("mail.smtp.auth", "true");

            Session session = Session.getInstance(properties);
            try {
                MimeMessage mail = mailBuilder(session, content);
                Transport transport = session.getTransport();
                transport.connect(USERNAME, PASSWORD);
                transport.sendMessage(mail, mail.getAllRecipients());
                transport.close();
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private MimeMessage mailBuilder(Session session, String content) throws MessagingException {
        MimeMessage msg = new MimeMessage(session);
        final String MAIL_HTML = "<!DOCTYPE html><html>" + content + "</html>";
        msg.setFrom(new InternetAddress(USERNAME));
        final CopyOnWriteArrayList<String> list = ConfigLoader.getRecipientList();
        InternetAddress[] recipientAddress = new InternetAddress[list.size()];
        int count = 0;
        for (String recipient : list) {
            recipientAddress[count] = new InternetAddress(recipient.trim());
            count++;
        }
        msg.setRecipients(MimeMessage.RecipientType.TO, recipientAddress);
        msg.setSubject(SUBJECT, "utf-8");
        msg.setContent(MAIL_HTML, "text/html;charset=utf-8");
        msg.saveChanges();
        return msg;
    }
}
