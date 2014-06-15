/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.gwt.flux.sample.calculator.calculator;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import org.jboss.gwt.flux.Action;
import org.jboss.gwt.flux.Dispatcher;

/**
 * @author Harald Pehl
 */
public class SequentialDispatcher implements Dispatcher {

    private final List<Function> callbacks;

    public SequentialDispatcher() {callbacks = new LinkedList<>();}

    @Override
    public <P> void register(final Function<Action<P>, Boolean> callback) {
        callbacks.add(callback);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> void dispatch(final Action<T> action) {
        System.out.printf("~-~-~-~-~ Processing %s with payload '%s'\n", action.getClass().getSimpleName(),
                action.getPayload());
        callbacks.forEach(callback -> callback.apply(action));
        System.out.printf("~-~-~-~-~ Finished\n\n");
    }
}