package data.data;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HouseCSV implements Csv {
    @JsonProperty("序号") public String id;
    @JsonProperty("城市") public String city;
    @JsonProperty("区域") public String district;
    @JsonProperty("板块") public String section;
    @JsonProperty("环线") public String ringLine;
    @JsonProperty("小区名称") public String communityName;
    @JsonProperty("价格") public String price;
    @JsonProperty("房屋户型") public String houseType;
    @JsonProperty("所在楼层") public String floor;
    @JsonProperty("建筑面积") public String buildingArea;
    @JsonProperty("套内面积") public String usableArea;
    @JsonProperty("房屋朝向") public String orientation;
    @JsonProperty("建筑结构") public String buildingStructure;
    @JsonProperty("装修情况") public String decoration;
    @JsonProperty("梯户比例") public String ratio;
    @JsonProperty("配备电梯") public String hasElevator;
    @JsonProperty("别墅类型") public String villaType;
    @JsonProperty("交易时间") public String transactionTime;
    @JsonProperty("交易权属") public String propertyRight;
    @JsonProperty("上次交易") public String lastTransaction;
    @JsonProperty("房屋用途") public String houseUsage;
    @JsonProperty("房屋年限") public String houseAge;
    @JsonProperty("产权所属") public String propertyOwnership;
    @JsonProperty("抵押信息") public String mortgageInfo;
    @JsonProperty("房屋优势") public String houseAdvantage;
    @JsonProperty("核心卖点") public String coreSellingPoint;
    @JsonProperty("户型介绍") public String houseDescription;
    @JsonProperty("周边配套") public String surroundingFacilities;
    @JsonProperty("交通出行") public String transport;
    @JsonProperty("lon") public String lon;
    @JsonProperty("lat") public String lat;
    @JsonProperty("年份") public String date;


    @Override
    public boolean validate() {

        return true;
    }

    @Override
    public void fill() {

    }

    @Override
    public String toString() {
        return String.join(",",
                escapeCsv(id),
                escapeCsv(city),
                escapeCsv(district),
                escapeCsv(section),
                escapeCsv(ringLine),
                escapeCsv(communityName),
                escapeCsv(price),
                escapeCsv(houseType),
                escapeCsv(floor),
                escapeCsv(buildingArea),
                escapeCsv(usableArea),
                escapeCsv(orientation),
                escapeCsv(buildingStructure),
                escapeCsv(decoration),
                escapeCsv(ratio),
                escapeCsv(hasElevator),
                escapeCsv(villaType),
                escapeCsv(transactionTime),
                escapeCsv(propertyRight),
                escapeCsv(lastTransaction),
                escapeCsv(houseUsage),
                escapeCsv(houseAge),
                escapeCsv(propertyOwnership),
                escapeCsv(mortgageInfo),
                escapeCsv(houseAdvantage),
                escapeCsv(coreSellingPoint),
                escapeCsv(houseDescription),
                escapeCsv(surroundingFacilities),
                escapeCsv(transport),
                escapeCsv(lon),
                escapeCsv(lat),
                escapeCsv(date)
        );
    }

    // 处理 CSV 转义的方法
    private String escapeCsv(String value) {
        if (value == null) {
            return ""; // 空值返回空字符串
        }
        // 如果值包含特殊字符（逗号、引号、换行符等），则加上双引号并转义内部的引号
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            value = value.replace("\"", "\"\"");
            return "\"" + value + "\"";
        }
        return value;
    }

}
