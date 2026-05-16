package org.example.measurementtrackerclean.model;
import jakarta.persistence.*;import lombok.Getter;import lombok.Setter;import java.time.LocalDateTime;
@Entity @Table(name="meters",uniqueConstraints=@UniqueConstraint(columnNames="serialNumber")) @Getter @Setter
public class Meter {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true) private String serialNumber;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private MeterType type;
 private String title;
 @Column(nullable=false) private boolean active=true;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="user_id",nullable=false) private User user;
 @Column(nullable=false) private LocalDateTime createdAt;
 @PrePersist void pre(){createdAt=LocalDateTime.now();}
}
