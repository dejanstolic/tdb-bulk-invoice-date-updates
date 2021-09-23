package com.brandmaker.cs.skyhigh.tdb.utils;

import com.brandmaker.cs.skyhigh.tdb.collection.CustomDataCollection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Raw Data Field Annotation
 * <p>
 * used for defining info for automatic CSV parse in CustomDataCollection {@link CustomDataCollection}
 * <p>
 * order - order of field in CSV file
 * required - does field need to have value
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RawDataFieldAnnotation {
    public int order() default 0;
    public boolean required() default false;
}