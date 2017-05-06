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
package info.gianlucacosta.easypmd.ide.options;

import info.gianlucacosta.easypmd.ide.DialogService;
import info.gianlucacosta.easypmd.ide.Injector;
import info.gianlucacosta.helios.swing.dialogs.JarFileChooser;
import info.gianlucacosta.helios.swing.jlist.AdvancedSelectionListModel;
import org.openide.util.Utilities;

import javax.swing.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

/**
 * Panel for managing the "Additional classpath" option
 */
public class AdditionalClasspathPanel extends JPanel {

    private static final JarFileChooser pathChooser = new JarFileChooser("Select path...");
    private final AdvancedSelectionListModel<URL> additionalClasspathModel = new AdvancedSelectionListModel<>();
    private final DialogService dialogService;

    public AdditionalClasspathPanel() {
        initComponents();

        dialogService = Injector.lookup(DialogService.class);

        additionalClasspathList.setModel(additionalClasspathModel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        additionalClasspathScrollPane = new javax.swing.JScrollPane();
        additionalClasspathList = new info.gianlucacosta.helios.swing.jlist.AdvancedSelectionJList();
        buttonsPanel = new javax.swing.JPanel();
        addJarButton = new javax.swing.JButton();
        addCustomUrlButton = new javax.swing.JButton();
        moveUpButton = new javax.swing.JButton();
        moveDownButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        additionalClasspathScrollPane.setViewportView(additionalClasspathList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(additionalClasspathScrollPane, gridBagConstraints);

        buttonsPanel.setLayout(new java.awt.GridBagLayout());

        addJarButton.setText(org.openide.util.NbBundle.getMessage(AdditionalClasspathPanel.class, "AdditionalClasspathPanel.addJarButton.text")); // NOI18N
        addJarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addJarButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
        buttonsPanel.add(addJarButton, gridBagConstraints);

        addCustomUrlButton.setText(org.openide.util.NbBundle.getMessage(AdditionalClasspathPanel.class, "AdditionalClasspathPanel.addCustomUrlButton.text")); // NOI18N
        addCustomUrlButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCustomUrlButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
        buttonsPanel.add(addCustomUrlButton, gridBagConstraints);

        moveUpButton.setText(org.openide.util.NbBundle.getMessage(AdditionalClasspathPanel.class, "AdditionalClasspathPanel.moveUpButton.text")); // NOI18N
        moveUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveUpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
        buttonsPanel.add(moveUpButton, gridBagConstraints);

        moveDownButton.setText(org.openide.util.NbBundle.getMessage(AdditionalClasspathPanel.class, "AdditionalClasspathPanel.moveDownButton.text")); // NOI18N
        moveDownButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveDownButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
        buttonsPanel.add(moveDownButton, gridBagConstraints);

        removeButton.setText(org.openide.util.NbBundle.getMessage(AdditionalClasspathPanel.class, "AdditionalClasspathPanel.removeButton.text")); // NOI18N
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
        buttonsPanel.add(removeButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(buttonsPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void addJarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addJarButtonActionPerformed
        pathChooser.setSelectedFile(new File(""));

        if (pathChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File selectedFile = pathChooser.getSelectedFile();

        final URL selectedJarUrl;
        try {
            selectedJarUrl = Utilities.toURI(selectedFile).toURL();
        } catch (MalformedURLException ex) {
            dialogService.showWarning("Error: invalid file path");
            return;
        }

        additionalClasspathModel.addElement(selectedJarUrl);
    }//GEN-LAST:event_addJarButtonActionPerformed

    private void addCustomUrlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCustomUrlButtonActionPerformed
        String newPathString = dialogService.askForString("Path URL:");

        if (newPathString == null) {
            return;
        }

        newPathString = newPathString.trim();

        final URL newPathUrl;
        try {
            newPathUrl = new URL(newPathString);
        } catch (MalformedURLException ex) {
            dialogService.showWarning("Invalid URL");
            return;
        }

        additionalClasspathModel.addElement(newPathUrl);
    }//GEN-LAST:event_addCustomUrlButtonActionPerformed

    private void moveUpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveUpButtonActionPerformed
        additionalClasspathList.moveUpSelection();
    }//GEN-LAST:event_moveUpButtonActionPerformed

    private void moveDownButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveDownButtonActionPerformed
        additionalClasspathList.moveDownSelection();
    }//GEN-LAST:event_moveDownButtonActionPerformed

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        additionalClasspathList.removeSelection();
    }//GEN-LAST:event_removeButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addCustomUrlButton;
    private javax.swing.JButton addJarButton;
    private info.gianlucacosta.helios.swing.jlist.AdvancedSelectionJList additionalClasspathList;
    private javax.swing.JScrollPane additionalClasspathScrollPane;
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JButton moveDownButton;
    private javax.swing.JButton moveUpButton;
    private javax.swing.JButton removeButton;
    // End of variables declaration//GEN-END:variables

    public Collection<URL> getAdditionalClassPathUrls() {
        return additionalClasspathModel.getItems();
    }

    public void setAdditionalClasspathUrls(Collection<URL> additionalClasspathUrls) {
        additionalClasspathModel.setItems(additionalClasspathUrls);
    }
}
