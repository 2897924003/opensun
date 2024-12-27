package article.inbound.ro;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

/**
 * 前端需要分页查询元数据的响应
 *
 * @param <E> 数据类型
 */
@Data
public class PageMetaRO<E> {
    private Long totalElements; // 总数据条数
    private int totalPages;    // 总页数
    private int currentPage;   // 当前页
    private int pageSize;      // 每页数据条数
    private List<E> elements;        // 当前页的数据

    public PageMetaRO() {
    }


    public static <E> PageMetaRO<E> accept(Page<E> page) {
        PageMetaRO<E> ro = new PageMetaRO<>();
        ro.setTotalElements(page.getTotalElements());
        ro.setTotalPages(page.getTotalPages());
        ro.setCurrentPage(page.getNumber() + 1);
        ro.setPageSize(page.getSize());
        ro.setElements(page.getContent().stream().toList());
        return ro;
    }

    /**
     * 接收来自spring data 的page中的elements并且转换成前端所需响应对象
     *
     * @param page      spring分页对象
     * @param converter 转化过程
     * @param <E>       原始响应对象
     * @param <DTO>     转化后，适应前端需求的响应对象
     */
    public static <E, DTO> PageMetaRO<DTO> getElementsAndConvert(Page<E> page, Function<E, DTO> converter) {
        PageMetaRO<DTO> ro = new PageMetaRO<>();
        ro.setTotalElements(page.getTotalElements());
        ro.setTotalPages(page.getTotalPages());
        ro.setCurrentPage(page.getNumber());
        ro.setPageSize(page.getSize());
        ro.setElements(page.getContent().stream().map(converter).toList());
        return ro;
    }

    /**
     * 接收来自list并且转换成前端所需响应对象
     *
     * @param list      list对象
     * @param converter 转化过程
     * @param <E>       原始响应对象
     * @param <DTO>     转化后，适应前端需求的响应对象
     */
    public static <E, DTO> PageMetaRO<DTO> getListElementsAndConvert(List<E> list, Function<E, DTO> converter) {
        PageMetaRO<DTO> ro = new PageMetaRO<>();
        ro.setPageSize(list.size());
        ro.setElements(list.stream().map(converter).toList());
        return ro;
    }

}
