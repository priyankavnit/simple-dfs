package org.agile.dfs.web.service;

import com.caucho.hessian.server.HessianServlet;

public class BasicService extends HessianServlet implements BasicAPI {
	private static final long serialVersionUID = 1978;
	private String _greeting = "Hello, world";

	public void setGreeting(String greeting) {
		_greeting = greeting;
	}

	public String hello() {
		String s = new java.util.Date() + ":" + _greeting;
		System.out.println(s);
		return s;
	}

}
