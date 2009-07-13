package org.agile.dfs.name.storage;

import junit.framework.TestCase;

import org.agile.dfs.name.jdbc.JdbcTemplate;

public class JdbcTemplateTest extends TestCase {

	JdbcTemplate template;
	protected void setUp() throws Exception {
		super.setUp();
		template = new JdbcTemplate();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testExecute() {
		fail("Not yet implemented");
	}

	public void testDoInActionWithTransaction() {
		fail("Not yet implemented");
	}

	public void testDoInAction() {
		fail("Not yet implemented");
	}

}
