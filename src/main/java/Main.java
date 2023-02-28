import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        ParametersBag parameters = new ParametersBag(args);
        String pathDir = parameters.getPathDir();
        long limitFileSize = parameters.getLimitFileSize();

        FileSearchEngine fileSearchEngine = new FileSearchEngine(pathDir, limitFileSize);
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        Map<String, Long> unsortedMap = forkJoinPool.invoke(fileSearchEngine);

        Map<String, Long> sortedMap = unsortedMap.entrySet().stream().
                sorted(Comparator.comparingLong(entry -> -entry.getValue()))
                .collect(Collectors.toMap(Map.Entry<String, Long>::getKey, Map.Entry<String, Long>::getValue,
                        (Long b, Long a) -> {throw  new AssertionError();}, LinkedHashMap::new));

        for (Map.Entry<String, Long> entry : sortedMap.entrySet()) {
            System.out.println(SizeCalculator.getHumanReadableSize(entry.getValue()) + " " + entry.getKey());
        }
    }
}
