package org.agile.dfs.rpc.endpoint;

import org.agile.dfs.util.StringUtil;

public class EndpointFactory {

    public Endpointable findEndpoint(String endpoint) {
        // tcp endpoint, ex:tcp://127.0.0.1:43100
        if (endpoint.startsWith("tcp://")) {
            String[] ip2port = StringUtil.simpleSplit(endpoint.substring(6), new char[] { ':', '/' });
            return new TcpEndpoint(ip2port[0], Integer.parseInt(ip2port[1]));
        }
        return null;
    }
}
