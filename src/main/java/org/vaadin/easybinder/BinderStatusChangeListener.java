package org.vaadin.easybinder;

import com.vaadin.event.SerializableEventListener;

@FunctionalInterface
public interface BinderStatusChangeListener extends SerializableEventListener {
    void statusChange(BinderStatusChangeEvent event);
}
