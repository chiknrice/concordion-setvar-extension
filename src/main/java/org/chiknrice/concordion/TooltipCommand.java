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

import org.concordion.api.AbstractCommand;
import org.concordion.api.CommandCall;
import org.concordion.api.Evaluator;
import org.concordion.api.ResultRecorder;
import org.concordion.internal.util.Check;

import static java.lang.String.format;
import static org.chiknrice.concordion.Const.TOOLTIP;

/**
 * A convenience command which behaves much like the echo command except that it renders the expression as a tooltip of
 * the span element
 *
 * @author <a href="mailto:chiknrice@gmail.com">Ian Bondoc</a>
 */
public class TooltipCommand extends AbstractCommand {

    @Override
    public void verify(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
        Check.isFalse(commandCall.hasChildCommands(), format("Nesting commands inside a '%s' is not supported", TOOLTIP));
        Object result = evaluator.evaluate(commandCall.getExpression());
        String tooltip;
        if (result != null) {
            tooltip = result.toString();
        } else {
            tooltip = commandCall.getExpression() + " = null";
        }
        commandCall.getElement().addAttribute("title", tooltip);
        commandCall.getElement().addStyleClass("cr-tooltip");
    }
}
