package data.data_process;


import eureka.formater.CsvHolder;

public interface DataProcessor {

    default void processCsv(CsvHolder data, DataProcessor next){
        return;
    }
}
