package ua.kokhaniuk.citiesgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kokhaniuk.citiesgame.model.City;

import java.util.List;

/**
 * @author Oleksandr Kokhaniuk
 * @created 6/8/2022, 6:11 AM
 */
public interface CitiesRepository extends JpaRepository<City, Long> {

    City findByName(String name);
//    City findByNameStartWith(String startLetter);
    List<City> findByNameStartingWith(String firstLetter);
}

