package com.sysoiev.developers_db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "skills")
@ToString(exclude = {"developers"})
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Skill extends BaseEntity{

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "skills")
    private List<Developers> developers;
}
