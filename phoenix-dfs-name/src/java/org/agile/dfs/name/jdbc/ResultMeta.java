package org.agile.dfs.name.jdbc;

class ResultMeta {

    private String[] colName;

    public ResultMeta(int col) {
        colName = new String[col + 1];
    }

    public int colSize() {
        return colName == null ? 0 : colName.length - 1;
    }

    public void setColName(int ix, String name) {
        colName[ix] = name;
    }

    public String getColName(int ix) {
        return colName[ix];
    }
}
