package org.lemon.lang;

import java.lang.annotation.Documented;

@Documented
public @interface Info {

	String author() default "author";
	int date() default 2020;
	int version() default 1;
	
}
