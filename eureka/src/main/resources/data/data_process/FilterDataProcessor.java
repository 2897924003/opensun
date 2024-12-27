package data.data_process;


import eureka.formater.CsvHolder;
import org.springframework.stereotype.Service;

@Service
public class FilterDataProcessor implements DataProcessor {


    @Override
    public void processCsv(CsvHolder data, DataProcessor next) {

        data.cleanCsv();
    }
}
