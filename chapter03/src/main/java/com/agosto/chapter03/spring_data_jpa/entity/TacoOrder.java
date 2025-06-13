package com.agosto.chapter03.spring_data_jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "TACO_ORDER")
public class TacoOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "PLACED_AT")
    private Date placedAt = new Date();;

    @Column(name = "DELIVERY_NAME")
    @NotBlank(message = " * Ingrese un nombre.")
    private String deliveryName;

    @Column(name = "DELIVERY_STREET")
    @NotBlank(message = " * Ingrese un domicilio.")
    private String deliveryStreet;

    @Column(name = "DELIVERY_CITY")
    @NotBlank(message = " * Ingrese una ciudad.")
    private String deliveryCity;

    @Column(name = "DELIVERY_STATE")
    @NotBlank(message = " * Ingrese un estado.")
    private String deliveryState;

    @Column(name = "DELIVERY_ZIP")
    @NotBlank(message = " * Ingrese un código postal.")
    private String deliveryZip;

    @Column(name = "CC_NUMBER")
    @NotBlank(message = " * Ingrese el código de su tarjeta.")
    private String ccNumber;

    @Column(name = "CC_EXPIRATION")
    @NotNull(message = " * No puede estar vacio.")
    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$", message=" * Debe estar en el siguiente formato: MM/YY")
    private String ccExpiration;

    @NotNull(message = " * No puede estar vacio.")
    @Digits(integer=3, fraction=0, message=" * CVV Inválido")
    @Column(name = "CC_CVV")
    private String ccCVV;

    @NotNull(message = " * No puede estar vacio.")
    @Size(min = 1, message = " * Ingrese al menos un taco.")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "TACO_ORDER")
    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        taco.setTacoOrder(this);
        this.tacos.add(taco);
    }

}
