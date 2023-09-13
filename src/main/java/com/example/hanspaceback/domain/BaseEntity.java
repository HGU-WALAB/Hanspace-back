package com.example.hanspaceback.domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // BaseEntity를 상속하는 엔티티들은 아래 필드들을 컬럼으로 인식하게 된다.
@EntityListeners(AuditingEntityListener.class) // Auditing (자동으로 값 매핑)
public class BaseEntity {
    @CreatedDate
    private LocalDateTime legDate;

    @LastModifiedDate
    private LocalDateTime modDate;

//    private boolean deleted = Boolean.FALSE;
}
