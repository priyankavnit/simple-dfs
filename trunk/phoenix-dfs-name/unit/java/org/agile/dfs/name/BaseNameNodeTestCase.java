package org.agile.dfs.name;

import junit.framework.TestCase;

import org.agile.dfs.core.common.UuidHexGenerator;
import org.agile.dfs.dao.IBatisTransactionFactory;
import org.agile.dfs.name.service.NodeInstaller;
import org.agile.dfs.util.ServiceFactory;

public class BaseNameNodeTestCase extends TestCase {

    private static NodeInstaller installer = (NodeInstaller) ServiceFactory.findService(NodeInstaller.class);
    protected UuidHexGenerator idGen = (UuidHexGenerator) ServiceFactory.findService(UuidHexGenerator.class);
    protected static final IBatisTransactionFactory transactionFactory = new IBatisTransactionFactory();
    static {
        installer.install();
    }

    protected void setUp() throws Exception {
        super.setUp();

    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testDemo() {

    }
}
