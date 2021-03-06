/*
 * Copyright 2017 Lars Sønderby Jessen Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */
package org.vaadin.easybinder;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.shared.util.SharedUtil;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextField;

public class ComponentFactoryRegistry {
    private static ComponentFactoryRegistry instance;

    protected Logger log = Logger.getLogger(getClass().getName());

    protected Map<Class<?>, List<Pair<Predicate<Field>, Function<Field, Component>>>> builders = new HashMap<>();

    protected ComponentFactoryRegistry() {
        addBuildPattern(String.class, e -> true, e -> new TextField(SharedUtil.camelCaseToHumanFriendly(e.getName())));
        addBuildPattern(Character.class, e -> true,
                e -> new TextField(SharedUtil.camelCaseToHumanFriendly(e.getName())));

        addBuildPattern(Number.class, e -> true, e -> new TextField(SharedUtil.camelCaseToHumanFriendly(e.getName())));

        addBuildPattern(int.class, e -> true, e -> new TextField(SharedUtil.camelCaseToHumanFriendly(e.getName())));
        addBuildPattern(long.class, e -> true, e -> new TextField(SharedUtil.camelCaseToHumanFriendly(e.getName())));
        addBuildPattern(float.class, e -> true, e -> new TextField(SharedUtil.camelCaseToHumanFriendly(e.getName())));

        addBuildPattern(Date.class, e -> true,
                // e -> Arrays.asList(e.getAnnotations()).stream().filter(f -> f instanceof Temporal)
                // .map(f -> (Temporal) f).filter(f -> f.value() == TemporalType.TIMESTAMP).findAny().isPresent(),
                e -> new DateTimeField(SharedUtil.camelCaseToHumanFriendly(e.getName())));

        addBuildPattern(LocalDate.class, e -> true,
                e -> new DateField(SharedUtil.camelCaseToHumanFriendly(e.getName())));
        addBuildPattern(LocalDateTime.class, e -> true,
                e -> new DateTimeField(SharedUtil.camelCaseToHumanFriendly(e.getName())));
        addBuildPattern(ZonedDateTime.class, e -> true,
                e -> new DateTimeField(SharedUtil.camelCaseToHumanFriendly(e.getName())));

        addBuildPattern(Boolean.class, e -> true,
                e -> new CheckBox(SharedUtil.camelCaseToHumanFriendly(e.getName()), false));
        addBuildPattern(boolean.class, e -> true,
                e -> new CheckBox(SharedUtil.camelCaseToHumanFriendly(e.getName()), false));
    }

    public static ComponentFactoryRegistry getInstance() {
        if (instance == null)
            instance = new ComponentFactoryRegistry();

        return instance;
    }

    public void addBuildPattern(Class<?> propertyType, Predicate<Field> matcher,
            Function<Field, Component> componentFactory) {
        List<Pair<Predicate<Field>, Function<Field, Component>>> lst = builders.get(propertyType);
        if (lst == null) {
            lst = new LinkedList<>();
            builders.put(propertyType, lst);
        }
        lst.add(new Pair<>(matcher, componentFactory));
    }

    public Optional<Component> createComponent(Field field) {

        Optional<Component> cpt = null;

        for (Entry<Class<?>, List<Pair<Predicate<Field>, Function<Field, Component>>>> builder : builders.entrySet())
            if (builder.getKey().isAssignableFrom(field.getType())) {
                Optional<Pair<Predicate<Field>, Function<Field, Component>>> o = builder.getValue().stream()
                        .filter(e -> e.getFirst().test(field)).findFirst();
                if (o.isPresent()) {
                    cpt = Optional.ofNullable(o.get().getSecond().apply(field));
                    break;
                }
            }

        if (cpt == null)
            if (Enum.class.isAssignableFrom(field.getType()))
                cpt = Optional.of(createGenericEnumComponent(field));
            else {
                log.log(Level.INFO, "No build rule for field=<{0}> with type=<{1}>",
                        new Object[] { field, field.getType() });
                cpt = Optional.empty();
            }

        return cpt;

        // if (!builders.containsKey(field.getType())) {
        // if (Enum.class.isAssignableFrom(field.getType())) {
        // return Optional.of(createGenericEnumComponent(field));
        // }
        // log.log(Level.INFO, "No build rule for field=<{0}> with type=<{1}>",
        // new Object[] { field, field.getType() });
        // return Optional.empty();
        // }
        //
        // return Optional.ofNullable(builders.get(field.getType()).stream().filter(e -> e.getFirst().test(field))
        // .findFirst().get().getSecond().apply(field));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected Component createGenericEnumComponent(Field field) {
        return new RadioButtonGroup(SharedUtil.camelCaseToHumanFriendly(field.getName()),
                Arrays.asList(field.getType().getEnumConstants()));
    }
}
