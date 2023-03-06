import java.util.HashMap;

public class SizeCalculator {

    private static final char[] sizeMultipliers = {'B', 'K', 'M', 'G', 'T'};
    private static final Map<Character, Integer> char2multiplier = new HashMap<>();

    public static String getHumanReadableSize(long size) {
        for (int i = 0; i < sizeMultipliers.length; i++) {
            double value = size / Math.pow(1024, i);
            if (value < 1024) {
                return Math.round(value * 100) / 100.00 + "" + sizeMultipliers[i] + (i > 0 ? "B" : "");
            }
        }
        return "Very big!";
    }

    public static long getSizeFromHumanReadable(String size) {
        HashMap<Character, Integer> char2multiplier = getMultipliers();
        char sizeFactor = size.replaceAll("[0-9\\s+]+", "").charAt(0);
        int multiplier = char2multiplier.get(sizeFactor);
        return multiplier * Long.valueOf(size.replaceAll("[^0-9]", ""));
    }

    private static HashMap<Character, Integer> getMultipliers() {
        for (int i = 0; i < sizeMultipliers.length; i++) {
            char2multiplier.put(sizeMultipliers[i], (int) Math.pow(1024, i));
        }
        return char2multiplier;
    }
}
