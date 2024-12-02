package com.lgcns.hrm.cv.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "`group`")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "`group`")
public class Group extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "groups")
    @JsonBackReference(value = "user-group")
    private Collection<User> users;

    @ManyToMany
    @JoinTable(
            name = "groups_permissions",
            joinColumns = @JoinColumn(
                    name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "permission_id", referencedColumnName = "id"))
    @JsonManagedReference(value = "group-permission")
    private Collection<Permission> permissions;
}