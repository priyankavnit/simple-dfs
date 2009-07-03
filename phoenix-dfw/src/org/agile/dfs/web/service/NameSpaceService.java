package org.agile.dfs.web.service;

import java.util.List;

import org.agile.dfs.entity.NameSpace;

public interface NameSpaceService {

    public NameSpace save(final NameSpace ns);

    public List queryAll();

    public List queryAllForMap();

}