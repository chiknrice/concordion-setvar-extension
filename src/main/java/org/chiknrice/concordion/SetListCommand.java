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

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:chiknrice@gmail.com">Ian Bondoc</a>
 */
public class SetListCommand extends AbstractSetCommand {

    @Override
    public void setUp(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
        if (commandCall.getElement().isNamed("ul")) {

            Element ul = commandCall.getElement();

            List<String> list = new ArrayList<>();

            Element[] liArray = ul.getChildElements("li");
            for (Element li : liArray) {
                tryToExpand(li, evaluator);
                list.add(li.getText());
            }

            evaluator.setVariable(commandCall.getExpression(), list);
        } else {
            throw new RuntimeException("setList can only be used on a table");
        }
        announceSetCompleted(commandCall.getElement(), commandCall.getExpression());
    }

}