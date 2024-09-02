package com.itcast.bigevent.validation;

import com.itcast.bigevent.anno.State;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context){
        if(value == null){
            return false;
        }

        if(value.equals("已发布") || value.equals("草稿")){
            return true;
        }

        return false;
    }
}