package com.skg.hms.framework.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Map;

public class ObjectUtils {

	static public boolean isEmpty(Object reqValue) {
		if(reqValue == null) {
			return true;
		}
		if(reqValue instanceof String) {
			return "".equals(reqValue.toString()) || ((String) reqValue).length() == 0 || ((String) reqValue).trim().length() == 0; 
		}
		if(reqValue instanceof Collection) {
			return ((Collection<?>) reqValue).isEmpty();
		}
		if(reqValue instanceof Map) {
			return ((Map<?, ?>) reqValue).isEmpty();
		}
		if(reqValue instanceof Object[]) {
			return ((Object[]) reqValue).length == 0;
		}
		return false;
	}
	
	static public boolean isNotEmpty(Object reqValue) {
		return !isEmpty(reqValue);
	}
	
	@SuppressWarnings("unchecked")
	static public <S> S copyObjectDeep(S reqObj) throws IOException, ClassNotFoundException {
		S resObj;
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(reqObj);
		
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bais);
		
		resObj = (S) ois.readObject();
		
		baos.close();
		oos.close();
		baos.close();
		ois.close();
		
		return resObj;
	}
}
