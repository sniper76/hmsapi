package com.skg.hms.framework.config.common;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import com.skg.hms.framework.utils.ProfileUtils;
import com.skg.hms.framework.utils.ValidationUtils;

//@Slf4j
@Configuration
public class BaseConfig {

	@Autowired
	private Environment env;
	
	@Bean("jasyptStringEncryptor")
	public StringEncryptor propertyEncryptor() {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword("9pwc3dke");
		return encryptor;
	}
	
	@Bean
	public ProfileUtils profileUtils() {
		ProfileUtils profileUtils = new ProfileUtils();
		profileUtils.setEnvironment(env);
		return profileUtils;
	}
	
	@Bean
	public ValidationUtils validationUtils() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		ValidationUtils vu = new ValidationUtils();
		vu.setValidatorAdapter(new SpringValidatorAdapter(validator));
		return vu;
	}
}
