package org.agile.dfs.core.common;

import java.io.Serializable;

public class UuidHexGenerator extends AbstractUuidGenerator {

    private String sep = "";

    protected String format(int intval) {
        String formatted = Integer.toHexString(intval);
        StringBuffer buf = new StringBuffer("00000000");
        buf.replace(8 - formatted.length(), 8, formatted);
        return buf.toString();
    }

    protected String format(short shortval) {
        String formatted = Integer.toHexString(shortval);
        StringBuffer buf = new StringBuffer("0000");
        buf.replace(4 - formatted.length(), 4, formatted);
        return buf.toString();
    }

    public String generate() {
        StringBuffer sb = new StringBuffer(36);
        sb.append(getIP());
        sb.append(sep);
        // sb.append(format(getJVM()));
        // sb.append(sep);
        // sb.append(format(getHiTime()));
        // sb.append(sep);
        sb.append(superFormat(System.currentTimeMillis(), 9));
        sb.append(sep);
        sb.append(superFormat(getCount(), 3));
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        UuidHexGenerator gen = new UuidHexGenerator();

        System.out.println("----------------------------------------");
        for (int i = 0; i < 42767; i++) {
            String id = (String) gen.generate();
            System.out.println(id);
        }
        System.out.println("----------------------------------------");
        // System.out.println("ID:" + Long.toString(255255255255L, 32));
        long t1 = System.currentTimeMillis();
        System.out.println("ID:" + Long.toString(t1 >> 2, 32));
        System.out.println("ID:" + Long.toString(t1 << 6, 32));
        System.out.println("ID:" + Long.toString(t1 >> 6, 32));

        // System.out.println("ID:" + Long.toString(System.currentTimeMillis(), 32));
    }

}
