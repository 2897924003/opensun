package data.reader;


import eureka.data.Csv;
import eureka.formater.CsvHolder;

public interface DataReader {
    CsvHolder readCsv(String pathIn, Class<? extends Csv> targetClass);
}

