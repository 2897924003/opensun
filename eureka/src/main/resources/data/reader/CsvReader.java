package data.reader;

import eureka.data.Csv;
import eureka.data.HouseCSV;
import eureka.formater.CsvHolder;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;



public class CsvReader implements DataReader {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public CsvHolder readCsv(String pathIn, Class<? extends Csv> targetClass) {


        try (BufferedReader br = new BufferedReader(new FileReader(pathIn))) {
            // 读取表头
            String[] headers = br.readLine().split(",");

            // 读取数据行并处理
            List<? extends Csv> csv = br.lines()
                    .map(row -> {
                        String[] values = row.split(",");
                        Map<String, String> rowMap = new HashMap<>();
                        for (int i = 0; i < headers.length; i++) {
                            rowMap.put(headers[i], values[i]);
                        }
                        return mapper.convertValue(rowMap, targetClass);
                    })
                    .toList();

            CsvHolder holder = CsvHolder.create(csv);
            holder.headers= List.of(headers);
            holder.setPathIn(pathIn);
            return holder;

        } catch (IOException e) {
            e.printStackTrace();
        }
       return CsvHolder.create(List.of());
    }
}
