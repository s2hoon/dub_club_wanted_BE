package com.likelion.dub.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class) //이걸 생략하고 orm.xml에 등록하면 전체 적용이가능하다
@MappedSuperclass
public abstract class BaseEntity extends BaseTimeEntity {

    @CreatedBy
    @Column(updatable = false, length = 512)
    private String createdBy;
    @LastModifiedBy
    @Column(length = 512)
    private String lastModifiedBy;
}

