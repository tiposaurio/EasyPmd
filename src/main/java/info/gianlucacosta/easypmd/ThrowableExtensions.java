/*
 * ==========================================================================%%#
 * EasyPmd
 * ===========================================================================%%
 * Copyright (C) 2009 - 2016 Gianluca Costa
 * ===========================================================================%%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * ==========================================================================%##
 */
package info.gianlucacosta.easypmd;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Throwable-related utilities
 */
public class ThrowableExtensions {

    private ThrowableExtensions() {
        //Just do nothing
    }

    /**
     * Converts the stack trace of a Throwable to a string
     *
     * @param throwable The subject throwable
     * @return the string representation of the stack trace
     */
    public static String getStackTraceString(Throwable throwable) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);

        throwable.printStackTrace(printWriter);

        return result.toString();
    }

    /**
     * Returns the throwable message, or the message of its cause, recursively,
     * or a simple message if no message was found in the chain
     *
     * @param throwable The subject throwable
     * @return a non-empty string
     */
    public static String getNonEmptyMessage(Throwable throwable) {
        String message = throwable.getMessage();

        if (message == null || message.isEmpty()) {
            Throwable cause = throwable.getCause();

            if (cause != null) {
                return getNonEmptyMessage(throwable);
            } else {
                return "(no message)";
            }
        }

        return message;
    }
}
