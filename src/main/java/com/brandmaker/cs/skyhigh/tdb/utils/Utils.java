package com.brandmaker.cs.skyhigh.tdb.utils;

import com.google.common.base.Defaults;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Utils class
 */
public class Utils {

    public static final SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");

    public static final SimpleDateFormat dateFormatFile = new SimpleDateFormat("yyyyMMddHHmmss");

    public static final SimpleDateFormat logDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");

    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    private static final Log LOG = LogFactory.getLog(Utils.class);

    /**
     * Parse object from string
     *
     * @param <T>   type of object
     * @param s     string value to parse
     * @param clazz class of object type
     * @param row
     * @return parsed value of T type
     */
    public static <T> ParseObjectMessage<T> parseObjectFromString(String s, Class<T> clazz, Integer row, Integer orderId, Boolean required) {

        try {
            String fieldValue = s.trim();

            if (required && fieldValue.equals("")) {
                LOG.error("PARSING ERROR: REQUIRED FIELD MISSING");
                return new ParseObjectMessage<T>(false, Defaults.defaultValue(clazz));
            }

            if (orderId == 4 || orderId == 5) {
                if (!checkCrossChargesDate(fieldValue, orderId)) {
                    return new ParseObjectMessage<T>(false, Defaults.defaultValue(clazz));
                }
            }

            T value = clazz.getConstructor(new Class[]{String.class}).newInstance(fieldValue);

            return new ParseObjectMessage<T>(true, value);
        } catch (Exception e) {
            LOG.error("PARSING ERROR: " + String.format("Data %s could not be parsed! Invalid format. Row %s", s.trim(), row));
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
     * Check if field has a proper date format and if accounting_date is not older than 28 days
     * @param inputDate date String to check
     * @param fieldIndex accounting_date = 4
     * @return
     */
    private static boolean checkCrossChargesDate(String inputDate, Integer fieldIndex) {

        boolean validDate;

            try {
                Date fieldDate = inputFormat.parse(inputDate);
                boolean allCoolDate = true;
                if (fieldIndex == 4) {
                    Date pastDate1 = inputFormat.parse(inputFormat.format(DateUtils.addDays(new Date(), -28)));
                    if (fieldDate.before(pastDate1)) {
                        LOG.error("CHECK DATE ERROR: DATE OLDER THAN 28 DAYS");
                        allCoolDate = false;
                    }
                }

                validDate = allCoolDate;

            } catch(Exception e) {
                LOG.error("CHECK DATE ERROR: " + e.getMessage());
                validDate = false;
            }

        return validDate;
    }

    /**
     * Check if a CSV file have enough and properly named columns
     * @param data first/header row in a file
     * @return boolean
     */
    public static boolean checkCrossChargesFileFormat(String[] data) {

        boolean allGood = true;

        if (data.length < 12) {
            allGood = false;
            LOG.error("FILE FORMAT ERROR: FILE MISSING COLUMN");
        } else {
            int err = 0;
            List<String> fieldNames = Arrays.asList("name_of_charge", "description", "amount", "currency", "accounting_date", "transaction_date", "accounting_transaction_id", "category_code", "cost_centre_proj_id", "bk_rc_gl", "cross_charge_state", "element_id");
            for (String cn : data) {
                if (!fieldNames.contains(cn)) {
                    err++;
                    LOG.error("FILE FORMAT ERROR: NOT VALID COLUMN NAME: " + cn);
                }
            }
            if (err != 0) {
                allGood = false;
            }
        }

        return allGood;
    }
}
