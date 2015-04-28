package com.dfc.springmvc.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.dfc.springmvc.pojo.UserInfo;

public class LoginValidator implements Validator {

	@Override
	public boolean supports(Class<?> cls) {
		return cls.equals(UserInfo.class);
	}

	@Override
	public void validate(Object object, Errors errors) {
		// User user = (User) object;
		
		 ValidationUtils.rejectIfEmpty(errors, "userName", "�û�������Ϊ��");
		 
		 ValidationUtils.rejectIfEmpty(errors, "password", "���벻��Ϊ��");
	}

}
