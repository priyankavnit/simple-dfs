package org.agile.dfs.client.service;

import org.agile.dfs.client.listener.NameNodeListener;

import junit.framework.TestCase;

public class MulticastServiceTest extends TestCase {

	NameNodeListener service;

	protected void setUp() throws Exception {
		super.setUp();
		service = NameNodeListener.instance();
		service.start();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testRun() throws InterruptedException {
		// fail("Not yet implemented");
		Thread.sleep(60 * 1000 * 1000);
	}

}
