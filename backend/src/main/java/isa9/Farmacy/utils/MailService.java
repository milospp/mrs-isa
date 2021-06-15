package isa9.Farmacy.utils;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.EPrescriptionDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;

@Service
public class MailService {

    @Value("${frontend.application.url}")
    private String host;
    @Value("${spring.mail.username}")
    private String email;
    @Value("${spring.mail.password}")
    private String password;

    private String startOfMail = "" +
            "<!doctype html>\n" +
            "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
            "  <head>\n" +
            "    <title>\n" +
            "    </title>\n" +
            "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "    <style type=\"text/css\">\n" +
            "      #outlook a{padding: 0;}\n" +
            "      \t\t\t.ReadMsgBody{width: 100%;}\n" +
            "      \t\t\t.ExternalClass{width: 100%;}\n" +
            "      \t\t\t.ExternalClass *{line-height: 100%;}\n" +
            "      \t\t\tbody{margin: 0; padding: 0; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%;}\n" +
            "      \t\t\ttable, td{border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt;}\n" +
            "      \t\t\timg{border: 0; height: auto; line-height: 100%; outline: none; text-decoration: none; -ms-interpolation-mode: bicubic;}\n" +
            "      \t\t\tp{display: block; margin: 13px 0;}\n" +
            "    </style>\n" +
            "    <!--[if !mso]><!-->\n" +
            "    <style type=\"text/css\">\n" +
            "      @media only screen and (max-width:480px) {\n" +
            "      \t\t\t  \t\t@-ms-viewport {width: 320px;}\n" +
            "      \t\t\t  \t\t@viewport {\twidth: 320px; }\n" +
            "      \t\t\t\t}\n" +
            "    </style>\n" +
            "    <!--<![endif]-->\n" +
            "    <!--[if mso]> \n" +
            "\t\t<xml> \n" +
            "\t\t\t<o:OfficeDocumentSettings> \n" +
            "\t\t\t\t<o:AllowPNG/> \n" +
            "\t\t\t\t<o:PixelsPerInch>96</o:PixelsPerInch> \n" +
            "\t\t\t</o:OfficeDocumentSettings> \n" +
            "\t\t</xml>\n" +
            "\t\t<![endif]-->\n" +
            "    <!--[if lte mso 11]> \n" +
            "\t\t<style type=\"text/css\"> \n" +
            "\t\t\t.outlook-group-fix{width:100% !important;}\n" +
            "\t\t</style>\n" +
            "\t\t<![endif]-->\n" +
            "    <style type=\"text/css\">\n" +
            "      @media only screen and (max-width:480px) {\n" +
            "      \n" +
            "      \t\t\t  table.full-width-mobile { width: 100% !important; }\n" +
            "      \t\t\t\ttd.full-width-mobile { width: auto !important; }\n" +
            "      \n" +
            "      }\n" +
            "      @media only screen and (min-width:480px) {\n" +
            "      .dys-column-per-100 {\n" +
            "      \twidth: 100.000000% !important;\n" +
            "      \tmax-width: 100.000000%;\n" +
            "      }\n" +
            "      }\n" +
            "      @media only screen and (min-width:480px) {\n" +
            "      .dys-column-per-50 {\n" +
            "      \twidth: 50.000000% !important;\n" +
            "      \tmax-width: 50.000000%;\n" +
            "      }\n" +
            "      .dys-column-per-100 {\n" +
            "      \twidth: 100.000000% !important;\n" +
            "      \tmax-width: 100.000000%;\n" +
            "      }\n" +
            "      .dys-column-per-90 {\n" +
            "      \twidth: 90% !important;\n" +
            "      \tmax-width: 90%;\n" +
            "      }\n" +
            "      }\n" +
            "      @media only screen and (min-width:480px) {\n" +
            "      .dys-column-per-100 {\n" +
            "      \twidth: 100.000000% !important;\n" +
            "      \tmax-width: 100.000000%;\n" +
            "      }\n" +
            "      }\n" +
            "    </style>\n" +
            "  </head>\n" +
            "  <body>\n" +
            "    <div>\n" +
            "      <!--[if mso | IE]>\n" +
            "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:600px;\" width=\"600\"><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
            "<![endif]-->\n" +
            "      <div style='background:#4DBFBF;background-color:#4DBFBF;margin:0px auto;max-width:600px;'>\n" +
            "        <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#4DBFBF;background-color:#4DBFBF;width:100%;'>\n" +
            "          <tbody>\n" +
            "            <tr>\n" +
            "              <td style='direction:ltr;font-size:0px;padding:20px 0;text-align:center;vertical-align:top;'>\n" +
            "                <!--[if mso | IE]>\n" +
            "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:600px;\">\n" +
            "<![endif]-->\n" +
            "                <div class='dys-column-per-100 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
            "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
            "                    <tr>\n" +
            "                      <td align='center' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
            "                        <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='border-collapse:collapse;border-spacing:0px;'>\n" +
            "                          <tbody>\n" +
            "                            <tr>\n" +
            "                              <td style='width:216px;'>\n" +
            "                                <img alt='Descriptive Alt Text' src='https://www.graphicsprings.com/filestorage/stencils/35d2e68662e9eb85e1449241fe11cfbc.png?width=500&height=500' style='border:none;display:block;font-size:13px;height:189px;outline:none;text-decoration:none;width:100%;height:auto;' />\n" +
            "                              </td>\n" +
            "                            </tr>\n" +
            "                          </tbody>\n" +
            "                        </table>\n" +
            "                      </td>\n" +
            "                    </tr>\n" +
            "                    <tr>\n" +
            "                      <td align='center' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
            "                        <div style=\"color:#FFFFFF;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:36px;line-height:1;text-align:center;\">\n";

    private String endOfMail =                 "                        </div>\n" +
            "                      </td>\n" +
            "                    </tr>\n" +
            "                  </table>\n" +
            "                </div>\n" +
            "                <!--[if mso | IE]>\n" +
            "</td></tr></table>\n" +
            "<![endif]-->\n" +
            "              </td>\n" +
            "            </tr>\n" +
            "          </tbody>\n" +
            "        </table>\n" +
            "      </div>\n" +
            "      <!--[if mso | IE]>\n" +
            "</td></tr></table>\n" +
            "<![endif]-->\n" +
            "      <!--[if mso | IE]>\n" +
            "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:600px;\" width=\"600\"><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
            "<![endif]-->\n" +
            "      <div style='background:#F5774E;background-color:#F5774E;margin:0px auto;max-width:600px;'>\n" +
            "        <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#F5774E;background-color:#F5774E;width:100%;'>\n" +
            "          <tbody>\n" +
            "            <tr>\n" +
            "              <td style='direction:ltr;font-size:0px;padding:0 30px;text-align:center;vertical-align:top;'>\n" +
            "                <!--[if mso | IE]>\n" +
            "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:540px;\">\n" +
            "<![endif]-->\n" +
            "                <div class='dys-column-per-90 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
            "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' width='100%'>\n" +
            "                    <tbody>\n" +
            "                      <tr>\n" +
            "                        <td style='background-color:#933f24;padding:0px 0px 1px 0px;vertical-align:top;'>\n" +
            "                          <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='' width='100%'>\n" +
            "                          </table>\n" +
            "                        </td>\n" +
            "                      </tr>\n" +
            "                    </tbody>\n" +
            "                  </table>\n" +
            "                </div>\n" +
            "                <!--[if mso | IE]>\n" +
            "</td></tr></table>\n" +
            "<![endif]-->\n" +
            "              </td>\n" +
            "            </tr>\n" +
            "          </tbody>\n" +
            "        </table>\n" +
            "      </div>\n" +
            "      <!--[if mso | IE]>\n" +
            "</td></tr></table>\n" +
            "<![endif]-->\n" +
            "      <!--[if mso | IE]>\n" +
            "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:600px;\" width=\"600\"><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
            "<![endif]-->\n" +
            "      <div style='background:#F5774E;background-color:#F5774E;margin:0px auto;max-width:600px;'>\n" +
            "        <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#F5774E;background-color:#F5774E;width:100%;'>\n" +
            "          <tbody>\n" +
            "            <tr>\n" +
            "              <td style='direction:ltr;font-size:0px;padding:10px 0;text-align:center;vertical-align:top;'>\n" +
            "                <!--[if mso | IE]>\n" +
            "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:540px;\">\n" +
            "<![endif]-->\n" +
            "                <div class='dys-column-per-90 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
            "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
            "                    <tr>\n" +
            "                      <td align='left' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
            "                        <div style=\"color:#933f24;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:left;\">\n" +
            "                          Thank you for trusting us.\n" +
            "                        </div>\n" +
            "                      </td>\n" +
            "                    </tr>\n" +
            "                  </table>\n" +
            "                </div>\n" +
            "                <!--[if mso | IE]>\n" +
            "</td></tr></table>\n" +
            "<![endif]-->\n" +
            "              </td>\n" +
            "            </tr>\n" +
            "          </tbody>\n" +
            "        </table>\n" +
            "      </div>\n" +
            "      <!--[if mso | IE]>\n" +
            "</td></tr></table>\n" +
            "<![endif]-->\n" +
            "      <!--[if mso | IE]>\n" +
            "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:600px;\" width=\"600\"><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
            "<![endif]-->\n" +
            "      <div style='background:#414141;background-color:#414141;margin:0px auto;max-width:600px;'>\n" +
            "        <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#414141;background-color:#414141;width:100%;'>\n" +
            "          <tbody>\n" +
            "            <tr>\n" +
            "              <td style='direction:ltr;font-size:0px;padding:20px 0;padding-bottom:0px;text-align:center;vertical-align:top;'>\n" +
            "                <!--[if mso | IE]>\n" +
            "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:600px;\">\n" +
            "<![endif]-->\n" +
            "                <div class='dys-column-per-100 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
            "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
            "                    <tr>\n" +
            "                      <td align='center' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
            "                        <table border='0' cellpadding='0' cellspacing='0' style='cellpadding:0;cellspacing:0;color:#000000;font-family:Helvetica, Arial, sans-serif;font-size:13px;line-height:22px;table-layout:auto;width:40%;' width='40%'>\n" +
            "                          <tbody>\n" +
            "                            <tr align='center'>\n" +
            "                              <td align='center'>\n" +
            "                                <a href='https://linkedin.com'>\n" +
            "                                  <img alt='linkedin' height='50px' src='https://swu-cs-assets.s3.amazonaws.com/OSET/neopolitan/linkedin.png' width='50px'>\n" +
            "                                </a>\n" +
            "                              </td>\n" +
            "                              <td align='center'>\n" +
            "                                <a href='https://facebook.com'>\n" +
            "                                  <img alt='facebook' height='50px' src='https://swu-cs-assets.s3.amazonaws.com/OSET/neopolitan/facebook.png' width='50px'>\n" +
            "                                </a>\n" +
            "                              </td>\n" +
            "                              <td align='center'>\n" +
            "                                <a href='https://twitter.com'>\n" +
            "                                  <img alt='twitter' height='50px' src='https://swu-cs-assets.s3.amazonaws.com/OSET/neopolitan/twitter.png' width='50px'>\n" +
            "                                </a>\n" +
            "                              </td>\n" +
            "                            </tr>\n" +
            "                          </tbody>\n" +
            "                        </table>\n" +
            "                      </td>\n" +
            "                    </tr>\n" +
            "                  </table>\n" +
            "                </div>\n" +
            "                <!--[if mso | IE]>\n" +
            "</td></tr></table>\n" +
            "<![endif]-->\n" +
            "              </td>\n" +
            "            </tr>\n" +
            "          </tbody>\n" +
            "        </table>\n" +
            "      </div>\n" +
            "      <!--[if mso | IE]>\n" +
            "</td></tr></table>\n" +
            "<![endif]-->\n" +
            "      <!--[if mso | IE]>\n" +
            "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:600px;\" width=\"600\"><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
            "<![endif]-->\n" +
            "      <div style='background:#414141;background-color:#414141;margin:0px auto;max-width:600px;'>\n" +
            "        <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#414141;background-color:#414141;width:100%;'>\n" +
            "          <tbody>\n" +
            "            <tr>\n" +
            "              <td style='direction:ltr;font-size:0px;padding:20px 0;padding-top:0px;text-align:center;vertical-align:top;'>\n" +
            "                <!--[if mso | IE]>\n" +
            "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:600px;\">\n" +
            "<![endif]-->\n" +
            "                <div class='dys-column-per-100 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
            "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
            "                    <tr>\n" +
            "                      <td align='center' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
            "                        <div style=\"color:#BBBBBB;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:12px;line-height:1;text-align:center;\">\n" +
            "                          View in Browser | Unsubscribe | Contact © 2019 All Rights Reserved\n" +
            "                        </div>\n" +
            "                      </td>\n" +
            "                    </tr>\n" +
            "                  </table>\n" +
            "                </div>\n" +
            "                <!--[if mso | IE]>\n" +
            "</td></tr></table>\n" +
            "<![endif]-->\n" +
            "              </td>\n" +
            "            </tr>\n" +
            "          </tbody>\n" +
            "        </table>\n" +
            "      </div>\n" +
            "      <!--[if mso | IE]>\n" +
            "</td></tr></table>\n" +
            "<![endif]-->\n" +
            "    </div>\n" +
            "  </body>\n" +
            "</html>";

    @Async
    public void sendMail(String recipient, String subject, String messageContent) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        System.out.println("email = " + email);

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setContent(messageContent, "text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            System.out.println("e.getMessage() = " + e.getMessage());
            //e.printStackTrace();
        }
    }

    @Async
    public void sendAppointmentInfo(Appointment appointment, Boolean canceled) {
        Patient patient = appointment.getExamination().getPatient();
        Doctor doctor = appointment.getDoctor();
        Pharmacy pharmacy = appointment.getPharmacy();
        String recipient = patient.getEmail();
        String subject = "";
        String content = this.startOfMail;
        //DecimalFormat df = new DecimalFormat("###.##");
        if (canceled) {
            subject = "Appointment is canceled";
            content = content +
                    "                          Appointment is canceled\n" +
                    "                        </div>\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                      <td align='center' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
                    "                        <div style=\"color:#187272;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:16px;line-height:20px;text-align:center;\">\n" +
                    "                          Doctor is on vacation.\n";
        }
        else {
            subject = "Appointment invoice";
            content = content +
                    "                          Appointment info\n" +
                    "                        </div>\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                      <td align='center' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
                    "                        <div style=\"color:#187272;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:16px;line-height:20px;text-align:center;\">\n" +
                    "                          You have successfully booked an appointment.\n";
        }
        content = content +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </tbody>\n" +
                "        </table>\n" +
                "      </div>\n" +
                "      <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "      <!--[if mso | IE]>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:600px;\" width=\"600\"><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
                "<![endif]-->\n" +
                "      <div style='background:#F5774E;background-color:#F5774E;margin:0px auto;max-width:600px;'>\n" +
                "        <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#F5774E;background-color:#F5774E;width:100%;'>\n" +
                "          <tbody>\n" +
                "            <tr>\n" +
                "              <td style='direction:ltr;font-size:0px;padding:20px 0;text-align:center;vertical-align:top;'>\n" +
                "                <!--[if mso | IE]>\n" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:300px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-50 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:25px 25px 0 50px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#FFFFFF;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:left;\">\n" +
                "                          " + patient.getName() + " " + patient.getSurname() + "\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:0 25px 0 50px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#933f24;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:left;\">\n" +
                "                          " + patient.getAddress().getState() + " " + patient.getAddress().getCity() + " " + patient.getAddress().getStreet() + " " + patient.getAddress().getNumber() + "\n" +
                "                        </div>\n" +
                "                        <div style=\"color:#933f24;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:left;\">\n" +
                "                          " + patient.getPhoneNumber() + "\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td><td style=\"vertical-align:top;width:300px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-50 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='right' style='font-size:0px;padding:25px 50px 0 25px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#FFFFFF;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:right;\">\n" +
                "                          Invoice: " + appointment.getId() + "\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td align='right' style='font-size:0px;padding:0 50px 0 25px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#933f24;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:right;\">\n" +
                "                          " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </tbody>\n" +
                "        </table>\n" +
                "      </div>\n" +
                "      <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "      <!--[if mso | IE]>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:600px;\" width=\"600\"><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
                "<![endif]-->\n" +
                "      <div style='background:#F5774E;background-color:#F5774E;margin:0px auto;max-width:600px;'>\n" +
                "        <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#F5774E;background-color:#F5774E;width:100%;'>\n" +
                "          <tbody>\n" +
                "            <tr>\n" +
                "              <td style='direction:ltr;font-size:0px;padding:20px 0;text-align:center;vertical-align:top;'>\n" +
                "                <!--[if mso | IE]>\n" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:600px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-100 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:10px 50px;word-break:break-word;'>\n" +
                "                        <table border='0' cellpadding='10' cellspacing='0' style='cellpadding:10;cellspacing:0;color:#000000;font-family:Helvetica, Arial, sans-serif;font-size:16px;line-height:22px;table-layout:auto;width:100%;' width='100%'>\n" +
                "                          <tbody>\n" +
                "                            <tr align='center'>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Starting at\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Duration\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Doctor\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Pharmacy\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Amount\n" +
                "                              </th>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                " + appointment.getStartTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                " + appointment.getDurationInMins() + "min\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                " + doctor.getName() + " " + doctor.getSurname() + "\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                " + pharmacy.getName() + "\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                $" + String.format("%.2f", appointment.getPrice()) + "\n" +
                "                              </td>\n" +
                "                            </tr>\n" +
                "                          </tbody>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </tbody>\n" +
                "        </table>\n" +
                "      </div>\n" +
                "      <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "      <!--[if mso | IE]>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:600px;\" width=\"600\"><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
                "<![endif]-->\n" +
                "      <div style='background:#F5774E;background-color:#F5774E;margin:0px auto;max-width:600px;'>\n" +
                "        <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#F5774E;background-color:#F5774E;width:100%;'>\n" +
                "          <tbody>\n" +
                "            <tr>\n" +
                "              <td style='direction:ltr;font-size:0px;padding:10px 0;text-align:center;vertical-align:top;'>\n" +
                "                <!--[if mso | IE]>\n" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:540px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-90 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#ffffff;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:16px;line-height:1;text-align:left;\">\n" +
                "                          Address: <strong>" + pharmacy.getAddress().getState() + " " + pharmacy.getAddress().getCity() + " " + pharmacy.getAddress().getStreet() + " " + pharmacy.getAddress().getNumber() + "</strong>\n" +
                this.endOfMail;

        sendMail(recipient, subject, content);
        return;
    }

    @Async
    public void sendActionInfo(MedPrice medPrice, Patient patient, Boolean delete) {             // metoda za sladnje mejla kada se napravi promocija
        Pharmacy apoteka = medPrice.getMedicineInPharmacy().getPharmacy();
        Medicine lek = medPrice.getMedicineInPharmacy().getMedicine();
        String mejl = patient.getEmail();
        String naslov = "";
        String htmlKod = "" + this.startOfMail;
        if (delete) {
            naslov = "Action or promotion is deleted";
            htmlKod = htmlKod +
                    "                          Deleted action or promotion\n" +
                    "                        </div>\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                      <td align='center' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
                    "                        <div style=\"color:#187272;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:16px;line-height:20px;text-align:center;\">\n" +
                    "                          " + apoteka.getName() + " pharmacy deleted action or promotion.\n";
        }
        else {
            naslov = "New action or promotion";
            htmlKod = htmlKod +
                    "                          New action or promotion\n" +
                    "                        </div>\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                      <td align='center' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
                    "                        <div style=\"color:#187272;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:16px;line-height:20px;text-align:center;\">\n" +
                    "                          " + apoteka.getName() + " pharmacy have new action or promotion.\n";
        }
        htmlKod = htmlKod +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </tbody>\n" +
                "        </table>\n" +
                "      </div>\n" +
                "      <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "      <!--[if mso | IE]>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:600px;\" width=\"600\"><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
                "<![endif]-->\n" +
                "      <div style='background:#F5774E;background-color:#F5774E;margin:0px auto;max-width:600px;'>\n" +
                "        <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#F5774E;background-color:#F5774E;width:100%;'>\n" +
                "          <tbody>\n" +
                "            <tr>\n" +
                "              <td style='direction:ltr;font-size:0px;padding:20px 0;text-align:center;vertical-align:top;'>\n" +
                "                <!--[if mso | IE]>\n" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:300px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-50 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:25px 25px 0 50px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#FFFFFF;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:left;\">\n" +
                "                          " + patient.getName() + " " + patient.getSurname() + "\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:0 25px 0 50px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#933f24;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:left;\">\n" +
                "                          " + patient.getAddress().getState() + " " + patient.getAddress().getCity() + " " + patient.getAddress().getStreet() + " " + patient.getAddress().getNumber() + "\n" +
                "                        </div>\n" +
                "                        <div style=\"color:#933f24;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:left;\">\n" +
                "                          " + patient.getPhoneNumber() + "\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td><td style=\"vertical-align:top;width:300px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-50 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='right' style='font-size:0px;padding:25px 50px 0 25px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#FFFFFF;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:right;\">\n" +
               //OVDE MENJAM
                "                          \n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td align='right' style='font-size:0px;padding:0 50px 0 25px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#FFFFFF;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:right;\">\n" +
                "                          \n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </tbody>\n" +
                "        </table>\n" +
                "      </div>\n" +
                "      <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "      <!--[if mso | IE]>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:600px;\" width=\"600\"><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
                "<![endif]-->\n" +
                "      <div style='background:#F5774E;background-color:#F5774E;margin:0px auto;max-width:600px;'>\n" +
                "        <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#F5774E;background-color:#F5774E;width:100%;'>\n" +
                "          <tbody>\n" +
                "            <tr>\n" +
                "              <td style='direction:ltr;font-size:0px;padding:20px 0;text-align:center;vertical-align:top;'>\n" +
                "                <!--[if mso | IE]>\n" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:600px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-100 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:10px 50px;word-break:break-word;'>\n" +
                "                        <table border='0' cellpadding='10' cellspacing='0' style='cellpadding:10;cellspacing:0;color:#000000;font-family:Helvetica, Arial, sans-serif;font-size:16px;line-height:22px;table-layout:auto;width:100%;' width='100%'>\n" +
                "                          <tbody>\n" +
                "                            <tr align='center'>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                //OVDE MENJAM
                "                                Medicine name (code)\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Old price\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Action/promotion\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                New price\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Action discount\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Start date\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                End date\n" +
                "                              </th>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                " + lek.getName() + " (" + lek.getCode() + ")\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                $" + medPrice.getOldPrice() + "\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                " + (medPrice.getPriceType() == PriceType.ACTION ? "Action" : "Promotion")  + "\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                $" + medPrice.getPrice() + "\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                " + (medPrice.getPriceType() == PriceType.ACTION ? (100 - medPrice.getPrice() *100/ medPrice.getOldPrice()) + "%" : "") + "\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                " + medPrice.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                " + medPrice.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
                "                              </td>\n" +
                "                            </tr>\n" +
                "                          </tbody>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </tbody>\n" +
                "        </table>\n" +
                "      </div>\n" +
                "      <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "      <!--[if mso | IE]>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:600px;\" width=\"600\"><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
                "<![endif]-->\n" +
                "      <div style='background:#F5774E;background-color:#F5774E;margin:0px auto;max-width:600px;'>\n" +
                "        <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#F5774E;background-color:#F5774E;width:100%;'>\n" +
                "          <tbody>\n" +
                "            <tr>\n" +
                "              <td style='direction:ltr;font-size:0px;padding:10px 0;text-align:center;vertical-align:top;'>\n" +
                "                <!--[if mso | IE]>\n" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:540px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-90 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#ffffff;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:16px;line-height:1;text-align:left;\">\n" +
                "                          Address: <strong>" + apoteka.getAddress().getState() + " " + apoteka.getAddress().getCity() + " " + apoteka.getAddress().getStreet() + " " + apoteka.getAddress().getNumber() + "</strong>\n" +
                this.endOfMail;

        sendMail(mejl, naslov, htmlKod);
        return;
    }

    @Async
    public void sendVacationInfo(Vacation vacation) {             // metoda za sladnje mejla kada se odobri ili odbije zahtev za odmor
        Pharmacy apoteka = vacation.getPharmacy();
        Doctor doktor = vacation.getDoctor();
        String mejl = doktor.getEmail();
        String naslov = "";
        String htmlKod = "" + this.startOfMail;
        if (vacation.getStatus() == VacationRequestStatus.DENIED) {
            naslov = "Vacation request is denided";
            htmlKod = htmlKod +
                    "                          Vacation request is denided\n" +
                    "                        </div>\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                      <td align='center' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
                    "                        <div style=\"color:#187272;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:16px;line-height:20px;text-align:center;\">\n" +
                    "                          Vacation request in " + apoteka.getName() + " pharmacy is denided.\n";
        }
        else {
            naslov = "Vacation request is accepted";
            htmlKod = htmlKod +
                    "                          Vacation request is accepted\n" +
                    "                        </div>\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                      <td align='center' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
                    "                        <div style=\"color:#187272;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:16px;line-height:20px;text-align:center;\">\n" +
                    "                          Vacation request in " + apoteka.getName() + " pharmacy is accepted.\n";
        }
        htmlKod = htmlKod +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </tbody>\n" +
                "        </table>\n" +
                "      </div>\n" +
                "      <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "      <!--[if mso | IE]>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:600px;\" width=\"600\"><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
                "<![endif]-->\n" +
                "      <div style='background:#F5774E;background-color:#F5774E;margin:0px auto;max-width:600px;'>\n" +
                "        <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#F5774E;background-color:#F5774E;width:100%;'>\n" +
                "          <tbody>\n" +
                "            <tr>\n" +
                "              <td style='direction:ltr;font-size:0px;padding:20px 0;text-align:center;vertical-align:top;'>\n" +
                "                <!--[if mso | IE]>\n" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:300px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-50 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:25px 25px 0 50px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#FFFFFF;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:left;\">\n" +
                "                          " + doktor.getName() + " " + doktor.getSurname() + "\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:0 25px 0 50px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#933f24;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:left;\">\n" +
                "                          " + doktor.getAddress().getState() + " " + doktor.getAddress().getCity() + " " + doktor.getAddress().getStreet() + " " + doktor.getAddress().getNumber() + "\n" +
                "                        </div>\n" +
                "                        <div style=\"color:#933f24;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:left;\">\n" +
                "                          " + doktor.getPhoneNumber() + "\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td><td style=\"vertical-align:top;width:300px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-50 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='right' style='font-size:0px;padding:25px 50px 0 25px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#FFFFFF;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:right;\">\n" +
                //OVDE MENJAM
                "                          \n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td align='right' style='font-size:0px;padding:0 50px 0 25px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#FFFFFF;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:right;\">\n" +
                "                          \n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </tbody>\n" +
                "        </table>\n" +
                "      </div>\n" +
                "      <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "      <!--[if mso | IE]>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:600px;\" width=\"600\"><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
                "<![endif]-->\n" +
                "      <div style='background:#F5774E;background-color:#F5774E;margin:0px auto;max-width:600px;'>\n" +
                "        <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#F5774E;background-color:#F5774E;width:100%;'>\n" +
                "          <tbody>\n" +
                "            <tr>\n" +
                "              <td style='direction:ltr;font-size:0px;padding:20px 0;text-align:center;vertical-align:top;'>\n" +
                "                <!--[if mso | IE]>\n" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:600px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-100 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:10px 50px;word-break:break-word;'>\n" +
                "                        <table border='0' cellpadding='10' cellspacing='0' style='cellpadding:10;cellspacing:0;color:#000000;font-family:Helvetica, Arial, sans-serif;font-size:16px;line-height:22px;table-layout:auto;width:100%;' width='100%'>\n" +
                "                          <tbody>\n" +
                "                            <tr align='center'>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                //OVDE MENJAM
                "                                Start date\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                End date\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Status\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Why not\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Admin\n" +
                "                              </th>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                " + vacation.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                " + vacation.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                " + (vacation.getStatus() == VacationRequestStatus.ACCEPTED ? "Accepted" : "Denided")  + "\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                " + (vacation.getWhyNot() == null ? "" : vacation.getWhyNot()) + "\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                " + vacation.getAdmin().getName() + " " + vacation.getAdmin().getSurname() + "\n" +
                "                              </td>\n" +
                "                            </tr>\n" +
                "                          </tbody>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </tbody>\n" +
                "        </table>\n" +
                "      </div>\n" +
                "      <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "      <!--[if mso | IE]>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:600px;\" width=\"600\"><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
                "<![endif]-->\n" +
                "      <div style='background:#F5774E;background-color:#F5774E;margin:0px auto;max-width:600px;'>\n" +
                "        <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#F5774E;background-color:#F5774E;width:100%;'>\n" +
                "          <tbody>\n" +
                "            <tr>\n" +
                "              <td style='direction:ltr;font-size:0px;padding:10px 0;text-align:center;vertical-align:top;'>\n" +
                "                <!--[if mso | IE]>\n" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:540px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-90 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#ffffff;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:16px;line-height:1;text-align:left;\">\n" +
                "                          Address: <strong>" + apoteka.getAddress().getState() + " " + apoteka.getAddress().getCity() + " " + apoteka.getAddress().getStreet() + " " + apoteka.getAddress().getNumber() + "</strong>\n" +
                this.endOfMail;

        sendMail(mejl, naslov, htmlKod);
        return;
    }

    @Async
    public void sendOfferInfo(Offer ponuda) {             // metoda za sladnje mejla kada prihvati ili odbije ponuda
        Pharmacy apoteka = ponuda.getOrder().getPharmacy();
        Supplier dobavljac = ponuda.getSupplier();
        String mejl = dobavljac.getEmail();
        String naslov = "";
        String htmlKod = "" + this.startOfMail;
        if (ponuda.getStatus() == OfferStatus.ACCEPTED) {
            naslov = "Offer is accepted";
            htmlKod = htmlKod +
                    "                          Offer is accepted\n" +
                    "                        </div>\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                      <td align='center' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
                    "                        <div style=\"color:#187272;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:16px;line-height:20px;text-align:center;\">\n" +
                    "                          " + apoteka.getName() + " pharmacy accepted your offer.\n";
        }
        else {
            naslov = "Offer is rejected";
            htmlKod = htmlKod +
                    "                          Offer is rejected\n" +
                    "                        </div>\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                      <td align='center' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
                    "                        <div style=\"color:#187272;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:16px;line-height:20px;text-align:center;\">\n" +
                    "                          " + apoteka.getName() + " pharmacy rejected your offer.\n";
        }
        htmlKod = htmlKod +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </tbody>\n" +
                "        </table>\n" +
                "      </div>\n" +
                "      <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "      <!--[if mso | IE]>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:600px;\" width=\"600\"><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
                "<![endif]-->\n" +
                "      <div style='background:#F5774E;background-color:#F5774E;margin:0px auto;max-width:600px;'>\n" +
                "        <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#F5774E;background-color:#F5774E;width:100%;'>\n" +
                "          <tbody>\n" +
                "            <tr>\n" +
                "              <td style='direction:ltr;font-size:0px;padding:20px 0;text-align:center;vertical-align:top;'>\n" +
                "                <!--[if mso | IE]>\n" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:300px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-50 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:25px 25px 0 50px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#FFFFFF;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:left;\">\n" +
                "                          " + dobavljac.getName() + " " + dobavljac.getSurname() + "\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:0 25px 0 50px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#933f24;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:left;\">\n" +
                "                          " + dobavljac.getAddress().getState() + " " + dobavljac.getAddress().getCity() + " " + dobavljac.getAddress().getStreet() + " " + dobavljac.getAddress().getNumber() + "\n" +
                "                        </div>\n" +
                "                        <div style=\"color:#933f24;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:left;\">\n" +
                "                          " + dobavljac.getPhoneNumber() + "\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td><td style=\"vertical-align:top;width:300px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-50 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='right' style='font-size:0px;padding:25px 50px 0 25px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#FFFFFF;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:right;\">\n" +
                //OVDE MENJAM
                "                          \n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td align='right' style='font-size:0px;padding:0 50px 0 25px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#FFFFFF;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:right;\">\n" +
                "                          \n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </tbody>\n" +
                "        </table>\n" +
                "      </div>\n" +
                "      <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "      <!--[if mso | IE]>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:600px;\" width=\"600\"><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
                "<![endif]-->\n" +
                "      <div style='background:#F5774E;background-color:#F5774E;margin:0px auto;max-width:600px;'>\n" +
                "        <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#F5774E;background-color:#F5774E;width:100%;'>\n" +
                "          <tbody>\n" +
                "            <tr>\n" +
                "              <td style='direction:ltr;font-size:0px;padding:20px 0;text-align:center;vertical-align:top;'>\n" +
                "                <!--[if mso | IE]>\n" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:600px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-100 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:10px 50px;word-break:break-word;'>\n" +
                "                        <table border='0' cellpadding='10' cellspacing='0' style='cellpadding:10;cellspacing:0;color:#000000;font-family:Helvetica, Arial, sans-serif;font-size:16px;line-height:22px;table-layout:auto;width:100%;' width='100%'>\n" +
                "                          <tbody>\n" +
                "                            <tr align='center'>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                //OVDE MENJAM
                "                                Status\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Start date\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                End date\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Price\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Description\n" +
                "                              </th>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                " + (ponuda.getStatus() == OfferStatus.ACCEPTED ? "Accepted" : "Rejected") + "\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                " + ponuda.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                " + ponuda.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))  + "\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                $" + ponuda.getPrice() + "\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                " + ponuda.getOfferDescription() + "\n" +
                "                              </td>\n" +
                "                            </tr>\n" +
                "                          </tbody>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </tbody>\n" +
                "        </table>\n" +
                "      </div>\n" +
                "      <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "      <!--[if mso | IE]>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:600px;\" width=\"600\"><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
                "<![endif]-->\n" +
                "      <div style='background:#F5774E;background-color:#F5774E;margin:0px auto;max-width:600px;'>\n" +
                "        <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#F5774E;background-color:#F5774E;width:100%;'>\n" +
                "          <tbody>\n" +
                "            <tr>\n" +
                "              <td style='direction:ltr;font-size:0px;padding:10px 0;text-align:center;vertical-align:top;'>\n" +
                "                <!--[if mso | IE]>\n" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:540px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-90 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#ffffff;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:16px;line-height:1;text-align:left;\">\n" +
                "                          Address: <strong>" + apoteka.getAddress().getState() + " " + apoteka.getAddress().getCity() + " " + apoteka.getAddress().getStreet() + " " + apoteka.getAddress().getNumber() + "</strong>\n" +
                this.endOfMail;

        sendMail(mejl, naslov, htmlKod);
        return;
    }
    @Async
    public void sendActivationMail(Patient patient, String token){
        String recipient = patient.getEmail();
        String subject = "Activation link";
        String content = this.startOfMail;

        String link = this.host+"/activatePatient?token="+token;

        System.out.println(host+"/activatePatient?token="+token);

        content = content +
                "                          Activation notification\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td align='center' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#187272;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:16px;line-height:20px;text-align:center;\">\n" +
                "                          "+"Before you can log in for the first time, please activate your account using the following link: \n";

        content += "<a target=\"_blank\" href='" + link + "'>Link</a>";


        content += this.endOfMail;

        sendMail(recipient, subject, content);
        return;
    }

    @Async
    public void sendResponseMail(Complaint complaint){
        Patient author = complaint.getAuthor();
        String description = complaint.getDescription();
        String response = complaint.getResponse();
        Pharmacy pharmacy = complaint.getPharmacy();
        Doctor doctor = complaint.getDoctor();

        String complaintAbout = (pharmacy == null) ? doctor.getName()+" "+doctor.getSurname() : pharmacy.getName();

        String recipient = author.getEmail();
        String subject = "Complaint response";
        String content = this.startOfMail;

        content = content +
                "                          Complaint response\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td align='center' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#187272;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:16px;line-height:20px;text-align:center;\">\n" +
                "                          "+"to your complaint about " + complaintAbout + " has arrived.\n";


        content += "\nYour complaint was: \n"+
                description+           "\n"+
                "\n\nAdministrator's response is: \n"+
                response                +"\n";

        ;



        content += this.endOfMail;

        sendMail(recipient, subject, content);
        return;
    }

    @Async
    public void sendReservationInfo(MedReservation medReservation) {             // sending mail to patient that reservation was taken successfully
        Pharmacy pharmacy = medReservation.getMedicineInPharmacy().getPharmacy();
        Medicine medicine = medReservation.getMedicineInPharmacy().getMedicine();
        Patient patient = medReservation.getPatient();
        String email = medReservation.getPatient().getEmail();
        String subject = "";
        String content = "" + this.startOfMail;
        subject = "New reserveation";
        content = content +
                "                          You have new reserveation.\nCode: " + medReservation.getCode() + "\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td align='center' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#187272;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:16px;line-height:20px;text-align:center;\">\n" +
                "                          " + "\n";
        content = content +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </tbody>\n" +
                "        </table>\n" +
                "      </div>\n" +
                "      <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "      <!--[if mso | IE]>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:600px;\" width=\"600\"><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
                "<![endif]-->\n" +
                "      <div style='background:#F5774E;background-color:#F5774E;margin:0px auto;max-width:600px;'>\n" +
                "        <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#F5774E;background-color:#F5774E;width:100%;'>\n" +
                "          <tbody>\n" +
                "            <tr>\n" +
                "              <td style='direction:ltr;font-size:0px;padding:20px 0;text-align:center;vertical-align:top;'>\n" +
                "                <!--[if mso | IE]>\n" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:300px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-50 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:25px 25px 0 50px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#FFFFFF;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:left;\">\n" +
                "                          " + patient.getName() + " " + patient.getSurname() + "\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:0 25px 0 50px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#933f24;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:left;\">\n" +
                "                          " + patient.getAddress().getState() + " " + patient.getAddress().getCity() + " " + patient.getAddress().getStreet() + " " + patient.getAddress().getNumber() + "\n" +
                "                        </div>\n" +
                "                        <div style=\"color:#933f24;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:left;\">\n" +
                "                          " + patient.getPhoneNumber() + "\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td><td style=\"vertical-align:top;width:300px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-50 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='right' style='font-size:0px;padding:25px 50px 0 25px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#FFFFFF;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:right;\">\n" +
                //OVDE MENJAM
                "                          \n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td align='right' style='font-size:0px;padding:0 50px 0 25px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#FFFFFF;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:right;\">\n" +
                "                          \n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </tbody>\n" +
                "        </table>\n" +
                "      </div>\n" +
                "      <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "      <!--[if mso | IE]>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:600px;\" width=\"600\"><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
                "<![endif]-->\n" +
                "      <div style='background:#F5774E;background-color:#F5774E;margin:0px auto;max-width:600px;'>\n" +
                "        <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#F5774E;background-color:#F5774E;width:100%;'>\n" +
                "          <tbody>\n" +
                "            <tr>\n" +
                "              <td style='direction:ltr;font-size:0px;padding:20px 0;text-align:center;vertical-align:top;'>\n" +
                "                <!--[if mso | IE]>\n" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:600px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-100 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:10px 50px;word-break:break-word;'>\n" +
                "                        <table border='0' cellpadding='5' cellspacing='0' style='cellpadding:5;cellspacing:0;color:#000000;font-family:Helvetica, Arial, sans-serif;font-size:12px;line-height:20px;table-layout:auto;width:100%;' width='100%'>\n" +
                "                          <tbody>\n" +
                "                            <tr align='center'>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Medicine name (code)\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Price\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Quantity\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Total\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Reservation made\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Reservation expires\n" +
                "                              </th>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                " + medicine.getName() + " (" + medicine.getCode() + ")\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                $" + medReservation.getMedicineInPharmacy().getCurrentPrice().getPrice() + "\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                " + medReservation.getQuantity() + "\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                $" + medReservation.getMedicineInPharmacy().getCurrentPrice().getPrice() * medReservation.getQuantity() + "\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                " + medReservation.getReservationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                " + medReservation.getLastDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
                "                              </td>\n" +
                "                            </tr>\n" +
                "                          </tbody>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </tbody>\n" +
                "        </table>\n" +
                "      </div>\n" +
                "      <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "      <!--[if mso | IE]>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:600px;\" width=\"600\"><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
                "<![endif]-->\n" +
                "      <div style='background:#F5774E;background-color:#F5774E;margin:0px auto;max-width:600px;'>\n" +
                "        <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#F5774E;background-color:#F5774E;width:100%;'>\n" +
                "          <tbody>\n" +
                "            <tr>\n" +
                "              <td style='direction:ltr;font-size:0px;padding:10px 0;text-align:center;vertical-align:top;'>\n" +
                "                <!--[if mso | IE]>\n" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:540px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-90 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#ffffff;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:16px;line-height:1;text-align:left;\">\n" +
                "                          Address: <strong>" + pharmacy.getAddress().getState() + " " + pharmacy.getAddress().getCity() + " " + pharmacy.getAddress().getStreet() + " " + pharmacy.getAddress().getNumber() + "</strong>\n" +
                this.endOfMail;

        sendMail(email, subject, content);
        return;
    }

    @Async
    public void sendEPrescriptionInfo(EPrescription ePrescription) {             // sending mail to patient that eprescription was taken successfull
        LocalDate issueDate = ePrescription.getIssueDate();
        List<MedReservation> medReservations = ePrescription.getMedicines();
        Patient patient = ePrescription.getPatient();
        String email = patient.getEmail();
        Pharmacy pharmacy = medReservations.get(0).getMedicineInPharmacy().getPharmacy();
        String subject = "";
        String content = "" + this.startOfMail;
        subject = "Successfully taken ePrescription";
        content = content +
                "                          You have successfully taken your ePrescription: " + ePrescription.getCode() + "\nIssue date: " + issueDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +"\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td align='center' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#187272;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:16px;line-height:20px;text-align:center;\">\n" +
                "                          " + "\n";
        content = content +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </tbody>\n" +
                "        </table>\n" +
                "      </div>\n" +
                "      <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "      <!--[if mso | IE]>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:600px;\" width=\"600\"><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
                "<![endif]-->\n" +
                "      <div style='background:#F5774E;background-color:#F5774E;margin:0px auto;max-width:600px;'>\n" +
                "        <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#F5774E;background-color:#F5774E;width:100%;'>\n" +
                "          <tbody>\n" +
                "            <tr>\n" +
                "              <td style='direction:ltr;font-size:0px;padding:20px 0;text-align:center;vertical-align:top;'>\n" +
                "                <!--[if mso | IE]>\n" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:300px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-50 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:25px 25px 0 50px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#FFFFFF;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:left;\">\n" +
                "                          " + patient.getName() + " " + patient.getSurname() + "\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:0 25px 0 50px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#933f24;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:left;\">\n" +
                "                          " + patient.getAddress().getState() + " " + patient.getAddress().getCity() + " " + patient.getAddress().getStreet() + " " + patient.getAddress().getNumber() + "\n" +
                "                        </div>\n" +
                "                        <div style=\"color:#933f24;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:left;\">\n" +
                "                          " + patient.getPhoneNumber() + "\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td><td style=\"vertical-align:top;width:300px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-50 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='right' style='font-size:0px;padding:25px 50px 0 25px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#FFFFFF;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:right;\">\n" +
                //OVDE MENJAM
                "                          \n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td align='right' style='font-size:0px;padding:0 50px 0 25px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#FFFFFF;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:right;\">\n" +
                "                          \n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </tbody>\n" +
                "        </table>\n" +
                "      </div>\n" +
                "      <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "      <!--[if mso | IE]>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:600px;\" width=\"600\"><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
                "<![endif]-->\n" +
                "      <div style='background:#F5774E;background-color:#F5774E;margin:0px auto;max-width:600px;'>\n" +
                "        <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#F5774E;background-color:#F5774E;width:100%;'>\n" +
                "          <tbody>\n" +
                "            <tr>\n" +
                "              <td style='direction:ltr;font-size:0px;padding:20px 0;text-align:center;vertical-align:top;'>\n" +
                "                <!--[if mso | IE]>\n" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:600px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-100 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:10px 50px;word-break:break-word;'>\n" +
                "                        <table border='0' cellpadding='5' cellspacing='0' style='cellpadding:5;cellspacing:0;color:#000000;font-family:Helvetica, Arial, sans-serif;font-size:16px;line-height:20px;table-layout:auto;width:100%;' width='100%'>\n" +
                "                          <tbody>\n" +
                "                            <tr align='center'>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Medicine name (code)\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Price\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Quantity\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Total\n" +
                "                              </th>\n" +
                "                            </tr>\n";

        for (MedReservation medReservation : medReservations){
            Medicine medicine = medReservation.getMedicineInPharmacy().getMedicine();
            content = content +
                    "                            <tr>\n" +
                    "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                    "                                " + medicine.getName() + " (" + medicine.getCode() + ")\n" +
                    "                              </td>\n" +
                    "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                    "                                $" + medReservation.getMedicineInPharmacy().getCurrentPrice().getPrice() + "\n" +
                    "                              </td>\n" +
                    "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                    "                                " + medReservation.getQuantity() + "\n" +
                    "                              </td>\n" +
                    "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                    "                                $" + medReservation.getMedicineInPharmacy().getCurrentPrice().getPrice() * medReservation.getQuantity() + "\n" +
                    "                              </td>\n" +
                    "                            </tr>\n";
        }
        content = content +
                "                          </tbody>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </tbody>\n" +
                "        </table>\n" +
                "      </div>\n" +
                "      <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "      <!--[if mso | IE]>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:600px;\" width=\"600\"><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
                "<![endif]-->\n" +
                "      <div style='background:#F5774E;background-color:#F5774E;margin:0px auto;max-width:600px;'>\n" +
                "        <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#F5774E;background-color:#F5774E;width:100%;'>\n" +
                "          <tbody>\n" +
                "            <tr>\n" +
                "              <td style='direction:ltr;font-size:0px;padding:10px 0;text-align:center;vertical-align:top;'>\n" +
                "                <!--[if mso | IE]>\n" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:540px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-90 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#ffffff;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:16px;line-height:1;text-align:left;\">\n" +
                "                          Address: <strong>" + pharmacy.getAddress().getState() + " " + pharmacy.getAddress().getCity() + " " + pharmacy.getAddress().getStreet() + " " + pharmacy.getAddress().getNumber() + "</strong>\n" +
                this.endOfMail;

        sendMail(email, subject, content);
        return;
    }

    @Async
    public void sendDispensedReservationInfo(MedReservation medReservation) {             // sending mail to patient that reservation was taken successfully
        Pharmacy pharmacy = medReservation.getMedicineInPharmacy().getPharmacy();
        Medicine medicine = medReservation.getMedicineInPharmacy().getMedicine();
        Patient patient = medReservation.getPatient();
        String email = medReservation.getPatient().getEmail();
        String subject = "";
        String content = "" + this.startOfMail;
            subject = "Successfully taken reservation";
            content = content +
                    "                          You have successfully taken your reservation: " + medReservation.getCode() + "\n" +
                    "                        </div>\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                      <td align='center' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
                    "                        <div style=\"color:#187272;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:16px;line-height:20px;text-align:center;\">\n" +
                    "                          Pharmacist " + medReservation.getWhoDispenses().getName() + " " + medReservation.getWhoDispenses().getSurname() + " dispensed the medicine.\n";
        content = content +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </tbody>\n" +
                "        </table>\n" +
                "      </div>\n" +
                "      <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "      <!--[if mso | IE]>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:600px;\" width=\"600\"><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
                "<![endif]-->\n" +
                "      <div style='background:#F5774E;background-color:#F5774E;margin:0px auto;max-width:600px;'>\n" +
                "        <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#F5774E;background-color:#F5774E;width:100%;'>\n" +
                "          <tbody>\n" +
                "            <tr>\n" +
                "              <td style='direction:ltr;font-size:0px;padding:20px 0;text-align:center;vertical-align:top;'>\n" +
                "                <!--[if mso | IE]>\n" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:300px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-50 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:25px 25px 0 50px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#FFFFFF;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:left;\">\n" +
                "                          " + patient.getName() + " " + patient.getSurname() + "\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:0 25px 0 50px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#933f24;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:left;\">\n" +
                "                          " + patient.getAddress().getState() + " " + patient.getAddress().getCity() + " " + patient.getAddress().getStreet() + " " + patient.getAddress().getNumber() + "\n" +
                "                        </div>\n" +
                "                        <div style=\"color:#933f24;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:left;\">\n" +
                "                          " + patient.getPhoneNumber() + "\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td><td style=\"vertical-align:top;width:300px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-50 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='right' style='font-size:0px;padding:25px 50px 0 25px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#FFFFFF;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:right;\">\n" +
                //OVDE MENJAM
                "                          \n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td align='right' style='font-size:0px;padding:0 50px 0 25px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#FFFFFF;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:13px;line-height:1;text-align:right;\">\n" +
                "                          \n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </tbody>\n" +
                "        </table>\n" +
                "      </div>\n" +
                "      <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "      <!--[if mso | IE]>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:600px;\" width=\"600\"><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
                "<![endif]-->\n" +
                "      <div style='background:#F5774E;background-color:#F5774E;margin:0px auto;max-width:600px;'>\n" +
                "        <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#F5774E;background-color:#F5774E;width:100%;'>\n" +
                "          <tbody>\n" +
                "            <tr>\n" +
                "              <td style='direction:ltr;font-size:0px;padding:20px 0;text-align:center;vertical-align:top;'>\n" +
                "                <!--[if mso | IE]>\n" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:600px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-100 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:10px 50px;word-break:break-word;'>\n" +
                "                        <table border='0' cellpadding='5' cellspacing='0' style='cellpadding:5;cellspacing:0;color:#000000;font-family:Helvetica, Arial, sans-serif;font-size:12px;line-height:20px;table-layout:auto;width:100%;' width='100%'>\n" +
                "                          <tbody>\n" +
                "                            <tr align='center'>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Medicine name (code)\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Price\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Quantity\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Total\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Reservation made\n" +
                "                              </th>\n" +
                "                              <th align='center' style='background-color: #ac4d2f; color: #ffffff;'>\n" +
                "                                Reservation taken\n" +
                "                              </th>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                " + medicine.getName() + " (" + medicine.getCode() + ")\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                $" + medReservation.getMedicineInPharmacy().getCurrentPrice().getPrice() + "\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                " + medReservation.getQuantity() + "\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                $" + medReservation.getMedicineInPharmacy().getCurrentPrice().getPrice() * medReservation.getQuantity() + "\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                " + medReservation.getReservationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
                "                              </td>\n" +
                "                              <td style='background-color: #f7a084; color: #933f24; text-align: center;'>\n" +
                "                                " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
                "                              </td>\n" +
                "                            </tr>\n" +
                "                          </tbody>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </tbody>\n" +
                "        </table>\n" +
                "      </div>\n" +
                "      <!--[if mso | IE]>\n" +
                "</td></tr></table>\n" +
                "<![endif]-->\n" +
                "      <!--[if mso | IE]>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:600px;\" width=\"600\"><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
                "<![endif]-->\n" +
                "      <div style='background:#F5774E;background-color:#F5774E;margin:0px auto;max-width:600px;'>\n" +
                "        <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#F5774E;background-color:#F5774E;width:100%;'>\n" +
                "          <tbody>\n" +
                "            <tr>\n" +
                "              <td style='direction:ltr;font-size:0px;padding:10px 0;text-align:center;vertical-align:top;'>\n" +
                "                <!--[if mso | IE]>\n" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:540px;\">\n" +
                "<![endif]-->\n" +
                "                <div class='dys-column-per-90 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
                "                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
                "                    <tr>\n" +
                "                      <td align='left' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
                "                        <div style=\"color:#ffffff;font-family:'Droid Sans', 'Helvetica Neue', Arial, sans-serif;font-size:16px;line-height:1;text-align:left;\">\n" +
                "                          Address: <strong>" + pharmacy.getAddress().getState() + " " + pharmacy.getAddress().getCity() + " " + pharmacy.getAddress().getStreet() + " " + pharmacy.getAddress().getNumber() + "</strong>\n" +
                this.endOfMail;

        sendMail(email, subject, content);
        return;
    }
}
