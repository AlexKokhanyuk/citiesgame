package ua.kokhaniuk.citiesgame.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Oleksandr Kokhaniuk
 * @created 6/8/2022, 6:07 AM
 */
@Data
@Entity
@Table(name="cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "city_name")
    private String name;
}
