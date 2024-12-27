package data.formater;


import eureka.data.Csv;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvHolder {
    private String pathIn;
    public String pathOut;
    public List<String> headers;
    public List<? extends Csv> csv;
    public Map<String,List<? extends Csv>> badCsv;

    public static CsvHolder create(List<? extends Csv> csv) {
        CsvHolder format = new CsvHolder();
        format.csv = csv;
        return format;
    }

    public void cleanCsv() {
        csv = csv.parallelStream().filter(Csv::validate).collect(Collectors.toList());
    }

    public void setPathIn(String pathIn) {
        this.pathIn = pathIn;

        if (pathIn != null && pathIn.contains(".")) {
            int lastDotIndex = pathIn.lastIndexOf(".");
            this.pathOut = pathIn.substring(0, lastDotIndex) + "-out.csv";
        } else {
            this.pathOut = pathIn + "-out.csv";
        }
    }

}
