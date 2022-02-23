package com.skg.hms.framework.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import static com.skg.hms.framework.utils.ObjectUtils.isNotEmpty;

public class MapperUtils {
	static public ModelMapper getModelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}
	
	static public <S, D> D convertData(S sourceData, Class<D> destClass) {
		return convertData(getModelMapper(), sourceData, destClass);
	}
	
	static public <S, D> D convertData(ModelMapper mapper, S sourceData, Class<D> destClass) {
		return isNotEmpty(sourceData) ? mapper.map(sourceData, destClass) : null;
	}
	
	static public <S, D> List<D> convertDataList(List<S> sourceDataList, Class<D> destClass) {
		return convertDataList(getModelMapper(), sourceDataList, destClass);
	}
	
	static public <S, D> List<D> convertDataList(ModelMapper mapper, List<S> sourceDataList, Class<D> destClass) {
		return isNotEmpty(sourceDataList) ? sourceDataList.stream().map(sourceData -> mapper.map(sourceData, destClass)).collect(Collectors.toList()) : null;
	}
}
