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
package info.gianlucacosta.easypmd.ide.options;

import net.sourceforge.pmd.RulePriority;

import java.net.URL;
import java.util.Collection;

/**
 * The plugin's options
 */
public interface Options {

    String getTargetJavaVersion();

    String getSourceFileEncoding();

    String getSuppressMarker();

    Collection<URL> getAdditionalClassPathUrls();

    Collection<String> getRuleSets();

    boolean isUseScanMessagesCache();

    boolean isShowRulePriorityInTasks();

    boolean isShowDescriptionInTasks();

    boolean isShowRuleInTasks();

    boolean isShowRuleSetInTasks();

    boolean isShowAnnotationsInEditor();

    boolean isShowAllMessagesInGuardedSections();

    PathFilteringOptions getPathFilteringOptions();

    RulePriority getMinimumPriority();

    String getAuxiliaryClassPath();

    Options clone();
}
