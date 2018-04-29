import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Params extends HashMap<String, String> {
    private static String separator_pairs = ";";
    private static String separator_kv = "=";

    public void addData(String data) throws Exception {
        // key=value;key=value
        if(data.isEmpty()) return;
        List<String> pairs = Arrays.asList(data.split(separator_pairs));
        for (String pair : pairs) {
            String[] x = pair.split(separator_kv);
            if (x.length != 2)
                throw new Exception(String.format("Pair '%s' is invalid.", pair));
            try {
                this.put(x[0], x[1]);
            } catch (Exception e) {
                throw new Exception(String.format("Key '%s' already exist.", x[0]));
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String,String> entry: this.entrySet())
            sb.append(entry.getKey()).append(separator_kv).append(entry.getValue()).append(separator_pairs);
        return sb.toString();
    }
}
