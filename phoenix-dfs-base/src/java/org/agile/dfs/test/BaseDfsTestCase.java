package org.agile.dfs.test;

import junit.framework.TestCase;

import org.agile.dfs.core.common.Environment;

public class BaseDfsTestCase extends TestCase {

	protected void setUp() throws Exception { 
		super.setUp();
		Environment.init();
	}

	protected void tearDown() throws Exception { 
		super.tearDown();
	}

}
