package backendshop.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class GeneralClass {
    @CreatedDate
    @Column(name = "create_date")
    private Date createDate;
    @LastModifiedDate
    @Column(name = "update_date")
    private Date updateDate;
    @Column(name = "del_flag")
    private boolean deleteFlag = false;
}