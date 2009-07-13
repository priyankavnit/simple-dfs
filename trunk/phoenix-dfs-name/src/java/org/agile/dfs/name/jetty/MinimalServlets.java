package org.agile.dfs.name.jetty;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.servlet.ServletHandler;

public class MinimalServlets {
    public static void main(String[] args) throws Exception {
        Server server = new Server();
        Connector connector = new SocketConnector();
        connector.setPort(8080);
        server.setConnectors(new Connector[] { connector });

        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);

        handler.addServletWithMapping("org.agile.dfs.core.name.jetty.MinimalServlets$HelloServlet", "/");

        server.start();
        server.join();
    }

    public static class HelloServlet extends HttpServlet {
        private static final long serialVersionUID = 1978;

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                IOException {
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("<h1>Hello SimpleServlet</h1>");
        }
    }
}
