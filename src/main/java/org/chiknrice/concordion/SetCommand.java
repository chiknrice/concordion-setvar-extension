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
import static org.chiknrice.concordion.Const.SET;

/**
 * Sets the command expression to the evaluated value of the expression specified in the eval command or if the eval
 * command does not exist, set the value to the text content of the element.  After evaluation, an optional templating
 * is available via the template and placeholder commands.
 *
 * @author <a href="mailto:chiknrice@gmail.com">Ian Bondoc</a>
 */
public class SetCommand extends AbstractSetCommand {

    @Override
    public void setUp(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
        Check.isFalse(commandCall.hasChildCommands(), format("Nesting commands inside a '%s' is not supported", SET));
        Element element = commandCall.getElement();
        Object value = eval(element, evaluator);
        if (value == null) {
            value = element.getText();
        }
        value = template(element, evaluator, value);
        evaluator.setVariable(commandCall.getExpression(), value);
    }
}
