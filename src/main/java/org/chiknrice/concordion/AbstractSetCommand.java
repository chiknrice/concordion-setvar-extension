/*
 * Copyright (c) 2014 Ian Bondoc
 *
 * This file is part of concordion-setvar-extension
 *
 * concordion-setvar-extension is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation, either version 3 of the License, or(at your option) any later version.
 *
 * concordion-setvar-extension is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 *
 */
package org.chiknrice.concordion;

import org.concordion.api.*;
import org.concordion.internal.util.Check;

import static java.lang.String.format;
import static org.chiknrice.concordion.Const.*;

/**
 * Defines the common methods for all set command.
 *
 * @author <a href="mailto:chiknrice@gmail.com">Ian Bondoc</a>
 */
public abstract class AbstractSetCommand extends AbstractCommand {

    @Override
    public abstract void setUp(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder);

    protected Object eval(Element e, Evaluator evaluator) {
        Object result = null;
        String expression = e.getAttributeValue(EVAL, NAMESPACE);
        if (expression != null) {
            result = evaluator.evaluate(expression);
        }
        return result;
    }

    protected Object template(Element e, Evaluator evaluator, Object value) {
        String template = e.getAttributeValue(TEMPLATE, NAMESPACE);
        if (template != null) {
            template = (String) evaluator.evaluate(template);
            String placeholder = e.getAttributeValue(PLACEHOLDER, NAMESPACE);
            Check.isTrue(placeholder != null,
                    format("'%s' command requires '%s' command to specify an existing concordion variable", TEMPLATE,
                            PLACEHOLDER));

            value = template.replaceAll(placeholder, value.toString());
        }
        return value;
    }

    protected void tryToExpand(Element e, Evaluator evaluator) {
        String expression = e.getAttributeValue(EVAL, NAMESPACE);
        if (expression != null) {
            Object result = eval(e, evaluator);
            if (result != null) {
                e.appendText(result.toString());
                e.addStyleClass("cr-eval");
            } else {
                Element child = new Element("em");
                child.appendText("null");
                e.appendChild(child);
            }
            e.addAttribute("title", expression);
        }
    }
}
