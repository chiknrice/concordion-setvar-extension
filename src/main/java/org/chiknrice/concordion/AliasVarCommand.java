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
import org.concordion.api.Evaluator;
import org.concordion.api.ResultRecorder;
import org.concordion.internal.util.Check;

/**
 * Sets the command expression to the value of the expression specified in the body of the span element.
 *
 * @author <a href="mailto:chiknrice@gmail.com">Ian Bondoc</a>
 */
public class AliasVarCommand extends AbstractSetCommand {

    @Override
    public void setUp(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
        Check.isFalse(commandCall.hasChildCommands(), "Nesting commands inside a 'alias' is not supported");
        Check.isTrue(commandCall.getElement().getLocalName().equals("span"),
                "'alias' command can only be used on <span> element");
        String varName = commandCall.getElement().getAttributeValue("var", SetVarCommandExtension.NAMESPACE);
        Check.isTrue(varName != null,
                "'alias' command requires 'var' command to specify an existing concordion variable");
        evaluator.setVariable(commandCall.getExpression(), evaluator.getVariable(varName));
        announceSetCompleted(commandCall.getElement(), commandCall.getExpression());
    }
}
