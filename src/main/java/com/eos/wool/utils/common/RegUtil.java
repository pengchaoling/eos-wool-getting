package com.eos.wool.utils.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegUtil {
    protected static Logger log                    = LoggerFactory.getLogger(RegUtil.class);
    public static final String PATTEM_DATE            = "yyyy-MM-dd";
    public static final String PATTEM_TIME            = "HH:mm:ss";
    public static final String PATTEM_DATE_TIME       = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTEM_TIMESTAMP       = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String STRING_TOKEN_COMMA     = ",";
    public static final String STRING_TOKEN_EQUAL     = "=";
    public static final String STRING_TOKEN_AMPERSAND = "&";
    public static       String CHARSET_ISO            = "ISO-8859-1";
    public static       String CHARSET_UTF8           = "UTF-8";
    public static       String CHARSET_GBK            = "GBK";
    public static       String CHARSET_GB2312         = "GB2312";
    public static       String BIG_LONG_ZERO          = "000000000000000000000000000000000000";

    private static Class[] classTypeList = {String.class, Integer.class, Integer.TYPE, Short.class, Short.TYPE, Long.class, Long.TYPE, Double.class,
                                            Double.TYPE, Float.class, Float.TYPE, Byte.class, Byte.TYPE, BigDecimal.class, Date.class};

    public static String round(double value, int scale) {
        try {
            if (value == 0.0D) {
                return "0." + BIG_LONG_ZERO.substring(0, scale);
            }
            String f = "#";
            if (scale >= 1) {
                f = f + "." + BIG_LONG_ZERO.substring(0, scale);
            }
            String rs = new DecimalFormat(f).format(value);
            if (rs.startsWith("."))
                ;
            return "0" + rs;
        } catch (Exception e) {
            log.error("round:" + value + "," + scale, e);
        }
        return "0.0";
    }

    public static Long getLong(String value) {
        Long rs = null;
        try {
            if (isNotEmptyString(value))
                rs = Long.valueOf(value);
        } catch (Exception e) {
            log.error("e:{}", e);
        }
        return rs;
    }

    public static Integer getInteger(String value) {
        Integer rs = null;
        try {
            if (isNotEmptyString(value))
                rs = Integer.valueOf(value.split("\\.")[0]);
        } catch (Exception e) {
            log.error("e:{}", e);
        }
        return rs;
    }

    public static Short getShort(String value) {
        Short rs = null;
        try {
            if (isNotEmptyString(value))
                rs = Short.valueOf(value);
        } catch (Exception e) {
            log.error("e:{}", e);
        }
        return rs;
    }

    public static Double getDouble(String value) {
        Double rs = null;
        try {
            if (isNotEmptyString(value))
                rs = Double.valueOf(value);
        } catch (Exception e) {
            log.error("e:{}", e);
        }
        return rs;
    }

    public static Float getFloat(String value) {
        Float rs = null;
        try {
            if (isNotEmptyString(value))
                rs = Float.valueOf(value);
        } catch (Exception e) {
            log.error("e:{}", e);
        }
        return rs;
    }

    public static BigDecimal getBigDecimal(String value) {
        BigDecimal rs = null;
        try {
            if (isNotEmptyString(value))
                rs = new BigDecimal(value);
        } catch (Exception e) {
            log.error("e:{}", e);
        }
        return rs;
    }

    public static Byte getByte(String value) {
        Byte rs = null;
        try {
            if (isNotEmptyString(value))
                rs = Byte.valueOf(value);
        } catch (Exception e) {
            log.error("e:{}", e);
        }
        return rs;
    }

    public static String getSQLObjectStr(Object obj) {
        if (obj == null) {
            return "null";
        }
        if ((obj instanceof Date)) {
            return "'" + DateUtil.formatDate((Date) obj) + "'";
        }
        if (String.class.equals(obj.getClass())) {
            String rs = "'" + obj.toString().replaceAll("\\\\", "\\\\\\\\").replaceAll("'", "\\\\'") + "'";
            return rs;
        }
        return obj.toString();
    }

    public static boolean isPlainClass(Class clazz) {
        if (clazz == null)
            return false;
        for (Class cl : classTypeList) {
            if (cl.equals(clazz))
                return true;
        }
        return false;
    }

    public static Object convertValue(Class clazz, String value) {
        Object rs = null;
        if ((clazz != null) && (value != null)) {
            if (String.class.equals(clazz))
                rs = value;
            else if ((Integer.class.equals(clazz)) || (Integer.TYPE.equals(clazz)))
                rs = getInteger(value);
            else if ((Short.class.equals(clazz)) || (Short.TYPE.equals(clazz)))
                rs = getShort(value);
            else if ((Long.class.equals(clazz)) || (Long.TYPE.equals(clazz)))
                rs = getLong(value);
            else if ((Double.class.equals(clazz)) || (Double.TYPE.equals(clazz)))
                rs = getDouble(value);
            else if ((Float.class.equals(clazz)) || (Float.TYPE.equals(clazz)))
                rs = getFloat(value);
            else if (BigDecimal.class.equals(clazz))
                rs = getBigDecimal(value);
            else if ((Byte.class.equals(clazz)) || (Byte.TYPE.equals(clazz)))
                rs = getByte(value);
            else if (Date.class.equals(clazz)) {
                if (value.indexOf(".") != -1)
                    rs = DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss.SSS");
                else if (value.indexOf(":") != -1)
                    rs = DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
                else if (value.indexOf("-") != -1)
                    rs = DateUtil.getDate(value, "yyyy-MM-dd");
                else {
                    rs = DateUtil.getDate(value, "yyyyMMddHHmmss");
                }
            }
        }

        return rs;
    }

    public static String getString(Object obj) {
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

    public static String getNotNullStr(Object obj) {
        if (obj != null) {
            return obj.toString();
        }
        return "";
    }

    public static void setProperty(Object bean, String name, Object value) {
        if ((bean != null) && (name != null) && (value != null)) {
            Class    clazz   = bean.getClass();
            Method[] methods = clazz.getMethods();
            if (methods != null)
                for (Method method : methods)
                    if ((method.getName().startsWith("set"))
                            && (name.toLowerCase().replaceAll("_", "").equals(method.getName().substring(3).toLowerCase().replaceAll("_", "")))) {
                        Class[] types = method.getParameterTypes();
                        if ((types != null) && (types.length > 0)) {
                            Object val = convertValue(types[0], String.valueOf(value));
                            try {
                                method.invoke(bean, new Object[]{val});
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
        }
    }

    public static <T>  T mapToObject(Map<String,Object> map ,Class<T> clazz){
        if(map == null || map.size() == 0){
            return null;
        }
        Object obj = null;
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if(obj == null){
            return null;
        }
        Set<String> keys = map.keySet();
        for (String key : keys) {
            Object value = map.get(key);
            if (value != null) {
                RegUtil.setProperty(obj, String.valueOf(key), value);
            }
        }
        return (T) obj;
    }

    public static Object getProperty(Object bean, String name) {
        if ((bean != null) && (name != null)) {
            Object   rs      = null;
            Class    clazz   = bean.getClass();
            Method[] methods = clazz.getMethods();
            if (methods != null) {
                for (Method method : methods) {
                    if ((method.getName().startsWith("get")) && (name.toLowerCase().equals(method.getName().substring(3).toLowerCase())))
                        try {
                            rs = method.invoke(bean, new Object[0]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }
            }
            return rs;
        }
        return null;
    }

    public static String string2Json(String s) {
        if (s == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '\'':
                    sb.append("\\'");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    public static boolean testRegExp(String exp, String content) {
        Pattern p = Pattern.compile(exp, 2);
        Matcher m = p.matcher(content);
        return m.matches();
    }

    public static boolean isEmptyString(String value) {
        return (value == null) || ("".equals(value.trim()));
    }

    public static boolean isNotEmptyString(String value) {
        return (value != null) && (!"".equals(value.trim()));
    }

    public static String replaceXLAccountWithStar(String name) {
        String rs   = "";
        String star = "*******";
        if (name != null) {
            if (name.length() >= 6) {
                int index = (name.length() - 4) / 2;
                rs = name.substring(0, index) + "****" + name.substring(index + 4);
            } else if (name.length() >= 2) {
                rs = name.substring(0, 1) + star.substring(0, name.length() - 2) + name.substring(name.length() - 1);
            } else {
                rs = name + "****";
            }
        }
        return rs;
    }

    public static String getValueFromUrl(String urlStr, String key, String token1, String token2, boolean needUrlDecode) {
        try {
            String rs = getValueFromUrl(urlStr, key, token1, token2);
            if ((needUrlDecode) && (isNotEmptyString(rs)))
                return URLDecoder.decode(rs, CHARSET_UTF8);
        } catch (Exception e) {
            log.error("getValueFromUrl:", e);
        }
        return "";
    }

    public static String getValueFromUrl(String urlStr, String key, String token1, String token2) {
        if (isNotEmptyString(urlStr)) {
            String[] arr = urlStr.split(token1);
            if ((arr != null) && (arr.length > 0)) {
                for (String val : arr) {
                    String[] arr2 = val.split(token2);
                    if ((arr2 != null) && (arr2.length == 2) && (key.equals(arr2[0]))) {
                        return arr2[1];
                    }
                }
            }
        }

        return "";
    }

    public static String getValueFromUrl(String urlStr, String key) {
        if (isNotEmptyString(urlStr)) {
            String[] arr = urlStr.split("&");
            if ((arr != null) && (arr.length > 0)) {
                for (String val : arr) {
                    String[] arr2 = val.split("=");
                    if ((arr2 != null) && (arr2.length == 2) && (key.equals(arr2[0]))) {
                        return arr2[1];
                    }
                }
            }
        }

        return "";
    }

    public static String getValueFromUrl(String urlStr, String key, boolean needUrlDecode) {
        try {
            String rs = getValueFromUrl(urlStr, key);
            if ((needUrlDecode) && (isNotEmptyString(rs)))
                return URLDecoder.decode(rs, CHARSET_UTF8);
        } catch (Exception e) {
            log.error("getValueFromUrl:", e);
        }
        return "";
    }

    public static int random(int min, int max) {
        int m = (int) (Math.random() * max - min + 1 + min);
        return m;
    }

    public static double random(double min, double max) {
        double m = Math.random() * (max - min) + min;
        return m;
    }

    public static int randomDraw(List<Double> rateList) {
        if ((rateList == null) || (rateList.size() < 1))
            return -1;

        List<Double> list = new ArrayList<Double>();
        double       sum  = 0.0D;
        for (Double dd : rateList) {
            sum += dd.doubleValue();
        }
        for (Double ddd : rateList) {
            list.add(Double.valueOf(ddd.doubleValue() / sum));
        }

        List<Double> nums = new ArrayList<Double>();
        double       lest = 0.0D;
        for (Double data : list) {
            double tmp = data.doubleValue();
            lest += tmp;
            nums.add(new Double(lest));
        }
        Double rtmp = Double.valueOf(Math.random());
        nums.add(new Double(rtmp.doubleValue()));
        Collections.sort(nums);

        if (nums.indexOf(rtmp) >= list.size()) {
            double min = 1.7976931348623157E+308D;
            for (Double d : list) {
                if (d.doubleValue() < min)
                    min = d.doubleValue();
            }
            return list.indexOf(Double.valueOf(min));
        }
        return nums.indexOf(rtmp);
    }

    public static int randomDraw(Double[] rateArr) {
        if ((rateArr == null) || (rateArr.length < 1))
            return -1;
        List<Double> list          = new ArrayList<Double>();
        Double[]     arrayOfDouble = rateArr;
        int          j             = rateArr.length;
        for (int i = 0; i < j; i++) {
            Double double1 = arrayOfDouble[i];
            list.add(double1);
        }
        return randomDraw(list);
    }

    public static String getSubString(String content, int subLen) {
        String r = "";
        if (isNotEmptyString(content)) {
            int l = content.length() >= subLen ? subLen : content.length();
            r = content.substring(0, l);
        }
        return r;
    }

    public static String mapToUrlParam(Map<String, Object> map) {
        return mapToUrlParam(map, "UTF-8");
    }

    public static String mapToUrlParam(Map<String, Object> map, String charset) {
        StringBuilder sb = new StringBuilder();
        try {
            int i = 0;
            for (String key : map.keySet()) {
                if (i > 0)
                    sb.append("&");
                sb.append(key).append("=").append(URLEncoder.encode(map.get(key).toString(), charset));
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String replaceUrlParamVal(String url, String param, String value) {
        if (isEmptyString(url))
            return "";
        if (isEmptyString(param))
            return url;
        if (value == null)
            value = "";

        int bondIndex = url.indexOf("#");
        if (bondIndex >= 0) {
            url = url.substring(0, bondIndex);
        }
        int    qindex   = url.indexOf("?");
        String url1     = "";
        String queryStr = "";
        if (qindex >= 0) {
            url1 = url.substring(0, qindex);
            queryStr = url.substring(qindex + 1);
        } else {
            url1 = url;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        if (isNotEmptyString(queryStr)) {
            String[] arr = queryStr.split("&");
            for (String val : arr) {
                String[] arr2 = val.split("=");
                if (arr2.length == 1)
                    map.put(arr2[0], "");
                else if (arr2.length == 2) {
                    map.put(arr2[0], arr2[1]);
                }
            }
        }
        map.put(param, value);
        String newQuery = mapToUrlParam(map);
        return url1 + "?" + newQuery;
    }

    public static String getHashTable(String table, long flag, int n) {
        if ((flag < 0L) || (n <= 0)) {
            return table;
        }

        long suffix = flag % n;

        int currentSuffixLength  = Long.toString(suffix).length();
        int completeSuffixLength = Integer.toString(n).length();
        if ((Integer.parseInt(Integer.toString(n).substring(0, 1)) == 1) && (Integer.parseInt(Integer.toString(n).substring(1)) == 0)) {
            completeSuffixLength--;
        }

        int lackSuffixLength = completeSuffixLength - currentSuffixLength;

        StringBuilder sb = new StringBuilder().append(table).append("_");
        for (int i = 0; i < lackSuffixLength; i++)
            sb.append(0);
        sb.append(suffix);

        return sb.toString();
    }

    public static String getHashTable(String table, long flag) {
        return getHashTable(table, flag, 100);
    }

    public static void main(String[] args) {
        Integer i   = Integer.valueOf(1);
        int     i1  = 1;
        String  str = "2014-02-02";
        System.out.println(DateUtil.formatDate((Date) convertValue(Date.class, str), "yyyy-MM-dd HH:mm:ss.SSS"));
    }
}