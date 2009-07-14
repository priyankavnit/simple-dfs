package org.agile.dfs.rpc.serialize;

import java.io.InputStream;
import java.io.Reader;

import org.agile.dfs.rpc.exception.RpcSerializeException;

public class ReflectDeSerializer implements RpcDeSerializer {

    // simple object,example
    // <o><c>string</c><v>some</>

    // complex object,example
    // <o><c>org.agile.SomeBean</c><f>nsId,string:some</f></o>

    public Object read(InputStream in) {
        // TODO Auto-generated method stub
        return null;
    }

    public Object read(Reader in) {

        return null;
    }

    public Object read(String s) {
        try {
            return _read(s);
        } catch (ClassNotFoundException e) {
            throw new RpcSerializeException("Can't deserialize " + s, e);
        }
    }

    private Object _read(String s) throws ClassNotFoundException { 
        int cb = 6;
        int ce = s.indexOf("</c>", cb);
        String cstr = s.substring(cb, ce);

        // simple object
        if (cstr.indexOf(".") == -1) {
            if (cstr.equals("null")) {
                return null;
            }
            int vb = ce + 4 + 3;
            int ve = s.indexOf("</v>", vb);
            String val = s.substring(vb, ve);
            return ObjectSimpleBuilder.instance().build(cstr, val);
        }
        // complex object
        ObjectReflectHelper helper = ObjectReflectHelper.instance(cstr);
        Object target = helper.buildObject();
        int fb = s.indexOf("<f>", ce);
        while (fb > 0) {
            int fe = s.indexOf("</f>", fb);
            String fstr = s.substring(fb + 3, fe);
            int fsplit = fstr.indexOf(',');
            String fname = fstr.substring(0, fsplit);
            int vsplit = fstr.indexOf(':', fsplit);
            String ftype = fstr.substring(fsplit + 1, vsplit);
            String fvalue = fstr.substring(vsplit + 1);
            Object obj = ObjectSimpleBuilder.instance().build(ftype, fvalue);
            helper.setValue(target, fname, obj);
            fb = s.indexOf("<f>", fb + 1);
        }
        return target;
    }
}
