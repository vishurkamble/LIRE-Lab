package br.com.antoniogabriel.lirelab.collection;

import com.google.inject.Inject;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.*;

public class CollectionsMonitor {

    private PathResolver resolver;

    private List<Runnable> listeners = new ArrayList<>();

    @Inject
    public CollectionsMonitor(PathResolver resolver) {
        this.resolver = resolver;
    }

    public void addListener(Runnable listener) {
        listeners.add(listener);
    }

    public void bindListenersTo(CreateCollectionTask task) {
        task.setOnSucceeded(event -> executeListeners());
    }

    private void executeListeners() {
        for (Runnable listener : listeners) {
            listener.run();
        }
    }

    public boolean hasListener(Runnable aListener) {
        return listeners.contains(aListener);
    }

    public void startMonitoringCollectionsDeleteAndUpdate() throws IOException {
        Path path = Paths.get(resolver.getCollectionsPath());

        // We obtain the file system of the Path
        FileSystem fs = path.getFileSystem();

        WatchService watcher = fs.newWatchService();

        // We register the path to the watcher
        // We watch for creation events
        path.register(watcher, ENTRY_DELETE, ENTRY_MODIFY);

        Runnable loop = () -> {

                // Start the infinite polling loop
                WatchKey key = null;

                while (true) {
                    try {
                        key = watcher.take();
                    } catch (InterruptedException e) {
                        continue;
                    }

                    // Dequeueing events
                    WatchEvent.Kind<?> kind = null;
                    for (WatchEvent<?> watchEvent : key.pollEvents()) {
                        // Get the type of the event
                        kind = watchEvent.kind();
                        if (OVERFLOW == kind) {
                            continue; //loop
                        } else if (ENTRY_DELETE == kind || ENTRY_MODIFY == kind) {

                            CollectionsMonitor.this.executeListeners();

//                        // A new Path was created
//                        Path newPath = ((WatchEvent<Path>) watchEvent).context();
//                        // Output
//                        System.out.println("New path created: " + newPath);
                        }
                    }

                    if (!key.reset()) {
                        break; //loop
                    }
                }
        };

        Thread t = new Thread(loop);
//            t.setDaemon(true);
        t.start();


    }
}
