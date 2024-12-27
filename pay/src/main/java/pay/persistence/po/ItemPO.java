package pay.persistence.po;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

//持久化模型-不涉及业务逻辑，专注于与数据库的数据交互
@Getter
@Setter
@Entity
@Table(name = "item", schema = "test-zerostart")
public class ItemPO {
    @Id
    @jakarta.validation.constraints.Size(max = 36)
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @jakarta.validation.constraints.Size(max = 255)
    @jakarta.validation.constraints.NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @jakarta.validation.constraints.Size(max = 10)
    @ColumnDefault("'RMB'")
    @Column(name = "currency", length = 10)
    private String currency;

    @ColumnDefault("0")
    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @jakarta.validation.constraints.Size(max = 36)
    @Column(name = "category_id", length = 36)
    private String categoryId;

    @jakarta.validation.constraints.Size(max = 50)
    @Column(name = "sku", length = 50)
    private String sku;

    @ColumnDefault("'ACTIVE'")
    @Lob
    @Column(name = "status")
    private String status;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_time")
    private Instant createdTime;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_time")
    private Instant updatedTime;

    @jakarta.validation.constraints.Size(max = 500)
    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "attributes")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> attributes;

    @Column(name = "discount_price", precision = 10, scale = 2)
    private BigDecimal discountPrice;

    @Column(name = "start_discount")
    private Instant startDiscount;

    @Column(name = "end_discount")
    private Instant endDiscount;

}