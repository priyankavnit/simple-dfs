package org.agile.file.web.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.agile.file.entity.FileItem;
import org.agile.file.manager.FileItemManager;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.appengine.api.datastore.Blob;

public class FileUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1978L;
    private static FileItemManager manager;
    private static int maxFileSize = 10 * 1024 * 1024; // 10M

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintStream out = System.out;
        try {
            ServletFileUpload upload = new ServletFileUpload();
            upload.setSizeMax(maxFileSize);
            FileItemIterator iterator = upload.getItemIterator(req);
            while (iterator.hasNext()) {
                FileItemStream item = iterator.next();
                InputStream in = item.openStream();

                if (item.isFormField()) {
                    out.println("Got a form field: " + item.getFieldName());
                } else {
                    String fieldName = item.getFieldName();
                    String fileName = item.getName();
                    String contentType = item.getContentType();

                    // do some logging
                    out.println("-----------------------------------------");
                    out.println("fileName = " + fileName);
                    out.println("field name = " + fieldName);
                    out.println("contentType = " + contentType);

                    // persist into appengine store
                    FileItem file = new FileItem();
                    file.setName(fileName);
                    file.setModified(new java.util.Date());
                    try {
                        file.setData(new Blob(IOUtils.toByteArray(in)));
                        manager.save(file);
                        out.println("file id = " + file.getId());
                    } finally {
                        IOUtils.closeQuietly(in);
                    }
                }
            }
        } catch (SizeLimitExceededException e) {
            out.println("Max upload size(" + e.getPermittedSize() + "), your size(" + e.getActualSize() + ")");
        } catch (FileUploadException e) {
            out.println("File upload exception! " + e);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        manager = (FileItemManager) context.getBean("photo.FileItemManager");
    }

    @Override
    public void destroy() {
        super.destroy();
    }

}