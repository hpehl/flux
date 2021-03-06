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
package org.jboss.gwt.circuit.processor;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

abstract class AbstractGenerator {

    protected final Configuration config;

    protected AbstractGenerator() {
        Version version = new Version(2, 3, 23);
        config = new Configuration(version);
        config.setDefaultEncoding("UTF-8");
        config.setClassForTemplateLoading(getClass(), "templates");
        config.setObjectWrapper(new DefaultObjectWrapperBuilder(version).build());
    }

    protected StringBuffer generate(Map<String, Object> context, String templateName) throws GenerationException {
        final StringWriter sw = new StringWriter();
        final BufferedWriter bw = new BufferedWriter(sw);
        try {
            final Template template = config.getTemplate(templateName);
            // template.complete(contextSupplier.get(), bw);
            template.process(context, bw);
        } catch (IOException | TemplateException ioe) {
            throw new GenerationException("Error generating template " + templateName + ": " + ioe.getMessage());
        } finally {
            try {
                bw.close();
                sw.close();
            } catch (IOException ioe) {
                //noinspection ThrowFromFinallyBlock
                throw new GenerationException("Error generating template " + templateName + ": " + ioe.getMessage());
            }
        }
        return sw.getBuffer();
    }
}
