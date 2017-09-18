/*
 * Copyright 2017 Lars Sønderby Jessen Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */
package org.vaadin.easybinder.converters;

import java.text.NumberFormat;
import java.util.Locale;

import com.vaadin.data.ValueContext;
import com.vaadin.data.converter.AbstractStringToNumberConverter;

@SuppressWarnings("serial")
public abstract class AbstractNullStringToNumberConverter<T extends Number>
        extends AbstractStringToNumberConverter<T> {

    protected String nullRepresentation = "";

    /**
     * @param nullRepresentation
     * @param emptyValue
     * @param errorMessage
     */
    protected AbstractNullStringToNumberConverter(final String nullRepresentation, final T emptyValue,
            final String errorMessage) {
        this(emptyValue, errorMessage);
        this.nullRepresentation = nullRepresentation;
    }

    /**
     * @param emptyValue
     * @param errorMessage
     */
    protected AbstractNullStringToNumberConverter(final T emptyValue, final String errorMessage) {
        super(emptyValue, errorMessage);
    }

    /**
     * Returns the format used by {@link #convertToPresentation(Object, ValueContext)} and
     * {@link #convertToModel(String, ValueContext)}.
     *
     * @param locale
     *            The locale to use
     * @return A NumberFormat instance
     */
    @Override
    protected NumberFormat getFormat(Locale locale) {
        if (locale == null)
            locale = Locale.getDefault();
        return NumberFormat.getIntegerInstance(locale);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String convertToPresentation(T value, ValueContext context) {
        return value == null ? nullRepresentation : super.convertToPresentation(value, context);
    }

}