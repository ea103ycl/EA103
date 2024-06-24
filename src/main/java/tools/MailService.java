package tools;

import java.util.Properties;

//import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
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
import javax.servlet.http.HttpServletRequest;

import com.mem.model.MemVO;

public class MailService
{

	// �]�w�ǰe�l��:�ܦ��H�H��Email�H�c,Email�D��,���ǰe�Ϥ������|,�ШD�ѼƤΦ���HVO
	public static void sendMail(String to, String subject, String photoPath, HttpServletRequest req, MemVO memVO)
	{

		// -----------------------------------------�HEmail�Ϥ���-----------------------------------------------

		try
			{
				// Email�������]�K�Xbutton���W�s��
				StringBuffer url = req.getRequestURL();

				String reqStr = "?action=URLfromEmail&memid=";

				String accno = memVO.getM_accno();
				String mem_id = memVO.getMem_id();

				url.append(reqStr);
				url.append(mem_id);

				System.out.println(url);

				// �]�w�ϥ�SSL�s�u�� Gmail smtp Server
				Properties props = new Properties();

				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.socketFactory.port", "465");
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port", "465");

				// ���]�w gmail ���b�� & �K�X (�N�ǥѧA��Gmail�ӶǰeEmail)

				// �����NmyGmail���i�w���ʸ��C�����ε{���s���v�j���}

				final String myGmail = "ea103g1@gmail.com"; // �H��H�b��

				final String myGmail_password = "!1qaz2wsx"; // �H��H�K�X

				Session session = Session.getInstance(props, new Authenticator()
				{
					protected PasswordAuthentication getPasswordAuthentication()
					{
						return new PasswordAuthentication(myGmail, myGmail_password);
					}
				});

				Transport transport = session.getTransport();

				Message message = new MimeMessage(session);

				// �]�w�H��H
				message.setFrom(new InternetAddress(myGmail));

				// �]�w����H
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

				// �]�w�H�����D��
				message.setSubject(subject);

				// �]�w�H�������e

				// ��r�����A�`�N img src �����n�� cid:���U�����ɪ�header
				MimeBodyPart textPart = new MimeBodyPart();
				StringBuffer html = new StringBuffer();

				html.append(
						"<div style=\"text-align:center\"><img style=\"width=100px;margin:0px auto\" src='cid:image'/><br>");
				html.append("<h2>Hi&ensp;" + accno + "</h2><br>");
				html.append("<h2>���I��H�U���s���]�z���K�X</h2><br>");
				// �ɦV���]�K�X�����s
				html.append("<div><!--[if mso]>\r\n"
						+ "  <v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"\" style=\"height:40px;v-text-anchor:middle;width:200px;\" arcsize=\"10%\" strokecolor=\"#ffffff\" fillcolor=\"#a26d53\">\r\n"
						+ "    <w:anchorlock/>\r\n"
						+ "    <center style=\"color:#ffffff;font-family:sans-serif;font-size:13px;font-weight:bold;\">Show me the button!</center>\r\n"
						+ "  </v:roundrect>\r\n" + "<![endif]--><a href=\"" + url + "\"\r\n"
						+ "style=\"background-color:#C39D6D;border:1px solid #ffffff;border-radius:4px;color:#ffffff;display:inline-block;font-family:sans-serif;font-size:13px;font-weight:bold;line-height:40px;text-align:center;text-decoration:none;width:200px;-webkit-text-size-adjust:none;mso-hide:all;\">���]�K�X</a></div></div>");

				textPart.setContent(html.toString(), "text/html; charset=UTF-8");

				// ���ɳ����A�`�N html �� cid:image�A�hheader�n�]<image>
				MimeBodyPart picturePart = new MimeBodyPart();

//				System.out.println(photoPath);
				FileDataSource fds = new FileDataSource(photoPath);
				picturePart.setDataHandler(new DataHandler(fds));
				picturePart.setFileName(fds.getName());
				picturePart.setHeader("Content-ID", "<image>");

				Multipart email = new MimeMultipart();
				email.addBodyPart(textPart);
				email.addBodyPart(picturePart);

				message.setContent(email);

				transport.connect();
				transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
				transport.close();

				System.out.println("傳送成功!");
			} catch (MessagingException e)
			{
				System.out.println("傳送失敗!");
				e.printStackTrace();
			}

	}

	// �]�w�ǰe�l��:�ܦ��H�H��Email�H�c,Email�D��,���ǰe�Ϥ������|,�ШD�ѼƤΦ���HVO
	public static void sendEmail4Verify(String to, String subject, String photoPath, HttpServletRequest req,
			MemVO memVO)
	{

		// -----------------------------------------�HEmail�Ϥ���-----------------------------------------------

		try
			{
				// Email�������]�K�Xbutton���W�s��
				StringBuffer url = req.getRequestURL();

				String reqStr = "?action=verify&memid=";

				String accno = memVO.getM_accno();
				String mem_id = memVO.getMem_id();

				url.append(reqStr);
				url.append(mem_id);

				System.out.println(url);

				// �]�w�ϥ�SSL�s�u�� Gmail smtp Server
				Properties props = new Properties();

				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.socketFactory.port", "465");
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port", "465");

				// ���]�w gmail ���b�� & �K�X (�N�ǥѧA��Gmail�ӶǰeEmail)

				// �����NmyGmail���i�w���ʸ��C�����ε{���s���v�j���}

				final String myGmail = "ea103g1@gmail.com"; // �H��H�b��

				final String myGmail_password = "!1qaz2wsx"; // �H��H�K�X

				Session session = Session.getInstance(props, new Authenticator()
				{
					protected PasswordAuthentication getPasswordAuthentication()
					{
						return new PasswordAuthentication(myGmail, myGmail_password);
					}
				});

				Transport transport = session.getTransport();

				Message message = new MimeMessage(session);

				// �]�w�H��H
				message.setFrom(new InternetAddress(myGmail));

				// �]�w����H
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

				// �]�w�H�����D��
				message.setSubject(subject);

				// �]�w�H�������e

				// ��r�����A�`�N img src �����n�� cid:���U�����ɪ�header
				MimeBodyPart textPart = new MimeBodyPart();
				StringBuffer html = new StringBuffer();

				html.append(
						"<div style=\"text-align:center\"><img style=\"width=100px;margin:0px auto\" src='cid:image'/><br>");
				html.append("<h2>Hi&ensp;" + accno + "</h2><br>");
				html.append("<h2>�̫�@�B, �I���U����s��}�l����ArtsBlock!</h2><br>");
				// ��s�|�����Ҫ��A�����s
				html.append("<div><!--[if mso]>\r\n"
						+ "  <v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"\" style=\"height:40px;v-text-anchor:middle;width:200px;\" arcsize=\"10%\" strokecolor=\"#ffffff\" fillcolor=\"#a26d53\">\r\n"
						+ "    <w:anchorlock/>\r\n"
						+ "    <center style=\"color:#ffffff;font-family:sans-serif;font-size:13px;font-weight:bold;\">Show me the button!</center>\r\n"
						+ "  </v:roundrect>\r\n" + "<![endif]--><a href=\"" + url + "\"\r\n"
						+ "style=\"background-color:#C39D6D;border:1px solid #ffffff;border-radius:4px;color:#ffffff;display:inline-block;font-family:sans-serif;font-size:13px;font-weight:bold;line-height:40px;text-align:center;text-decoration:none;width:200px;-webkit-text-size-adjust:none;mso-hide:all;\">�T�{����</a></div></div>");

				textPart.setContent(html.toString(), "text/html; charset=UTF-8");

				// ���ɳ����A�`�N html �� cid:image�A�hheader�n�]<image>
				MimeBodyPart picturePart = new MimeBodyPart();

//					System.out.println(photoPath);
				FileDataSource fds = new FileDataSource(photoPath);
				picturePart.setDataHandler(new DataHandler(fds));
				picturePart.setFileName(fds.getName());
				picturePart.setHeader("Content-ID", "<image>");

				Multipart email = new MimeMultipart();
				email.addBodyPart(textPart);
				email.addBodyPart(picturePart);

				message.setContent(email);

				transport.connect();
				transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
				transport.close();

				System.out.println("傳送成功!");
			} catch (MessagingException e)
			{
				System.out.println("傳送失敗!");
				e.printStackTrace();
			}

	}

	// ���ե�

//	public static void main(String args[]) {
//
//		String path = "C:\\Users\\Big data\\Desktop\\15897945014338.jpg";
//
//		String to = "caroline20126862@gmail.com";
//
//		String subject = "�K�X�q��";
//
//		String ch_name = "peter1";
//		String passRandom = "111";
//		String messageText = "Hello! " + ch_name + " ���԰O���K�X: " + passRandom + "\n" + " (�w�g�ҥ�)";
//
//		MailService.sendMail(to, subject, messageText, path);
//
//	}

}
//-----------------------------------------�HEmail��r��-----------------------------------------------
//import java.util.Properties;
//
//import javax.mail.Authenticator;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//
//public class MailService
//{
//
//	// �]�w�ǰe�l��:�ܦ��H�H��Email�H�c,Email�D��,Email���e
//	public void sendMail(String to, String subject, String messageText)
//	{
//
//		try
//			{
//				// �]�w�ϥ�SSL�s�u�� Gmail smtp Server
//				Properties props = new Properties();
//				props.put("mail.smtp.host", "smtp.gmail.com");
//				props.put("mail.smtp.socketFactory.port", "465");
//				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//				props.put("mail.smtp.auth", "true");
//				props.put("mail.smtp.port", "465");
//
//				// ���]�w gmail ���b�� & �K�X (�N�ǥѧA��Gmail�ӶǰeEmail)
//				// �����NmyGmail���i�w���ʸ��C�����ε{���s���v�j���}
//				final String myGmail = "caroline20126862@gmail.com";
//				final String myGmail_password = "caroline159357";
//				Session session = Session.getInstance(props, new Authenticator()
//				{
//					protected PasswordAuthentication getPasswordAuthentication()
//					{
//						return new PasswordAuthentication(myGmail, myGmail_password);
//					}
//				});
//
//				Message message = new MimeMessage(session);
//				message.setFrom(new InternetAddress(myGmail));
//				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//
//				// �]�w�H�����D��
//				message.setSubject(subject);
//				// �]�w�H�������e
//				message.setText(messageText);
//
//				Transport.send(message);
//				System.out.println("�ǰe���\!");
//			} catch (MessagingException e)
//			{
//				System.out.println("�ǰe����!");
//				e.printStackTrace();
//			}
//	}
//
//	public static void main(String args[])
//	{
//
//		String to = "ea103g1@gmail.com";
//
//		String subject = "�K�X�q��";
//
//		String ch_name = "peter1";
//		String passRandom = "111";
//		String messageText = "Hello! " + ch_name + " ���԰O���K�X: " + passRandom + "\n" + " (�w�g�ҥ�)";
//
//		MailService mailService = new MailService();
//		mailService.sendMail(to, subject, messageText);
//
//	}
//
//}
