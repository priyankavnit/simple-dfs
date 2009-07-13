package org.agile.dfs.entity;

import org.agile.dfs.core.entity.NodeItem;

import junit.framework.Assert;
import junit.framework.TestCase;

public class NodeItemTest extends TestCase {

	public void testFromString() {
		NodeItem item = new NodeItem();
		item.setIp("127.0.0.1");
		item.setPort(45100);
		item.setType(NodeItem.NODE_TYPE_DATA);
		item.setStatus(NodeItem.NODE_STATUS_RUN);

		String str = item.toString();

		NodeItem item2 = NodeItem.fromString(str);

		Assert.assertEquals(str, item2.toString());
		System.out.println("Item 1:" + item.toString());
		System.out.println("Item 2:" + item2.toString());
	}

}
