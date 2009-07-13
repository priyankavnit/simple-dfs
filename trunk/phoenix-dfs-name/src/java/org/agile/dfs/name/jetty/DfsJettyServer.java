package org.agile.dfs.name.jetty;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class DfsJettyServer {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String dfsWebHome = "/lv-phoenix/studio/workspace/phoenix-dfw";
        Server server = new Server();

        Connector connector = new SelectChannelConnector();
        connector.setPort(Integer.getInteger("jetty.port", 8080).intValue());
        server.setConnectors(new Connector[] { connector });

        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        webapp.setWar(dfsWebHome + "/webapp");
        // webapp.setDefaultsDescriptor(dfsWebHome + "/webapp/WEB-INF/web.xml");

        server.setHandler(webapp);

        server.start();
        server.join();

    }

}
