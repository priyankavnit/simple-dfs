package org.agile.dfs.name;

import junit.framework.TestCase;

import org.agile.dfs.core.common.UuidHexGenerator;
import org.agile.dfs.core.factory.ServiceFactory;
import org.agile.dfs.name.setup.NameInstaller;

public class BaseNameNodeTestCase extends TestCase {

    private static NameInstaller installer = (NameInstaller) ServiceFactory.findService(NameInstaller.class);
    protected UuidHexGenerator idGen = (UuidHexGenerator) ServiceFactory.findService(UuidHexGenerator.class);

    static {
        installer.install();
    }

    protected void setUp() throws Exception {
        super.setUp();

    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
