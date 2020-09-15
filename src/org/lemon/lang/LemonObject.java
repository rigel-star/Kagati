package org.lemon.lang;

import java.lang.annotation.Documented;

@Documented
public @interface LemonObject {
	
	static String GUI_CLASS = "type-container";
	static String HELPER_CLASS = "type-helper";
	static String FILTER_CLASS = "type-filter";
	static String DATABASE_CLASS = "type-database";
	
	String type() default HELPER_CLASS;
	
}
