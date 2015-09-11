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

import static org.chiknrice.concordion.Const.EVAL;
import static org.chiknrice.concordion.Const.NAMESPACE;

/**
 * Defines the common methods for all set command.
 *
 * @author <a href="mailto:chiknrice@gmail.com">Ian Bondoc</a>
 */
public abstract class AbstractSetCommand extends AbstractCommand {

    @Override
    public abstract void setUp(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder);

    protected void tryToExpand(Element e, Evaluator evaluator) {
        String expression = e.getAttributeValue(EVAL, NAMESPACE);
        if (expression != null) {
            Object result = evaluator.evaluate(expression);
            e.addAttribute("title", expression);
            e.addStyleClass("cr-eval");
            if (result != null) {
                e.appendText(result.toString());
            } else {
                Element child = new Element("em");
                child.appendText("null");
                e.appendChild(child);
            }
        }
    }
}
