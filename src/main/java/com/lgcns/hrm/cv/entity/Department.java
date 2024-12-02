package com.lgcns.hrm.cv.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lgcns.hrm.cv.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import java.util.Collection;

@Entity
@Table(name = "department")
@Getter
@Setter
public class Department extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private String code;
    private String name;

    @Column(name = "parent_id", insertable = false, updatable = false)
//    @JsonIgnore
    private Integer parentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @Where(clause = "parent_id IS NULL")
    @JsonBackReference
    private Department parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    @Where(clause = "parent_id IS NOT NULL")
    @JsonManagedReference
    private Collection<Department> children;

    private Boolean status;
}
