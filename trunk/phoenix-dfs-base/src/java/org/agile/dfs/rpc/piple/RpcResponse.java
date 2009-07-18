package org.agile.dfs.rpc.piple;

// service example ( all in one line)  
// <o><c>org.agile.SomeBean</c><f>nsId,string:some</f></o>  
public class RpcResponse {
    public static final String STATUS_SUCCESS = "sucess";
    public static final String STATUS_EXCEPTION = "exception";
    private String status;
    private Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSucess() {
        return STATUS_SUCCESS.equals(status);
    }

}
