/*
 * ==========================================================================%%#
 * EasyPmd
 * ===========================================================================%%
 * Copyright (C) 2009 - 2017 Gianluca Costa
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
package info.gianlucacosta.easypmd.ide.annotations;

import org.openide.text.Annotation;

/**
 * Basic annotation subclass that can be instantiated
 */
public class BasicAnnotation extends Annotation {

    private final String annotationType;
    private final String message;

    public BasicAnnotation(String annotationType, String message) {
        this.annotationType = annotationType;
        this.message = message;
    }

    @Override
    public String getAnnotationType() {
        return annotationType;
    }

    @Override
    public String getShortDescription() {
        return message;
    }
}
