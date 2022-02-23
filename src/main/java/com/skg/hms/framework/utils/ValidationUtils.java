package com.skg.hms.framework.utils;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import com.skg.hms.framework.dto.ErrorFieldDto;
import com.skg.hms.framework.exception.DataBindingException;

import static com.skg.hms.framework.utils.MapperUtils.getModelMapper;
import static com.skg.hms.framework.utils.MapperUtils.convertDataList;

public class ValidationUtils {
	static private SpringValidatorAdapter validatorAdapter;
	
	public void setValidatorAdapter(SpringValidatorAdapter adapter) {
		ValidationUtils.validatorAdapter = adapter;
	}

	static public <T> BindingResult checkBindingResult(T sourceData) {
		return checkBindingResult(sourceData, false);
	}
	
	static public <T> BindingResult checkBindingResult(T sourceData, Boolean flag) {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(sourceData, sourceData.getClass().getSimpleName());
		validatorAdapter.validate(sourceData, bindingResult);
		
		if(flag && bindingResult.hasErrors()) {
			throwDataBindingException(bindingResult);
		}
		return bindingResult;
	}
	
	static public void throwDataBindingException(BindingResult bindingResult) {
		ModelMapper modelMapper = getModelMapper();
		modelMapper.addMappings(errorFieldPropertyMapper());
		List<ErrorFieldDto> errorFieldList = convertDataList(modelMapper, bindingResult.getFieldErrors(), ErrorFieldDto.class);
		
		throw new DataBindingException("Binding Exception", errorFieldList);
	}
	
	static private PropertyMap<FieldError, ErrorFieldDto> errorFieldPropertyMapper() {
		return new PropertyMap<FieldError, ErrorFieldDto>() {
			@Override
			protected void configure() {
				map().setField(source.getField());
				map().setValue(String.valueOf(source.getRejectedValue()));
				map().setType(source.getCode());
				map().setReason(source.getDefaultMessage());
			}
		};
	}
}