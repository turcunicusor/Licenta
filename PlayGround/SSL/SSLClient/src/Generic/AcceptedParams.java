package Generic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AcceptedParams extends HashMap<String, ParamDescription> {
    private static String separator_pairs = ";";
    private static String separator_kv = "=";
    private static String separator_parm_desc = ":";

    public void addData(String data) throws Exception {
        // key=value1|value2;key=value
        this.clear();
        if (data.isEmpty()) return;
        List<String> pairs = Arrays.asList(data.split(separator_pairs));
        for (String pair : pairs) {
            String[] p = pair.split(separator_kv);
            if (p.length != 2)
                throw new Exception(String.format("Pair '%s' is invalid.", pair));

            String[] pd = p[1].split(separator_parm_desc);
            if (pd.length != 2)
                throw new Exception(String.format("Parameter Description '%s' is invalid.", p[1]));
            ParamDescription paramDescription;
            try {
                paramDescription = new ParamDescription(Boolean.parseBoolean(pd[0]), pd[1]);
            } catch (Exception e) {
                throw new Exception("Parameters Description field types is invalid.");
            }
            try {
                this.put(p[0], paramDescription);
            } catch (Exception e) {
                throw new Exception(String.format("Key '%s' already exist.", p[0]));
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, ParamDescription> entry : this.entrySet())
            sb.append(entry.getKey()).append(separator_kv)
                    .append(entry.getValue().isReadOnly())
                    .append(separator_parm_desc)
                    .append(entry.getValue().getType())
                    .append(separator_pairs);
        return sb.toString();
    }
}
