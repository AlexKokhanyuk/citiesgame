package ua.kokhaniuk.citiesgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kokhaniuk.citiesgame.model.City;
import ua.kokhaniuk.citiesgame.repository.CitiesRepository;

import java.util.List;

/**
 * @author Oleksandr Kokhaniuk
 * @created 6/8/2022, 6:13 AM
 */
@Service
public class CitiesService {
    private final CitiesRepository citiesRepository;

    @Autowired
    public CitiesService(CitiesRepository citiesRepository) {
        this.citiesRepository = citiesRepository;
    }

    public City findById(Long id) {
        return citiesRepository.findById(id).orElse(null);
    }

    public City findByName(String name){
        return citiesRepository.findByName(name);
    }

    public List<City> findAll() {
        return citiesRepository.findAll();
    }

    public List<City> findByFirstLetter(String firstLetter){
        return citiesRepository.findByNameStartingWith(firstLetter);
    }
}
