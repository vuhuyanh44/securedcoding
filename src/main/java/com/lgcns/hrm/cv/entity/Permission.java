package com.lgcns.hrm.cv.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lgcns.hrm.cv.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Collection;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(
        name = "permission", uniqueConstraints = @UniqueConstraint(columnNames = {"component_name", "action"})
)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "permission")
public class Permission extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "component_name")
    private String componentName;

    @Column(name = "action")
    private String action;

    @ManyToMany(mappedBy = "permissions")
    @JsonBackReference(value = "group-permission")
    private Collection<Group> groups;
}