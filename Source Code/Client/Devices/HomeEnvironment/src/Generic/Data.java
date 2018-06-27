package Generic;

import java.util.ArrayList;
import java.util.Arrays;

public class Data extends ArrayList<String> {
    private static String separator = ";";

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String part : this)
            sb.append(part).append(separator);
        return sb.toString();
    }

    public void addData(String data) {
        if(data.isEmpty()) return;
        this.addAll(Arrays.asList(data.split(separator)));
    }
}
