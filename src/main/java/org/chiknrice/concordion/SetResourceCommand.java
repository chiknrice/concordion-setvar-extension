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
import org.concordion.internal.util.IOUtil;

import static java.lang.String.format;
import static org.chiknrice.concordion.Const.*;

/**
 * Sets the command expression to the evaluated value of the expression specified in the eval command.
 *
 * @author <a href="mailto:chiknrice@gmail.com">Ian Bondoc</a>
 */
public class SetResourceCommand extends AbstractSetCommand {

    @Override
    public void setUp(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
        Check.isFalse(commandCall.hasChildCommands(),
                format("Nesting commands inside a '%s' is not supported", SET_RESOURCE));
//        Check.isTrue(commandCall.getElement().getLocalName().equals("span"),
//                format("'%s' command can only be used on <span> element", SET_RESOURCE));
        String path = commandCall.getElement().getAttributeValue(PATH, NAMESPACE);
        Check.isTrue(path != null,
                format("'%s' command requires '%s' command to specify an resource in the classpath", SET_RESOURCE, PATH));

        Resource resource = commandCall.getResource().getRelativeResource(path);

        String text = IOUtil.readResourceAsString(resource.getPath());
        evaluator.setVariable(commandCall.getExpression(), text);
        commandCall.getElement().appendText(text);
    }
}
