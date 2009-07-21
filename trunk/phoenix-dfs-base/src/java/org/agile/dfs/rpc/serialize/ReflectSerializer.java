package org.agile.dfs.rpc.serialize;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Date;

import org.agile.dfs.rpc.exception.RpcSerializeException;

/**
 * Simple object serialze, not support object diagram.
 * 
 * @author chenbin
 * 
 */

@SuppressWarnings("unchecked")
public class ReflectSerializer implements RpcSerializer {

    private static final char[] clz_name_null = "null".toCharArray();
    private static final char[] clz_name_string = "string".toCharArray();
    private static final char[] clz_name_date = "date".toCharArray();
    private static final char[] obj_b = "<o>".toCharArray();
    private static final char[] obj_e = "</o>".toCharArray();
    private static final char[] clz_b = "<c>".toCharArray();
    private static final char[] clz_e = "</c>".toCharArray();
    private static final char[] smp_val_b = "<v>".toCharArray();
    private static final char[] smp_val_e = "</v>".toCharArray();
    private static final char[] fld_b = "<f>".toCharArray();
    private static final char[] fld_e = "</f>".toCharArray();
    private static final char fld_clz_val_split = ':';

    public void write(Object obj, OutputStream out) {
        Class clz = obj.getClass();
        clz.getName();
    }

    public void write(Object obj, Writer out) throws IOException {
        if (obj == null) {
            out.write(obj_b);
            out.write(clz_b);
            out.write(clz_name_null);
            out.write(clz_e);
            out.write(obj_e);
            return;
        }
        Class clz = obj.getClass();
        if (obj instanceof String) {
            out.write(obj_b);
            out.write(clz_b);
            out.write(clz_name_string);
            out.write(clz_e);
            out.write(smp_val_b);
            out.write(obj.toString());
            out.write(smp_val_e);
            out.write(obj_e);
        } else if (obj instanceof Date) {
            out.write(obj_b);
            out.write(clz_b);
            out.write(clz_name_date);
            out.write(clz_e);
            out.write(smp_val_b);
            out.write(String.valueOf(((Date) obj).getTime()));
            out.write(smp_val_e);
            out.write(obj_e);
        } else {
            String clzName = fineClzName(clz);
            if (clzName.indexOf('.') == -1) {
                out.write(obj_b);
                out.write(clz_b);
                out.write(clzName);
                out.write(clz_e);
                out.write(smp_val_b);
                out.write(obj.toString());
                out.write(smp_val_e);
                out.write(obj_e);
            } else {
                ObjectReflectHelper helper = ObjectReflectHelper.instance(obj.getClass());
                out.write(obj_b);
                out.write(clz_b);
                out.write(helper.getClassNameChars());
                out.write(clz_e);
                Field[] fds = helper.getTotalFields();
                for (int i = 0, l = fds.length; i < l; i++) {
                    Object fval = helper.getValue(obj, fds[i]);
                    if (fval != null) {
                        Class fclz = fds[i].getType();
                        out.write(fld_b);
                        // out.write(fld_name_b);
                        out.write(fds[i].getName());
                        out.write(',');
                        // out.write(fld_name_e);
                        marshall(fclz, fval, out);
                        out.write(fld_e);
                    }
                }
                out.write(obj_e);
            }
        }
    }

    private void marshall(Class type, Object obj, Writer out) throws IOException {
        String clzName = fineClzName(type);
        if (clzName.indexOf('.') == -1) {
            out.write(clzName);
            out.write(fld_clz_val_split);
            out.write(obj.toString());
        } else if (obj instanceof String) {
            out.write(clz_name_string);
            out.write(fld_clz_val_split);
            out.write(obj.toString());
        } else if (obj instanceof Date) {
            out.write(clz_name_date);
            out.write(fld_clz_val_split);
            out.write(Long.toString(((Date) obj).getTime()));
        } else {
            throw new RpcSerializeException("Can't serialize " + type.getName());
        }
    }

    // * @see java.lang.Boolean#TYPE
    // * @see java.lang.Character#TYPE
    // * @see java.lang.Byte#TYPE
    // * @see java.lang.Short#TYPE
    // * @see java.lang.Integer#TYPE
    // * @see java.lang.Long#TYPE
    // * @see java.lang.Float#TYPE
    // * @see java.lang.Double#TYPE
    // * @see java.lang.Void#TYPE
    public String fineClzName(Class clz) {
        if (clz.isPrimitive())
            return clz.getName();
        String clzName = clz.getName();
        if ("java.lang.Boolean".equals(clzName))
            return "boolean";
        else if ("java.lang.Character".equals(clzName))
            return "char";
        else if ("java.lang.Byte".equals(clzName))
            return "byte";
        else if ("java.lang.Short".equals(clzName))
            return "short";
        else if ("java.lang.Integer".equals(clzName))
            return "int";
        else if ("java.lang.Long".equals(clzName))
            return "long";
        else if ("java.lang.Float".equals(clzName))
            return "float";
        else if ("java.lang.Double".equals(clzName))
            return "double";

        return clzName;
    }

    public String write(Object obj) {
        StringWriter sw = new StringWriter(100);
        try {
            this.write(obj, sw);
        } catch (IOException e) {
            return null;
        }
        return sw.toString();
    }
}
