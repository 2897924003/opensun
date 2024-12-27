package article.infrastructure.utils;

import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class SortUtil {
    //标记接口
    public interface SortableFields {
    }

    /**
     * 创建并校验排序规则
     *
     * @param enumClass  排序字段枚举类型
     * @param properties 排序字段列表
     * @param converter  字段到 Sort.Order 的转换器
     * @param <E>        排序字段枚举类型
     * @return Sort 对象
     */
    public static <E extends Enum<E> & SortableFields> Sort createSort(
            Class<E> enumClass,
            List<String> properties,
            Function<String, Sort.Order> converter
    ) {
        // 获取枚举可排序字段名
        List<String> sortableFields = Arrays.stream(enumClass.getEnumConstants()).map(Enum::name).toList();
        // 过滤无效字段并生成排序规则
        List<Sort.Order> sort = properties.stream()
                .filter(sortableFields::contains)
                .map(converter)
                .toList();

        return Sort.by(sort);
    }


}
