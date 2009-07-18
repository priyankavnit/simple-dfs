package org.agile.dfs.rpc.endpoint;

import java.util.HashMap;
import java.util.Map;

import org.agile.dfs.util.StringUtil;

public class EndpointFactory {

    private static final Map<String, Endpointable> cache = new HashMap<String, Endpointable>();

    public Endpointable findEndpoint(String endpoint) {
        endpoint = endpoint.trim();
        Endpointable item = cache.get(endpoint);
        if (item != null && !item.isClose()) {
            return item;
        }
        // tcp endpoint, ex:tcp://127.0.0.1:43100
        if (endpoint.startsWith("tcp://")) {
            String[] ip2port = StringUtil.simpleSplit(endpoint.substring(6), new char[] { ':', '/' });
            Endpointable end = new TcpEndpoint(ip2port[0], Integer.parseInt(ip2port[1]));
            cache.put(endpoint, end);
            return end;
        }
        return null;
    }
}
