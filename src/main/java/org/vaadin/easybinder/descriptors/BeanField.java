package org.vaadin.easybinder.descriptors;

import com.google.common.base.Strings;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class BeanField
        implements Comparable<BeanField> {

    @Getter
    private final Class<?> beanClass;

    @Getter
    private final String propertyName;
    @Getter
    private final Class<?> propertyType;
    // @Getter
    // @Setter
    // private I18nKey i18nKey;

    public BeanField(Class<?> beanClass, String propertyName, Class<?> propertyType) {
        this.beanClass = beanClass;
        this.propertyName = propertyName;
        this.propertyType = propertyType;
    }

    @Override
    public int compareTo(BeanField bean) {
        return this.propertyName.compareTo(bean.propertyName);
    }

    public boolean is(String property) {
        return this.propertyName.equals(property);
    }

    public boolean isNested() {
        return !Strings.isNullOrEmpty(this.propertyName) && this.propertyName.contains(".");
    }

    // public Class<?> getNestedType() {
    // if (!isNested())
    // return this.type;
    // else
    // return ClassUtils.findNestedField(this.bean, this.propertyName).getType();
    // }

    protected void defaultValues() {
        // if (this.i18nKey == null)
        // this.i18nKey = I18nHelper.getKey(this.bean, this.propertyName);
    }

}
