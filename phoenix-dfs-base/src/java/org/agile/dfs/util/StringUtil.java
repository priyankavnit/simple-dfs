package org.agile.dfs.util;

public class StringUtil {

    public static boolean isBlank(String str) {
        return str == null ? true : str.trim().length() == 0;
    }

    public static boolean startsWith(String s1, String s2) {
        if (s1 == s2) {
            return true;
        } else if (s1 == null || s2 == null) {
            return false;
        }
        return s1.startsWith(s2);
    }

    public static boolean equals(String s1, String s2) {
        return equals(s1, s2, false, false);
    }

    public static boolean equals(String s1, String s2, boolean trim) {
        return equals(s1, s2, trim, false);
    }

    public static boolean equals(String s1, String s2, boolean trim, boolean ignore) {
        if (s1 == s2) {
            return true;
        } else if (s1 != null && s2 != null) {
            if (trim) {
                if (ignore) {
                    return s1.trim().equalsIgnoreCase(s2.trim());
                } else {
                    return s1.trim().equals(s2.trim());
                }
            } else {
                if (ignore) {
                    return s1.equalsIgnoreCase(s2);
                } else {
                    return s1.equals(s2);
                }
            }
        } else {
            return false;
        }
    }

    public static boolean getBoolean(String s) {
        if (s == null) {
            return false;
        } else {
            return Boolean.valueOf(s).booleanValue();
        }
    }

    public static String[] simpleSplit(String src, char sc) {
        return simpleSplit(src, new char[] { sc });
    }

    public static String[] simpleSplit(String src, char[] scs) {
        if (src == null) {
            return null;
        }
        int len = src.length();
        String[] res = new String[8];
        int num = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char c = src.charAt(i);
            boolean f = false;
            for (int n = 0, l = scs.length; n < l; n++) {
                if (c == scs[n]) {
                    f = true;
                    break;
                }
            }
            if (f) {
                if (sb.length() > 0) {
                    if (num > res.length) {
                        // extend array
                        String[] tmp = new String[res.length + res.length / 2];
                        System.arraycopy(res, 0, tmp, 0, num);
                        res = tmp;
                    }
                    res[num++] = sb.toString();
                }
                sb = new StringBuilder();
            } else {
                sb.append(c);
            }
        }
        if (sb.length() > 0) {
            if (num > res.length) {
                // extend array
                String[] tmp = new String[res.length + res.length / 2];
                System.arraycopy(res, 0, tmp, 1, num);
                res = tmp;
            }
            res[num++] = sb.toString();
        }
        String[] ret = new String[num];
        System.arraycopy(res, 0, ret, 0, num);
        return res;
    }

    public static String simpleReplace(String s, String src, String dest) {
        int len = s.length();
        int bf = -1, ef = -1;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (c == '{') {
                bf = i;
                ef = -1;
            } else if (c == '}') {
                if (bf >= 0) {
                    ef = i;
                    String key = sb.toString();
                    if (src.equalsIgnoreCase(key)) {
                        StringBuffer tb = new StringBuffer();
                        return tb.append(s.substring(0, bf)).append(dest).append(s.substring(ef + 1)).toString();
                    }
                } else {
                    bf = -1;
                    ef = -1;
                }
            } else {
                if (bf >= 0) {
                    sb.append(c);
                }
            }
        }
        return s;
    }
}
