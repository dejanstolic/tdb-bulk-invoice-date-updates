package com.brandmaker.cs.skyhigh.tdb.collection;

import com.brandmaker.cs.skyhigh.tdb.dto.CrossChargesDto;
import com.brandmaker.cs.skyhigh.tdb.dto.ParseMessageDto;
import com.brandmaker.cs.skyhigh.tdb.utils.Enumerations;
import com.brandmaker.cs.skyhigh.tdb.utils.ParseObjectMessage;
import com.brandmaker.cs.skyhigh.tdb.utils.Utils;
import com.opencsv.bean.CsvBindByPosition;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class CustomDataCollection<T> implements Serializable{

    private static final Log LOG = LogFactory.getLog(CustomDataCollection.class);


    private final Class<T> genericClass;
    CrossChargesDto crossChargesDto = new CrossChargesDto();
    ArrayList<T> arrayList = new ArrayList<T>();

    public CustomDataCollection(Class<T> genericClass) {
        this.genericClass = genericClass;
    }

    /**
     * Get method
     * <p>
     * Get last object from collection
     * <p>
     * @return Object of type T
     */
    public T getLastItem() {
        if (arrayList.size() > 0) return arrayList.get(arrayList.size() - 1);
        return null;
    }

    /**
     * Method for process String[] data to object T
     * For processing data this method use CsvBindByPosition - {@link CsvBindByPosition}
     * <p>
     * First step - create instance of T and get fields
     * <p>
     * Second step - get field order using CsvBindByPosition Annotation
     * <p>
     * Third step - parse string value to type of field - if field is of a right type it set value to property
     * <p>
     * @param data String[] with values
     * @return Boolean - ParseMessageDto object with error messages
     */
    public ParseMessageDto processData(String[] data, String fileName, Integer row){
        ParseMessageDto parseMessage;
        StringBuilder message = new StringBuilder();
        boolean isValid = true;

        T objectData = null;
        try {
            objectData = genericClass.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e) {
            LOG.error(e.getMessage());
        }catch (Exception ex){
            LOG.error("Can not create instance of generic class!!");
        }

        Class<? extends T> clazz = genericClass;
        Field[] fields = clazz.getDeclaredFields();

        if(fields.length == 0) isValid = false;

        for (Field field : fields){
            if(field.isAnnotationPresent(CsvBindByPosition.class)){
                int orderId = field.getAnnotation(CsvBindByPosition.class).position();
                boolean dataTypeValidation = true;

//                if(clazz.isInstance(crossChargesDto)){
//                    dataTypeValidation = Utils.checkSalesPlanDataType(data[0]);
//
//                    if(!dataTypeValidation){
//                        parseMessage = new ParseMessageDto(Enumerations.ProcessOutcomeStatusEnum.FAILED,
//                                "Datatype does not have valid value. File name: " + fileName + ". Row index: " + row + ".");
//                        return parseMessage;
//                    }
//                }


                ParseObjectMessage<?> pom = Utils.parseObjectFromString(data[orderId], field.getType());
                if (pom.isValid())
                {
                    Utils.setFieldValue(objectData, field.getName(), pom.getValue());
                }
                else{
                    // This block try to handle float values which decimal values separated by comma
                    try{
                        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
                        Number number = format.parse(data[orderId]);
                        ParseObjectMessage<?> pomFloat = Utils.parseObjectFromString(number.toString(), field.getType());
                        Utils.setFieldValue(objectData, field.getName(), pomFloat.getValue());
                    }
                    catch (Exception ex){
                        isValid = false;
                        message.append("Field: " + field.getName() + " is not of type " + field.getType() + ".  File name: " + fileName + ". Row index: " + row + ".");
                    }
                }

            }
        }
        if(isValid){
            arrayList.add(objectData);
            parseMessage = new ParseMessageDto(Enumerations.ProcessOutcomeStatusEnum.SUCCESSED, null);
        }else {

            parseMessage = new ParseMessageDto(Enumerations.ProcessOutcomeStatusEnum.FAILED, message.toString().replace(",", ";"));
        }
        return parseMessage;
    }

    public boolean parse(String[] item) {
        return true;
    }

}
