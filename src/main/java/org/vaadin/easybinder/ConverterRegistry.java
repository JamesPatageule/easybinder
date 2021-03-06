/*
 * Copyright 2017 Lars Sønderby Jessen Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */
package org.vaadin.easybinder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.vaadin.easybinder.converters.NullStringToFloatConverter;
import org.vaadin.easybinder.converters.NullStringToIntegerConverter;
import org.vaadin.easybinder.converters.NullStringToLongConverter;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.converter.LocalDateTimeToDateConverter;
import com.vaadin.data.converter.LocalDateToDateConverter;

public class ConverterRegistry {
    static ConverterRegistry instance;

    protected Map<Pair<Class<?>, Class<?>>, Converter<?, ?>> converters = new HashMap<>();

    public static ConverterRegistry getInstance() {
        if (instance == null)
            instance = new ConverterRegistry();
        return instance;
    }

    private ConverterRegistry() {

        registerConverter(String.class, Integer.class, new NullStringToIntegerConverter(0, "Conversion failed"));
        registerConverter(String.class, int.class, new NullStringToIntegerConverter(0, "Conversion failed"));

        registerConverter(String.class, Long.class, new NullStringToLongConverter(0L, "Conversion failed"));
        registerConverter(String.class, long.class, new NullStringToLongConverter(0L, "Conversion failed"));

        registerConverter(String.class, Float.class, new NullStringToFloatConverter(0F, "Conversion failed"));
        registerConverter(String.class, float.class, new NullStringToFloatConverter(0F, "Conversion failed"));

        registerConverter(String.class, Character.class,
                Converter.from(
                        e -> e.length() == 0 ? Result.ok(null)
                                : (e.length() == 1 ? Result.ok(e.charAt(0)) : Result.error("Must be 1 character")),
                        f -> f == null ? "" : "" + f));
        registerConverter(LocalDateTime.class, Date.class, new LocalDateTimeToDateConverter(ZoneId.systemDefault()));
        registerConverter(LocalDate.class, Date.class, new LocalDateToDateConverter());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <PRESENTATIONTYPE, MODELTYPE> Converter<PRESENTATIONTYPE, MODELTYPE> getConverter(
            Class<PRESENTATIONTYPE> presentationType, Class<MODELTYPE> modelType) {
        return (Converter) converters.get(new Pair<>(presentationType, modelType));
    }

    public <PRESENTATIONTYPE, MODELTYPE> void registerConverter(Class<PRESENTATIONTYPE> presentationType,
            Class<MODELTYPE> modelType, Converter<PRESENTATIONTYPE, MODELTYPE> converter) {
        converters.put(new Pair<>(presentationType, modelType), converter);
    }

    public <PRESENTATIONTYPE, MODELTYPE> void unregisterConverter(Class<PRESENTATIONTYPE> presentationType,
            Class<MODELTYPE> modelType) {
        converters.remove(new Pair<>(presentationType, modelType));
    }
}
