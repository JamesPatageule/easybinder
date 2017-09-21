package org.vaadin.easybinder.converters;

import java.text.NumberFormat;
import java.util.Locale;

import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;

/**
 * A converter that converts from {@link String} to {@link Long} and back. Uses the given locale and a
 * {@link NumberFormat} instance for formatting and parsing.
 * <p>
 * Override and overwrite {@link #getFormat(Locale)} to use a different format.
 * </p>
 */
public class NullStringToIntegerConverter
        extends AbstractNullStringToNumberConverter<Integer> {

    private static final long serialVersionUID = -9075370779303547110L;

    /**
     * Creates a new converter instance with the given error message. Empty strings are converted to <code>null</code>.
     *
     * @param errorMessage
     *            the error message to use if conversion fails
     */
    public NullStringToIntegerConverter(String errorMessage) {
        this(null, errorMessage);
    }

    /**
     * Creates a new converter instance with the given empty string value and error message.
     *
     * @param emptyValue
     *            the presentation value to return when converting an empty string, may be <code>null</code>
     * @param errorMessage
     *            the error message to use if conversion fails
     */
    public NullStringToIntegerConverter(Integer emptyValue, String errorMessage) {
        super(emptyValue, errorMessage);
    }

    @Override
    public Result<Integer> convertToModel(String value, ValueContext context) {
        Result<Number> n = convertToNumber(value, context.getLocale().orElse(null));
        return n.map(number -> {
            if (number == null) {
                return null;
            } else {
                return number.intValue();
            }
        });
    }

}
