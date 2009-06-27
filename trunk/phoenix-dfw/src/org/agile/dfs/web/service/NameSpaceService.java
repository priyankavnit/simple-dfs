package org.agile.dfs.web.service;

import java.util.List;

import org.agile.dfs.entity.NameSpace;

public interface NameSpaceService {

    public void create(final NameSpace ns);

    public List queryAll();

    public List queryAllForMap();

}