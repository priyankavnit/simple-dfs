package org.agile.dfs.rpc.piple;

import org.agile.dfs.rpc.exception.RpcSerializeException;
import org.agile.dfs.rpc.serialize.RpcDeSerializer;
import org.agile.dfs.rpc.serialize.RpcSerializer;
import org.agile.dfs.rpc.serialize.SerializerFactory;
import org.agile.dfs.rpc.util.ArrayHelper;

public class RpcCallHelper {
    private static final RpcSerializer serializer = SerializerFactory.instance().getRpcSerializer();

    private static final RpcDeSerializer deserializer = SerializerFactory.instance().getRpcDeSerializer();

    private static final RpcCallHelper _instance = new RpcCallHelper();

    private RpcCallHelper() {

    }

    public static RpcCallHelper instance() {
        return _instance;
    }

    // request example ( all in one line)
    // <s>
    // <i>org.agile.dfs.core.service.Hello</i>
    // <m>hello</m>
    // <as>
    // <a>
    // <o><c>string</c><v>agile</v></o>
    // </a>
    // </as>
    // </s>
    public String marshall(RpcRequest req) {
        // TODO l2, use CharWriter
        StringBuilder sb = new StringBuilder(250);
        sb.append("<s>");
        sb.append("<i>").append(req.getInterfaceClz()).append("</i>");
        sb.append("<m>").append(req.getMethodName()).append("</m>");
        Object[] args = req.getArgs();
        if (args != null && args.length != 0) {
            sb.append("<as>");
            for (int i = 0, l = args.length; i < l; i++) {
                sb.append("<a>");
                sb.append(serializer.write(args[i]));
                sb.append("</a>");
            }
            sb.append("</as>");
        }
        sb.append("</s>");
        return sb.toString();
    }

    // response example ( all in one line)
    // <r>
    // <s>success</s>
    // <o><c>string</c><v>agile</v></o>
    // <m>disk space is fully! </m> (error msg only)
    // </r>
    public String marshall(RpcResponse resp) {
        // TODO l2, use CharWriter
        StringBuilder sb = new StringBuilder(250);
        sb.append("<r>");
        sb.append("<s>").append("success").append("</s>");
        sb.append(serializer.write(resp.getResult()));
        sb.append("</r>");
        return sb.toString();
    }

    public RpcResponse buildResponse(String str) {
        int sb = 6;
        int se = str.indexOf("</s>", 6);
        String status = str.substring(sb, se);
        if ("success".equals(status)) {
            int rb = se + 4;
            int re = str.indexOf("</r>", rb);
            String body = str.substring(rb, re);
            Object result = deserializer.read(body);
            RpcResponse resp = new RpcResponse();
            resp.setResult(result);
            return resp;
        } else {
            int mb = se + 4 + 3;
            int me = str.indexOf("</m>", mb);
            String msg = str.substring(mb, me);
            throw new RpcSerializeException(msg);
        }
    }

    // request example ( all in one line)
    // <s>
    // <i>org.agile.dfs.core.service.Hello</i>
    // <m>hello</m>
    // <as>
    // <a>
    // <o><c>string</c><v>agile</v></o>
    // </a>
    // </as>
    // </s>
    public RpcRequest buildRequest(String body) {
        RpcRequest service = new RpcRequest();
        // parser interface
        int ib = 6;
        int ie = body.indexOf("</i>", ib);
        service.setInterfaceClz(body.substring(ib, ie));
        // parser method
        int mb = ie + 4 + 3;
        int me = body.indexOf("</m>", mb);
        service.setMethodName(body.substring(mb, me));
        // parser args
        ArrayHelper<Object> args = new ArrayHelper<Object>();
        int asb = body.indexOf("<as>", me + 4);
        if (asb > 0) {
            int ab = body.indexOf("<a>", asb + 4);
            while (ab > 0) {
                ab += 3;
                int ae = body.indexOf("</a>", ab);
                String astr = body.substring(ab, ae);
                Object arg = deserializer.read(astr);
                args.add(arg);
                ab = body.indexOf("<a>", ae + 4);
            }
        }
        service.setArgs(args.array(Object.class));
        return service;
    }

    public void serialize(RpcRequest rpcRequest) {
        try {
            // return _parser(body);
        } catch (Exception e) {
            // throw new RpcSerializeException("Can't parser service for " + result, e);
        }

    }

    public RpcRequest deserialize(String body) {
        try {
            return _parser(body);
        } catch (Exception e) {
            throw new RpcSerializeException("Can't parser service for " + body, e);
        }

    }

    // body example: (in fact, all in one line)
    // <s>
    // <i>org.agile.dfs.core.BlockWriter</i>
    // <m>write</m>
    // <as>
    // <a>
    // <o><c>org.agile.SomeBean</c><f>nsId,string:some</f></o>
    // </a>
    // </as>
    // <s>
    private RpcRequest _parser(String body) {
        RpcRequest service = new RpcRequest();
        // parser interface
        int ib = 6;
        int ie = body.indexOf("</i>", ib);
        service.setInterfaceClz(body.substring(ib, ie));
        // parser method
        int mb = ie + 4 + 3;
        int me = body.indexOf("</m>", mb);
        service.setMethodName(body.substring(mb, me));
        // parser args
        ArrayHelper<Object> args = new ArrayHelper<Object>();
        int asb = body.indexOf("<as>", me + 4);
        if (asb > 0) {
            int ab = body.indexOf("<a>", asb + 4);
            while (ab > 0) {
                ab += 3;
                int ae = body.indexOf("</a>", ab);
                String astr = body.substring(ab, ae);
                Object arg = deserializer.read(astr);
                args.add(arg);
                ab = body.indexOf("<a>", ae + 4);
            }
        }
        service.setArgs(args.array(Object.class));
        return service;
    }
}
