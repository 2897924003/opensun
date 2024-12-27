package data.writer;

import eureka.data.Csv;
import eureka.formater.CsvHolder;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {

    public void writeCsv(CsvHolder csv) {

        try (BufferedWriter br = new BufferedWriter(new FileWriter(csv.pathOut))) {
            // 写入表头
            List<String> headers = csv.headers;
            if (headers != null && !headers.isEmpty()) {
                br.write(String.join(",", headers));
                br.newLine();
            }

            // 写入数据行
            for (Csv row : csv.csv) {
                br.write(row.toString());
                br.newLine();
            }

            System.out.println("CSV 文件写入完成: " + csv.pathOut);
            br.flush();
        } catch (IOException e) {
            throw new RuntimeException("写入 CSV 文件时发生错误: " + e.getMessage(), e);
        }
    }
}
