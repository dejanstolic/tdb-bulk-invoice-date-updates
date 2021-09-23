package com.brandmaker.cs.skyhigh.tdb.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Techincal Data Annotation
 * <p>
 * used for defining technical name for writing in database
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TechincalDataAnnotation {
	 public String technicalName();
}