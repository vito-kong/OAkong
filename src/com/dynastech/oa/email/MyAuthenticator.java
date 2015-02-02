package com.dynastech.oa.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MyAuthenticator extends Authenticator {
	String userName;
	String password;


	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}

	public MyAuthenticator(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
}
