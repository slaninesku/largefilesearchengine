import java.io.File;

public class ParametersBag {

    private final String pathDir;
    private final long limitFileSize;

    public ParametersBag(String[] args) {
        if (args.length != 2){
            throw new IllegalArgumentException("Укажите два параметра: 'путь к папке' 'лимит по объему'");
        }
        pathDir = args[0];
        File dir = new File(pathDir);
        if (!dir.exists() || !dir.isDirectory()){
            throw new IllegalArgumentException("Путь к папке указан неверно или папка не существует!");
        }
        limitFileSize = SizeCalculator.getSizeFromHumanReadable(args[1] + "M");
        if (limitFileSize < 0){
            throw new IllegalArgumentException("Лимит не указан!");
        }
    }
    public String getPathDir(){
        return pathDir;
    }
    public long getLimitFileSize(){
        return limitFileSize;
    }
}