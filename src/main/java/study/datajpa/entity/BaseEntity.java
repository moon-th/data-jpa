package study.datajpa.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass //상속을 받은 Entity 에서 해당 클래스의 프로퍼티를 사용하기 위하여 선언
@Getter
@EntityListeners(AuditingEntityListener.class) //audit 을 이용하면 자동으로 시간을 매핑하여 데이터베이스의 테이블에 넣어주게 됩니다.
public class BaseEntity {

    @CreatedDate //해당 Entity 가 등록될 경우 자동으로 생성
    @Column(updatable = false) // 해당 컬럼에 대해서 업데이트를 하지 않는다.
    private LocalDateTime createdDate;

    @LastModifiedDate //해당 Entity 가 수정될 경우 자동으로 생성
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;
}

