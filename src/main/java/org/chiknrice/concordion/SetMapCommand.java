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

import static java.lang.String.format;
import static org.chiknrice.concordion.Const.*;

/**
 * Sets the command expression to the map created from all rows in the table element.  Keys and values are taken from
 * columns which are defined by columnAs attribute.  Possible values of columnAs attribute are 'key' or 'value'.
 *
 * @author <a href="mailto:chiknrice@gmail.com">Ian Bondoc</a>
 */
public class SetMapCommand extends AbstractSetCommand {

    @Override
    public void setUp(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
        Check.isFalse(commandCall.hasChildCommands(), format("Nesting commands inside a '%s' is not supported", SET_MAP));
        Check.isTrue(commandCall.getElement().getLocalName().equals("table"),
                format("'%s' command can only be used on <table> element", SET_MAP));

        Table table = new Table(commandCall.getElement());
        Row header = table.getLastHeaderRow();
        int columnCount = header.getCells().length;
        int keyColumnIndex = -1;
        int valueColumnIndex = -1;
        for (Element thCell : header.getCells()) {
            String columnAs = thCell.getAttributeValue(COLUMN_AS, NAMESPACE);
            if (columnAs != null) {
                switch (columnAs) {
                    case KEY:
                        keyColumnIndex = header.getIndexOfCell(thCell);
                        break;
                    case VALUE:
                        valueColumnIndex = header.getIndexOfCell(thCell);
                        break;
                    default:
                        throw new RuntimeException(format("Unsupported %s attribute value %s", COLUMN_AS, columnAs));
                }
            }
        }
        if (keyColumnIndex < 0 || valueColumnIndex < 0) {
            throw new RuntimeException(
                    format("Invalid configuration, '%s' should define which columns represent the key and value using '%s' attribute", SET_MAP, COLUMN_AS));
        }

        Map<String, String> map = new HashMap<>();
        Row[] detailRows = table.getDetailRows();
        for (Row detailRow : detailRows) {
            Element[] cells = detailRow.getCells();
            if (cells.length != columnCount) {
                throw new RuntimeException(
                        format("The <table> '%s' command only supports rows with an equal number of columns", SET_MAP));
            }
            tryToExpand(cells[keyColumnIndex], evaluator);
            tryToExpand(cells[valueColumnIndex], evaluator);
            map.put(cells[keyColumnIndex].getText(), cells[valueColumnIndex].getText());
        }
        evaluator.setVariable(commandCall.getExpression(), map);
    }

}