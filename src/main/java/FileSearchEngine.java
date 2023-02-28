import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveTask;

public class FileSearchEngine extends RecursiveTask<ConcurrentHashMap<String, Long>> {

    private final String pathDir;
    private final long limitFileSize;
    private final ArrayList<FileSearchEngine> tasks = new ArrayList<>();
    private final ConcurrentHashMap<String, Long> resultMap = new ConcurrentHashMap<>();

    public FileSearchEngine(String pathDir, long limitFileSize) {
        this.pathDir = pathDir;
        this.limitFileSize = limitFileSize;
    }

    @Override
    protected ConcurrentHashMap<String, Long> compute() {
        File dir = new File(pathDir);
        if (dir.isDirectory()) {
            File[] item = dir.listFiles();
            assert item != null;
            for (File it : item) {
                if (it.isFile()) {
                    if (it.length() > limitFileSize) {
                        resultMap.put(it.getAbsolutePath(), it.length());
                    }
                } else {
                    FileSearchEngine fileSearchEngine = new FileSearchEngine(it.getAbsolutePath(), limitFileSize);
                    tasks.add(fileSearchEngine);
                    fileSearchEngine.fork();
                }
            }
        }
        for (FileSearchEngine task : tasks) {
            task.join();
            resultMap.putAll(task.resultMap);
        }
        return resultMap;
    }
}
