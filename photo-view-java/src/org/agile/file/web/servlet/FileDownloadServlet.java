package org.agile.file.web.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 2472992451592754307L;

	private String fileRoot;

	public FileDownloadServlet() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// String uri = request.getRequestURI();
		String name = fileRoot + request.getQueryString();
		File file = new File(name);
		if (file.exists()) {
			log("Down file " + name);
		} else {
			log(name + " not exists! ");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void init() throws ServletException {
		super.init();
		ServletContext config = this.getServletContext();
		fileRoot = config.getInitParameter("fileStoreRoot");
		if (fileRoot == null || fileRoot.trim().length() == 0) {
			// throw new ServletException("Store file's root is null, can't startup!");
		} else {
			File file = new File(fileRoot);
			if (!file.exists()) {
				file.mkdirs();
			}
		}
		log("Store file's root is " + fileRoot);

	}

	public void destroy() {
		super.destroy();
	}

	public void log(String msg) {
		System.out.println(msg);
	}
}