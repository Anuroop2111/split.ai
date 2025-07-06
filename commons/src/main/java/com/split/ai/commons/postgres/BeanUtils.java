package com.split.ai.commons.postgres;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class BeanUtils {

    static Map<String, Object> toMap(Object bean) {
        BeanWrapper wrapper = new BeanWrapperImpl(bean);
        Map<String, Object> map = new HashMap<>();
        for (java.beans.PropertyDescriptor pd : wrapper.getPropertyDescriptors()) {
            String name = pd.getName();
            if (!"class".equals(name)) {
                map.put(name, wrapper.getPropertyValue(name));
            }
        }
        return map;
    }
}
