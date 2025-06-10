package com.agosto.chapter03.spring_data_jdbc.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PRIVATE, force=true)
@Table("INGREDIENT")
public class Ingredient {

    @Id
    @Column("ID")
    private String id;

    @Column("NAME")
    private String name;

    @Column("TYPE")
    private Type type;

    public enum Type{
            WRAP,PROTEIN,VEGGIES,CHEESE,SAUCE
        }

}