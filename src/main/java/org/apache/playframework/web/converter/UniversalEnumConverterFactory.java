package org.apache.playframework.web.converter;

import com.baomidou.mybatisplus.core.enums.IEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

public class UniversalEnumConverterFactory implements ConverterFactory<String, IEnum<String>> {

    @Override
    public <T extends IEnum<String>> Converter<String, T> getConverter(Class<T> targetType) {
        return new IntegerToEnum(targetType);
    }

    private class IntegerToEnum<T extends IEnum<String>> implements Converter<String, T> {

        private final T[] values;

        public IntegerToEnum(Class<T> targetType) {
            values = targetType.getEnumConstants();
        }

        @Override
        public T convert(String source) {
            for (T t : values) {
                String value = t.getValue();
                if (value.equals(source)) {
                    return t;
                }
            }
            return null;
        }
    }
}


