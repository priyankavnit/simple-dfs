package org.agile.dfs.name.rpc;

import org.agile.dfs.config.Configuration;
import org.agile.dfs.core.entity.NameNode;
import org.agile.dfs.core.entity.NodeItem;
import org.agile.dfs.rpc.server.RpcHandler;
import org.agile.dfs.rpc.server.RpcServer;

public class NameServer extends RpcServer {

	public NameServer() {
		ip = Configuration.getProperty("name.server.ip", null);
		port = Configuration.getPropertyForInt("name.server.port", 0);
	}

	@Override
	public String getNodeType() {
		return NodeItem.NODE_TYPE_NAME;
	}

	@Override
	public RpcHandler getRpcHandler() {
		return new NameHandler();
	}

	@Override
	public NodeItem self() {
		if (_self == null) {
			_self = new NameNode(this.getIp(), this.getPort());
			_self.setStatus(NodeItem.NODE_STATUS_INIT);
		}
		return _self;
	}

	public static void main(String[] args) {
		NameServer ns = new NameServer();
		ns.startup();
	}

}
