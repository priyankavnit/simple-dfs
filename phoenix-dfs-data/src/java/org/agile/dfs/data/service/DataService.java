package org.agile.dfs.data.service;

import java.io.IOException;

public interface DataService {

    public void write(String blockId, int len) throws IOException;

}
