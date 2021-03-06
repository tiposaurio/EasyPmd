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
package info.gianlucacosta.easypmd.pmdscanner.strategies;

import info.gianlucacosta.easypmd.pmdscanner.messages.ScanError;
import info.gianlucacosta.easypmd.pmdscanner.messages.ScanViolation;
import info.gianlucacosta.easypmd.ide.Injector;
import info.gianlucacosta.easypmd.ide.options.Options;
import info.gianlucacosta.easypmd.pmdscanner.pmdcatalogs.LanguageVersionParser;
import info.gianlucacosta.easypmd.pmdscanner.PmdScannerStrategy;
import info.gianlucacosta.easypmd.pmdscanner.ScanMessage;
import net.sourceforge.pmd.*;
import net.sourceforge.pmd.lang.LanguageVersion;
import net.sourceforge.pmd.lang.dfa.report.ReportTree;

import java.io.*;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Scans a file using PMD, using the PMD class internally linked with the plugin
 */
public class LinkedPmdScanningStrategy implements PmdScannerStrategy {

    private final PMD pmd;
    private final RuleSets ruleSets;
    private final String sourceFileEncoding;

    public LinkedPmdScanningStrategy(Options options) {
        LanguageVersionParser languageVersionParser = Injector.lookup(LanguageVersionParser.class);

        ClassLoader pmdBasedClassLoader = PmdBasedClassLoader.create(options.getAdditionalClassPathUrls());

        RuleSetFactory ruleSetFactory = new RuleSetFactory();

        String ruleSetsString = buildRuleSetsString(options.getRuleSets());

        try {
            ruleSetFactory.setClassLoader(pmdBasedClassLoader);
            ruleSetFactory.setMinimumPriority(options.getMinimumPriority());

            ruleSets = ruleSetFactory.createRuleSets(ruleSetsString);
        } catch (RuleSetNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        LanguageVersion languageVersion = languageVersionParser.parse(options.getTargetJavaVersion());
        sourceFileEncoding = options.getSourceFileEncoding();

        pmd = new PMD();
        PMDConfiguration pmdConfiguration = pmd.getConfiguration();
        pmdConfiguration.setDefaultLanguageVersion(languageVersion);
        pmdConfiguration.setSuppressMarker(options.getSuppressMarker());
        pmdConfiguration.setClassLoader(pmdBasedClassLoader);
        pmdConfiguration.setMinimumPriority(options.getMinimumPriority());

        String auxiliaryClassPath = options.getAuxiliaryClassPath();

        if (auxiliaryClassPath != null && !auxiliaryClassPath.isEmpty()) {
            try {
                pmdConfiguration.prependClasspath(auxiliaryClassPath);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private String buildRuleSetsString(Collection<String> ruleSets) {
        StringBuilder result = new StringBuilder();

        Iterator<String> iterator = ruleSets.iterator();

        iterator.forEachRemaining(ruleSet -> {
            result.append(ruleSet);

            if (iterator.hasNext()) {
                result.append(",");
            }
        });

        return result.toString();
    }

    @Override
    public Set<ScanMessage> scan(Path path) {
        String pathString = path.toAbsolutePath().toString();

        Report report = new Report();

        RuleContext ruleContext = new RuleContext();
        ruleContext.setReport(report);
        ruleContext.setSourceCodeFilename(pathString);

        Set<ScanMessage> scanMessages = new HashSet<>();

        RuleSets applicableRuleSets = new RuleSets();
        Iterator<RuleSet> ruleSetsIterator = ruleSets.getRuleSetsIterator();

        ruleSetsIterator.forEachRemaining(currentRuleSet -> {
            if (currentRuleSet.applies(path.toFile())) {
                applicableRuleSets.addRuleSet(currentRuleSet);
            }
        });

        try {
            try (Reader reader = new InputStreamReader(new FileInputStream(pathString), sourceFileEncoding)) {
                pmd.getSourceCodeProcessor().processSourceCode(reader, applicableRuleSets, ruleContext);
            }

            ReportTree violationTree = report.getViolationTree();

            Iterator<RuleViolation> violationsIterator = violationTree.iterator();

            violationsIterator.forEachRemaining(violation -> {
                scanMessages.add(new ScanViolation(violation));
            });
        } catch (IOException | PMDException ex) {
            scanMessages.add(new ScanError(ex));
        }

        return scanMessages;
    }
}
