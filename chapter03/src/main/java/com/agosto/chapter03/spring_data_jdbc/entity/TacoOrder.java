package com.agosto.chapter03.spring_data_jdbc.entity;

import jakarta.validation.constraints.*;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Table("Taco_Order")
public class TacoOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    private Date placedAt;
    @NotBlank(message = " * Ingrese un nombre.")
    private String deliveryName;
    @NotBlank(message = " * Ingrese un domicilio.")
    private String deliveryStreet;
    @NotBlank(message = " * Ingrese una ciudad.")
    private String deliveryCity;
    @NotBlank(message = " * Ingrese un estado.")
    private String deliveryState;
    @NotBlank(message = " * Ingrese un código postal.")
    private String deliveryZip;
    @NotBlank(message = " * Ingrese el código de su tarjeta.")
    private String ccNumber;
    @NotNull(message = " * No puede estar vacio.")
    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$", message=" * Debe estar en el siguiente formato: MM/YY")
    private String ccExpiration;
    @NotNull(message = " * No puede estar vacio.")
    @Digits(integer=3, fraction=0, message=" * CVV Inválido")
    private String ccCVV;
    @NotNull(message = " * No puede estar vacio.")
    @Size(min = 1, message = " * Ingrese al menos un taco.")
    private List<Taco> tacos = new ArrayList<Taco>();

    public void addTaco(Taco taco){
        this.tacos.add(taco);
    }

}