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
package info.gianlucacosta.easypmd.ide.editor;

import org.openide.cookies.LineCookie;
import org.openide.filesystems.FileChangeAdapter;
import org.openide.filesystems.FileChangeListener;
import org.openide.filesystems.FileEvent;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.text.Annotation;
import org.openide.text.Line;
import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Default implementation of AnnotationService
 */
@ServiceProvider(service = AnnotationService.class)
public class DefaultAnnotationService implements AnnotationService {

    private final Map<FileObject, ScanMessageAnnotationList> attachedAnnotations = new HashMap<>();
    private final Map<FileObject, FileChangeListener> registeredFileChangeListeners = new HashMap<>();

    @Override
    public void attachAnnotationsTo(DataObject dataObject, ScanMessageAnnotationList annotations) {
        final FileObject fileObject = dataObject.getPrimaryFile();

        if (attachedAnnotations.containsKey(fileObject)) {
            throw new IllegalArgumentException("EasyPmd annotations have already been attached to this file.\nYou must detach them before attaching new ones.");
        }

        LineCookie lineCookie = dataObject.getLookup().lookup(LineCookie.class);
        Line.Set lineSet = lineCookie.getLineSet();

        for (ScanMessageAnnotation annotation : annotations) {
            annotation.attach(lineSet);
        }

        attachedAnnotations.put(fileObject, annotations);

        registerFileEventHandlers(fileObject);
    }

    private void registerFileEventHandlers(final FileObject fileObject) {
        FileChangeListener fileChangeListener = new FileChangeAdapter() {
            @Override
            public void fileChanged(FileEvent ev) { //This is triggered only when the user SAVES the file
                detachAnnotationsFrom(fileObject);
            }

            @Override
            public void fileDeleted(FileEvent ev) {
                detachAnnotationsFrom(fileObject);
            }
        };

        fileObject.addFileChangeListener(fileChangeListener);
        registeredFileChangeListeners.put(fileObject, fileChangeListener);
    }

    @Override
    public void detachAnnotationsFrom(FileObject fileObject) {
        ScanMessageAnnotationList fileAnnotations = attachedAnnotations.get(fileObject);

        if (fileAnnotations == null) {
            return;
        }

        for (Annotation annotation : fileAnnotations) {
            annotation.detach();
        }

        attachedAnnotations.remove(fileObject);

        unregisterFileEventHandlers(fileObject);
    }

    private void unregisterFileEventHandlers(FileObject fileObject) {
        FileChangeListener fileChangeListener = registeredFileChangeListeners.get(fileObject);

        fileObject.removeFileChangeListener(fileChangeListener);

        registeredFileChangeListeners.remove(fileObject);
    }

    @Override
    public void detachAllAnnotations() {
        Collection<FileObject> fileObjects = new ArrayList<>(attachedAnnotations.keySet());

        for (FileObject fileObject : fileObjects) {
            detachAnnotationsFrom(fileObject);
        }
    }
}
