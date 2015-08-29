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
import org.concordion.internal.Row;
import org.concordion.internal.Table;
import org.concordion.internal.util.Check;

import java.util.HashMap;
import java.util.Map;

/**
 * Sets the command expression to the map created from all rows in the table element.  Keys and values are taken from
 * columns which are defined by columnAs attribute.  Possible values of columnAs attribute are 'key' or 'value'.
 *
 * @author <a href="mailto:chiknrice@gmail.com">Ian Bondoc</a>
 */
public class SetMapCommand extends AbstractSetCommand {

    @Override
    public void setUp(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
        Check.isFalse(commandCall.hasChildCommands(), "Nesting commands inside a 'setMap' is not supported");
        Check.isTrue(commandCall.getElement().getLocalName().equals("table"),
                "'setMap' command can only be used on <table> element");

        Table table = new Table(commandCall.getElement());
        Row header = table.getLastHeaderRow();
        int columnCount = header.getCells().length;
        int keyColumnIndex = -1;
        int valueColumnIndex = -1;
        for (Element thCell : header.getCells()) {
            String columnAs = thCell.getAttributeValue("columnAs", SetVarCommandExtension.NAMESPACE);
            if (columnAs != null) {
                switch (columnAs) {
                    case "key":
                        keyColumnIndex = header.getIndexOfCell(thCell);
                        break;
                    case "value":
                        valueColumnIndex = header.getIndexOfCell(thCell);
                        break;
                    default:
                        throw new RuntimeException("Unsupported columnAs attribute value " + columnAs);
                }
            }
        }
        if (keyColumnIndex < 0 || valueColumnIndex < 0) {
            throw new RuntimeException(
                    "Invalid configuration, 'setMap' should define which columns represent the key and value using 'columnAs' attribute");
        }

        Map<String, String> map = new HashMap<>();
        Row[] detailRows = table.getDetailRows();
        for (Row detailRow : detailRows) {
            Element[] cells = detailRow.getCells();
            if (cells.length != columnCount) {
                throw new RuntimeException(
                        "The <table> 'setMap' command only supports rows with an equal number of columns.");
            }
            tryToExpand(cells[keyColumnIndex], evaluator);
            tryToExpand(cells[valueColumnIndex], evaluator);
            map.put(cells[keyColumnIndex].getText(), cells[valueColumnIndex].getText());
        }
        evaluator.setVariable(commandCall.getExpression(), map);
        announceSetCompleted(commandCall.getElement(), commandCall.getExpression());
    }

}