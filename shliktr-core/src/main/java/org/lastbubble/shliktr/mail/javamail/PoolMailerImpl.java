package org.lastbubble.shliktr.mail.javamail;

import org.lastbubble.shliktr.IPoolEntry;
import org.lastbubble.shliktr.mail.IMessageFactory;
import org.lastbubble.shliktr.mail.IPoolMailer;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

/**
 * @version $Id$
 */
public class PoolMailerImpl implements IPoolMailer
{
	private IMessageFactory messageFactory;

	private JavaMailSender mailSender;

	private String fromAddress;


	//---------------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------------

	public void setMessageFactory( IMessageFactory messageFactory )
	{
		this.messageFactory = messageFactory;
	}

	public void setMailSender( JavaMailSender mailSender )
	{
		this.mailSender = mailSender;
	}

	public void setFromAddress( String s ) { this.fromAddress = s; }


	//---------------------------------------------------------------------------
	// Implements IPoolMailer
	//---------------------------------------------------------------------------

	/** @see	IPoolMailer#mailEntry */
	public void mailEntry( IPoolEntry entry ) throws Exception
	{
		final String fromAddress = this.fromAddress;

		final String emailAddress = entry.getPlayer().getEmailAddress();

		final String subject = new StringBuilder()
			.append("NFL Pool - ")
			.append(entry.getPlayer().getName())
			.append("'s week ")
			.append(entry.getWeek().getWeekNumber())
			.append(" picks")
			.toString();

		final String text = this.messageFactory.messageForEntry(entry);

		this.mailSender.send( new MimeMessagePreparator() {
				public void prepare( MimeMessage mimeMessage ) throws Exception
				{
					MimeMessageHelper message =
						new MimeMessageHelper(mimeMessage, true, "UTF-8");
					message.setFrom(fromAddress);
					message.setTo(emailAddress);
					message.addCc(fromAddress);
					message.setSubject(subject);
					message.setText(text, true);
				}
			}
		);
	}
}