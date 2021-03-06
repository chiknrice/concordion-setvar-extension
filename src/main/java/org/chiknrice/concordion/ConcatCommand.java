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

import org.concordion.api.CommandCall;
import org.concordion.api.Element;
import org.concordion.api.Evaluator;
import org.concordion.api.ResultRecorder;
import org.concordion.internal.util.Check;

import static java.lang.String.format;
import static org.chiknrice.concordion.Const.CONCAT;

/**
 * Sets the command expression to the concatenated contents of every span element within the parent span element.
 *
 * @author <a href="mailto:chiknrice@gmail.com">Ian Bondoc</a>
 */
public class ConcatCommand extends AbstractSetCommand {

    @Override
    public void setUp(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
        Check.isFalse(commandCall.hasChildCommands(), format("Nesting commands inside a '%s' is not supported", CONCAT));
        Check.isTrue(commandCall.getElement().getLocalName().equals("span"),
                format("'%s' command can only be used on <span> element", CONCAT));
        Element[] children = commandCall.getElement().getChildElements("span");
        StringBuilder sb = new StringBuilder();
        for (Element span : children) {
            tryToExpand(span, evaluator);
            sb.append(span.getText());
        }
        evaluator.setVariable(commandCall.getExpression(), sb.toString());
    }

}
