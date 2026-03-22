package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.Multipart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.InternetAddress;
import javax.mail.*;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/email")
public class PdfEmailController {

    @Autowired
    private JavaMailSender emailSender;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmailWithPdf(@RequestParam String to,
                                                   @RequestParam String subject,
                                                   @RequestParam String message,
                                                   @RequestParam String pdfPath) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(message);

            MimeBodyPart pdfPart = new MimeBodyPart();
            File pdfFile = new File(pdfPath);
             
            pdfPart.attachFile(pdfFile);
            pdfPart.setFileName(MimeUtility.encodeText(pdfFile.getName()));
            pdfPart.setHeader("Content-Disposition", "attachment; filename=" + pdfFile.getName());

            Multipart multipart = new mimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(pdfPart);

            mimeMessage.setSubject(subject);
            mimeMessage.setFrom(new InternetAddress("your_email@example.com"));
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setContent(multipart);

            emailSender.send(mimeMessage);
            return new ResponseEntity<>("Email Sent Successfully!", HttpStatus.OK);
        } catch (MessagingException | IOException e) {
            return new ResponseEntity<>("Error Occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}