package kis.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * Created by jim on 2015/7/18.
 */
public class WebUtils {

    public static final String EMPTY_STR = "";

    public static int strToInt(String p) {
        return strToInt(p, 0);
    }

    public static int strToInt(String p, int defaultValue) {
        return NumberUtils.toInt(p, defaultValue);
    }


    public static BigDecimal getBigDecimalParamValue(HttpServletRequest request, String paramName) {
        String s = request.getParameter(paramName);
        if (StringUtils.isBlank(s)) return null;
        BigDecimal v = new BigDecimal(s);
        return v;
    }

    public static int getIntParamValue(HttpServletRequest request, String paramName) {
        return getIntParamValue(request, paramName, 0);
    }

    public static int getIntParamValue(HttpServletRequest request, String paramName, int defaultValue) {
        return strToInt(request.getParameter(paramName), defaultValue);
    }




    public static String nullToBlank(Object obj){
        return obj==null ?  EMPTY_STR : obj.toString();
    }
}
