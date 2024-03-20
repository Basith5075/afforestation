package com.plant.utilities;

import com.plant.entity.Campaign;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;


public class CommonUtils {

    // Utility method to get the list of properties with null values
    public String[] getNullPropertyNames(Campaign source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> nonNullNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue != null && !isPrimitiveDefault(src.getPropertyType(pd.getName()), srcValue)) {
                nonNullNames.add(pd.getName());
            }
        }
        return nonNullNames.toArray(new String[0]);

    }

    private boolean isPrimitiveDefault(Class<?> type, Object value) {
        if (type == int.class || type == short.class || type == long.class || type == byte.class) {
            return (Integer)value == 0;
        } else if (type == double.class || type == float.class) {
            return (Double)value == 0.0;
        } else if (type == char.class) {
            return (Character)value == '\u0000';
        } else if (type == boolean.class) {
            return !(Boolean)value;
        }
        return false;
    }
}
