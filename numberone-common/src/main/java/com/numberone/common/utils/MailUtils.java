/**
 * 
 */
package com.numberone.common.utils;

import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.numberone.common.config.Global;

/**
 * 邮件工具类
 * <p>Title:MailUtils</p>
 * @author 段佳佳
 * @date 2020年4月14日
 */
public class MailUtils {
	private static String defaultAccount = Global.getConfig("numberone.defaultAccount");
	private static String defaultPwd = Global.getConfig("numberone.defaultPwd");
	
	/**
	 * 发送邮件
	 * @param receAddr 接收方邮箱地址
	 * @param subject 邮件标题
	 * @param content 邮件内容
	 * @param appendixAddr 附件地址
	 * @return
	 * @throws Exception
	 */
	public static int sendMail(String receAddr,String subject,String content,String appendixAddr,String originalFileName)
			throws Exception{
		
		return sendMail(defaultAccount, receAddr, subject, content, appendixAddr,originalFileName, defaultAccount, defaultPwd);
	}
	/**
	 * 发送邮件
	 * @param receAddr 接收方邮箱地址
	 * @param subject 邮件标题
	 * @param content 邮件内容
	 * @return
	 * @throws Exception
	 */
	public static int sendMail(String receAddr,String subject,String content)
			throws Exception{
		
		return sendMail(receAddr, subject, content, null,null);
	}
	/**
	 * 发送任务邮件
	 * @param receAddr 接收方邮箱地址
	 * @param map 任务参数
	 * @return
	 * @throws Exception
	 */
	public static int sendTaskMail(Map<String,String> map)
			throws Exception{
		
		StringBuffer sb = new StringBuffer();
		sb.append("任务");
		sb.append("【");
		sb.append(map.get("taskId"));
		sb.append("】【");
		sb.append(map.get("taskType"));
		sb.append("】");
		sb.append("由【");
		sb.append(map.get("createUserName"));
		sb.append("】指派给【");
		sb.append(map.get("dealUserName"));
		sb.append("】");
		
		return sendMail(map.get("receAddr"), sb.toString(), sb.toString() ,map.get("appendixAddr"), map.get("originalFileName"));
	}
	/**
	 * 
	 * @param receAddr 接收方邮箱地址
	 * @param map 任务参数
	 * @return
	 * @throws Exception
	 */
	public static int sendTaskAlterMail(Map<String,String> map)
			throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("任务");
		sb.append("【");
		sb.append(map.get("taskId"));
		sb.append("】【");
		sb.append(map.get("taskType"));
		sb.append("】");
		sb.append("由【");
		sb.append(map.get("createUserName"));
		sb.append("】转交给【");
		sb.append(map.get("dealUserName"));
		sb.append("】");
		
		return sendMail(map.get("receAddr"), sb.toString(), sb.toString() );
	}
	/**
	 * 发送邮件最大的方法
	 * @param fromAddr 发送者邮箱地址
	 * @param receAddr 接收方邮箱地址
	 * @param subject 邮件标题
	 * @param content 邮件内容
	 * @param appendixAddr 附件地址
	 * @param authAccount 授权邮箱账户
	 * @param authPwd 授权密码
	 * @return
	 * @throws Exception
	 */
	public static int sendMail(String fromAddr,String receAddr,String subject,String content,String appendixAddr,
			String originalFileName,String authAccount,String authPwd)
			throws Exception{
		Properties props = new Properties();//key value:配置参数。真正发送邮件时再配置
        
        props.setProperty("mail.transport.protocol", "smtp");//指定邮件发送的协议，参数是规范规定的
        props.setProperty("mail.host", "smtp.126.com");//指定发件服务器的地址，参数是规范规定的
//        props.setProperty("mail.debug", "true");//邮件发送的调试模式，参数是规范规定的
        props.setProperty("mail.smtp.auth", "true");//请求服务器进行身份认证。参数与具体的JavaMail实现有关
        
	        /*Authenticator auth = new Authenticator() {
	        	public PasswordAuthentication getPasswordAuthentication() {
	        		return new PasswordAuthentication("qq2554033105@126.com", "qq2554033105");
	        	}
	        };*/
        
        Session session = Session.getInstance(props);//发送邮件时使用的环境配置
//      session.setDebug(true);
        MimeMessage message = new MimeMessage(session);
        // 创建验证器
        //设置邮件的头
        message.setFrom(new InternetAddress(fromAddr));
        message.setRecipients(Message.RecipientType.TO, receAddr);
        message.setSubject(subject);
        //设置正文
        
        //文本部分
        MimeBodyPart textPart = new MimeBodyPart();
        //textPart.setContent("美女<img src='cid:mm'/>aaa", "text/html;charset=UTF-8");
        textPart.setContent(content, "text/html;charset=UTF-8");
        
	        //搞图片部分
	        /*MimeBodyPart imagePart = new MimeBodyPart();
	        imagePart.setContentID("mm");
	        //把磁盘上的文件加到part中使用到了JAF框架
	        DataHandler dh = new DataHandler(new FileDataSource("巴蛇.jpg"));
	        imagePart.setDataHandler(dh);*/
        MimeMultipart mp = new MimeMultipart();
        mp.addBodyPart(textPart);
        //mp.addBodyPart(imagePart);
        mp.setSubType("related");//有关系的
        
        MimeBodyPart textImagePart = new MimeBodyPart();    //将 MimeMultipart 添加到 MimeBodyPart实现附件的发送
        textImagePart.setContent(mp);
        //最终的 MimeMultipart
        MimeMultipart finalMp = new MimeMultipart();
        
        //创建附件部分
        if(!StringUtils.isEmpty(appendixAddr)){
        	MimeBodyPart attachmentPart = new MimeBodyPart();
        	String baseDir = Global.getUploadPath();
        	DataHandler dh = new DataHandler(new FileDataSource(baseDir+appendixAddr));
        	attachmentPart.setDataHandler(dh);
        	
        	//手工设置文件名  防止乱码使用  javaMail里的 MimeUtility进行编码
        	attachmentPart.setFileName(MimeUtility.encodeText(originalFileName)); 
        	
        	finalMp.addBodyPart(attachmentPart);
        }
        finalMp.addBodyPart(textImagePart);
        
        finalMp.setSubType("mixed");
        
        message.setContent(finalMp);
        message.saveChanges();
        
        //发送邮件
        Transport ts = session.getTransport();
        ts.connect(authAccount, authPwd); //密码为授权码不是邮箱的登录密码
        ts.sendMessage(message, message.getAllRecipients());//对象，用实例方法
        return 0;
	}
	
}
