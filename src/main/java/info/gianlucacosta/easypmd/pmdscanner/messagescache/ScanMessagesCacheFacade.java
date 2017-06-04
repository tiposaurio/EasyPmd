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
package info.gianlucacosta.easypmd.pmdscanner.messagescache;

import info.gianlucacosta.easypmd.StorageAreaService;
import info.gianlucacosta.easypmd.ide.Injector;
import info.gianlucacosta.easypmd.ide.options.OptionsService;
import info.gianlucacosta.helios.beans.events.TriggerListener;
import org.openide.util.lookup.ServiceProvider;

import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The scan messages cache facade, providing the 2-tier cache
 */
@ServiceProvider(service = ScanMessagesCache.class)
public class ScanMessagesCacheFacade extends AbstractScanMessagesCache {

    private static final Logger logger = Logger.getLogger(AbstractScanMessagesCache.class.getName());
    private final OptionsService optionsService;
    private final InMemoryScanMessagesCache inMemoryCache = new InMemoryScanMessagesCache();
    private final StorageAreaBasedScanMessagesCache onDiskCache;

    public ScanMessagesCacheFacade() {
        optionsService = Injector.lookup(OptionsService.class);

        StorageAreaService storageAreaService = Injector.lookup(StorageAreaService.class);

        onDiskCache = new StorageAreaBasedScanMessagesCache(storageAreaService.getStorageArea());

        optionsService.addOptionsChangedListener(new TriggerListener() {
            @Override
            public void onTriggered() {
                logger.log(Level.INFO, "The options have changed: clearing the cache");
                clear();
            }
        });
    }

    @Override
    protected ScanMessagesCacheItem getItem(Path path) {
        ScanMessagesCacheItem memoryCacheItem = inMemoryCache.getItem(path);

        if (memoryCacheItem != null) {
            return memoryCacheItem;
        }

        ScanMessagesCacheItem storageCacheItem = onDiskCache.getItem(path);

        if (storageCacheItem != null) {
            inMemoryCache.putItem(path, storageCacheItem);

            return storageCacheItem;
        }

        return null;
    }

    @Override
    protected void putItem(Path path, ScanMessagesCacheItem cacheItem) {
        inMemoryCache.putItem(path, cacheItem);
        onDiskCache.putItem(path, cacheItem);
    }

    @Override
    protected boolean doClear() {
        return inMemoryCache.clear() && onDiskCache.clear();
    }
}
