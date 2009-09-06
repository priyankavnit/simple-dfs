package org.agile.upload.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.agile.upload.entity.FileItem;
import org.agile.upload.helper.MimeUtil;
import org.agile.upload.manager.FileItemManager;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class FileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1978L;
	private static FileItemManager manager;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sid = request.getParameter("fileItemId");
		if (sid != null) {
			Long id = Long.parseLong(sid);
			FileItem item = manager.findById(id);
			if (item != null && item.getRawimage() != null) {
				String mime = MimeUtil.getMimeByFileName(item.getName());
				response.setContentType(mime);
				byte[] data = item.getRawimage().getBytes();
				ServletOutputStream os = response.getOutputStream();
				os.write(data);
				os.flush();
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void init() throws ServletException {
		super.init();
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		manager = (FileItemManager) context.getBean("upload.FileItemManager");
	}

	public void destroy() {
		super.destroy();
	}

}