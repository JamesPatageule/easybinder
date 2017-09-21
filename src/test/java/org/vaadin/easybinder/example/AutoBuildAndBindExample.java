package org.vaadin.easybinder.example;

import org.vaadin.addonhelpers.AbstractTest;
import org.vaadin.easybinder.AutoBinder;
import org.vaadin.easybinder.testentity.Person;

import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;

public class AutoBuildAndBindExample
        extends AbstractTest {
    private static final long serialVersionUID = 1L;

    @Override
    public Component getTestComponent() {
        AutoBinder<Person> binder = new AutoBinder<>(Person.class);

        FormLayout f = new FormLayout();
        f.addComponents(binder.buildAndBind());

        binder.setBean(new Person());

        return f;
    }
}
