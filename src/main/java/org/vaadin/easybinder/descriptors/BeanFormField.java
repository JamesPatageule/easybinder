package org.vaadin.easybinder.descriptors;

import lombok.Getter;

public class BeanFormField
        extends BeanField {

    @Getter
    private final Integer order;
    @Getter
    private final String layoutId;

    public BeanFormField(Class<?> bean, String propertyName, Class<?> type, Integer order,
            String layout/* , Align align, String i18nPath */) {
        super(bean, propertyName, type);
        this.order = order;
        this.layoutId = layout;
        // if (!Strings.isNullOrEmpty(i18nPath))
        // setI18nKey(I18nHelper.getKey(i18nPath));

        defaultValues();
    }

    @Override
    public int compareTo(BeanField bean) {
        if (bean instanceof BeanFormField) {
            BeanFormField beanF = (BeanFormField) bean;
            if (this.layoutId.equals(beanF.layoutId))
                if (this.order.equals(beanF.order))
                    return super.compareTo(bean);
                else
                    return this.order.compareTo(beanF.order);
            return this.layoutId.compareTo(beanF.layoutId);
        } else
            return super.compareTo(bean);
    }

    @Override
    protected void defaultValues() {
        super.defaultValues();
    }
}
