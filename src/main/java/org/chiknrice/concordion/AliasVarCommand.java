/*
 * Copyright (c) 2014 Ian Bondoc
 *
 * This file is part of concordion-setvar-extension
 *
 * concordion-setvar-extension is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation, either version 3 of the License, or(at your option) any later version.
 *
 * Jen8583 is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
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

/**
 * @author <a href="mailto:chiknrice@gmail.com">Ian Bondoc</a>
 */
public class AliasVarCommand extends AbstractSetCommand {

    @Override
    public void setUp(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
        Check.isFalse(commandCall.hasChildCommands(), "Nesting commands inside a 'alias' is not supported");
        Check.isTrue(commandCall.getElement().getLocalName().equals("span"),
                "'alias' command can only be used on <span> element");
        Check.isTrue(commandCall.getElement().getText() != null,
                "'alias' command requires a text that evaluates to a concordion variable is not supported");
        evaluator.setVariable(commandCall.getExpression(), evaluator.evaluate(commandCall.getElement().getText()));
        Element parentElement = commandCall.getElement().getParentElement();
        parentElement.removeChild(commandCall.getElement());
        announceSetCompleted(commandCall.getElement(), commandCall.getExpression());
    }
}
