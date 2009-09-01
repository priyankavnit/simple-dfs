package org.agile.upload.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.agile.upload.entity.FileItem;
import org.agile.upload.manager.FileItemManager;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.appengine.api.datastore.Blob;

public class JpaServlet extends HttpServlet {
    private static final long serialVersionUID = 2472992451592754307L;
    private static FileItemManager manager;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FileItem item = new FileItem();
        item.setName("demo");
        item.setModified(new java.util.Date());
        item.setData(new Blob("some body".getBytes()));
        manager.save(item);
        System.out.println("------> " + item.getId());
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void init() throws ServletException {
        super.init();
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        manager = (FileItemManager) context.getBean("upload.FileItemManager");
    }

}