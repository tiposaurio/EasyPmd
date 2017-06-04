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
package info.gianlucacosta.easypmd.pmdscanner;

import info.gianlucacosta.easypmd.ide.options.Options;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

/**
 * Scans files using PMD, returning a list of ScanMessage for each scanned file
 */
public class PmdScanner {

    private final PmdScannerStrategy strategy;

    public PmdScanner(Options options) {
        if (options.getRuleSets().isEmpty()) {
            strategy = new NoOpPmdScannerStrategy();
            return;
        }

        if (options.isUseScanMessagesCache()) {
            strategy = new CacheBasedLinkedPmdScanningStrategy(options);
        } else {
            strategy = new LinkedPmdScanningStrategy(options);
        }
    }

    public List<ScanMessage> scan(Path path) {
        try {
            return strategy.scan(path);
        } catch (RuntimeException ex) {
            ScanError scanError = new ScanError(ex);

            return Collections.singletonList(
                    scanError
            );
        }
    }
}
