package com.sysoiev.developers_db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "developers")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Developer extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "birth_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @Column(name = "employment_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date employmentDate;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "developers_skills", joinColumns = @JoinColumn(name = "developer_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    @OrderBy(value = "id ASC")
    private List<Skill> skills;
}