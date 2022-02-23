package com.skg.hms.service.common.aop;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApiLog {
	String apiId();
	String reqKey();
}
