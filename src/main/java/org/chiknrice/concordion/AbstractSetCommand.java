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

import org.concordion.api.*;
import org.concordion.api.listener.SetEvent;
import org.concordion.api.listener.SetListener;
import org.concordion.internal.util.Announcer;

/**
 * @author <a href="mailto:chiknrice@gmail.com">Ian Bondoc</a>
 */
public abstract class AbstractSetCommand extends AbstractCommand {

    private Announcer<SetListener> listeners = Announcer.to(SetListener.class);

    public void addSetListener(SetListener listener) {
        listeners.addListener(listener);
    }

    public void removeSetListener(SetListener listener) {
        listeners.removeListener(listener);
    }

    protected void announceSetCompleted(Element element, String expression) {
        listeners.announce().setCompleted(new SetEvent(element, expression));
    }

    @Override
    public abstract void setUp(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder);

    protected void tryToExpand(Element e, Evaluator evaluator) {
        String expression = e.getAttributeValue("eval", SetVarCommandExtension.NAMESPACE);
        if (expression != null) {
            Object result = evaluator.evaluate(expression);
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
