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

import org.concordion.api.Resource;
import org.concordion.api.extension.ConcordionExtender;
import org.concordion.api.extension.ConcordionExtension;

import static org.chiknrice.concordion.Const.*;

/**
 * Configures all features of the setvar extension.
 *
 * @author <a href="mailto:chiknrice@gmail.com">Ian Bondoc</a>
 */
public class SetVarCommandExtension implements ConcordionExtension {

    private static final String CSS_SOURCE_PATH = "/setvar.css";
    private static final Resource CSS_TARGET_RESOURCE = new Resource("/stylesheets/setvar.css");

    @Override
    public void addTo(ConcordionExtender concordionExtender) {
        concordionExtender.withCommand(NAMESPACE, SET_MAP, new SetMapCommand());
        concordionExtender.withCommand(NAMESPACE, SET_LIST, new SetListCommand());
        concordionExtender.withCommand(NAMESPACE, SET, new SetEvalCommand());
        concordionExtender.withCommand(NAMESPACE, SET_RESOURCE, new SetResourceCommand());
        concordionExtender.withCommand(NAMESPACE, CONCAT, new ConcatCommand());
        concordionExtender.withCommand(NAMESPACE, TOOLTIP, new TooltipCommand());
        concordionExtender.withLinkedCSS(CSS_SOURCE_PATH, CSS_TARGET_RESOURCE);
    }
}
