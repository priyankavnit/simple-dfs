package org.agile.dfs.web.service;

import java.util.List;

import org.agile.dfs.entity.NameSpace;

public interface NameSpaceService {

    public abstract void create(final NameSpace ns);

    public abstract List queryAll();

}