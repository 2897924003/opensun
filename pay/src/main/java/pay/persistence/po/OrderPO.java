package pay.persistence.po;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "`order`", schema = "test-zerostart")
public class OrderPO {
    @Id
    @Size(max = 36)
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Size(max = 36)
    @NotNull
    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;

    @Size(max = 20)
    @NotNull
    @ColumnDefault("'PENDING'")
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @NotNull
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Size(max = 10)
    @NotNull
    @ColumnDefault("'RMB'")
    @Column(name = "currency", nullable = false, length = 10)
    private String currency;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_time")
    private Instant createdTime;

    @Column(name = "completed_time")
    private Instant completedTime;

    @Size(max = 255)
    @Column(name = "payment_link")
    private String paymentLink;

    @Lob
    @Column(name = "shipping_address")
    private String shippingAddress;

    @Size(max = 20)
    @ColumnDefault("'PENDING'")
    @Column(name = "payment_status", length = 20)
    private String paymentStatus;

    @Size(max = 20)
    @ColumnDefault("'PENDING'")
    @Column(name = "shipping_status", length = 20)
    private String shippingStatus;

}