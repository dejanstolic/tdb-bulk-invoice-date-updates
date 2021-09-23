package com.brandmaker.cs.skyhigh.tdb.utils;

import com.google.common.base.Defaults;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Utils class
 */
public class Utils {

    public static final SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");

    public static final SimpleDateFormat dateFormatFile = new SimpleDateFormat("yyyyMMddHHmmss");

    public static final SimpleDateFormat logDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    private static final Log LOG = LogFactory.getLog(Utils.class);

    /**
     * Parse object from string
     *
     * @param <T>   type of object
     * @param s     string value to parse
     * @param clazz class of object type
     * @return parsed value of T type
     */
    public static <T> ParseObjectMessage<T> parseObjectFromString(String s, Class<T> clazz) {
        try {
            T value = clazz.getConstructor(new Class[]{String.class}).newInstance(s.trim());
            return new ParseObjectMessage<T>(true, value);
        } catch (Exception e) {
            LOG.error(String.format("Data %s could not be parsed! Invalid format.", s.trim()));
        }

        return new ParseObjectMessage<T>(false, Defaults.defaultValue(clazz));
    }

    /**
     * Set value of field in generic class
     *
     * @param object     class
     * @param fieldName  property name of class
     * @param fieldValue value to be set
     * @return status
     */
    public static boolean setFieldValue(Object object, String fieldName, Object fieldValue) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(object, fieldValue);
                return true;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return false;
    }

    /**
     * Extracting parameters from url
     *
     * @param uriString url string
     * @return parameters in Map
     */
    public static Map<String, String> getParametersFromUriString(String uriString) {
        try {
            String formatedUri = uriString.replaceAll("\"", "");
            URL url = new URL(formatedUri);
            String query = url.getQuery();

            String[] params = query.split("&");
            Map<String, String> map = new HashMap<String, String>();

            for (String param : params) {
                String name = param.split("=")[0];
                String value = param.split("=")[1];
                map.put(name, value);
            }
            return map;
        } catch (MalformedURLException e) {
            LOG.error(e.getMessage());
        }
        return null;
    }

    /**
     * Get month name for given index in BudgetMonthDto
     *
     * @param budgetMonthIndex index in BudgetMonthDto
     * @return Month short name
     */
    public static String getMonthForIndex(Integer budgetMonthIndex){
        switch (budgetMonthIndex){
            case 0: return "May";
            case 1: return "Jun";
            case 2: return "Jul";
            case 3: return "Aug";
            case 4: return "Sep";
            case 5: return "Oct";
            case 6: return "Nov";
            case 7: return "Dec";
            case 8: return "Jan";
            case 9: return "Feb";
            case 10: return "Mar";
            case 11: return "Apr";
        }

        return "";
    }
}
