package ua.kokhaniuk.citiesgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.kokhaniuk.citiesgame.model.City;
import ua.kokhaniuk.citiesgame.service.CitiesService;

import java.util.List;
import java.util.Locale;

/**
 * @author Oleksandr Kokhaniuk
 * @created 6/8/2022, 5:59 AM
 */
@Controller
public class CitiesController {
    private final CitiesService citiesService;

    @Value("${user.message.hello}")
    String hello;
    @Value("${user.message.welcome}")
    String welcome;
    @Value("${user.message.continuegame}")
    String continuegame;
    @Value("${user.message.incorrectword}")
    String incorrectword;
    @Value("${user.message.tohelp}")
    String tohelp;
    @Value("${user.message.endgame}")
    String endgame;


    @Autowired
    public CitiesController(CitiesService citiesService) {
        this.citiesService = citiesService;
    }

    @GetMapping("/")
    public String citiesgame(Model model) {
        model.addAttribute("hello", hello);
        model.addAttribute("welcome", welcome);
        return "cities-game";
    }

    @GetMapping("/begin")
    public String newGame(Model model) {
        List<City> cityList = citiesService.findAll();
        int a = (int) (Math.random() * cityList.size());
        City city = cityList.get(a);
        model.addAttribute("city", city);
        model.addAttribute("message", continuegame);

        return "new-game";
    }

    @GetMapping("/next")
    public String nextWord(@RequestParam(value = "word", defaultValue = " ", required = false) String word,
                           @RequestParam(value = "name") String prevcity,
                           Model model) {

        String next = word.substring(0, 1);
        int lengPrev = prevcity.length();
        String prev = prevcity.substring(lengPrev - 1);
        if (checkExistCity(word)) {
            if (prev.equalsIgnoreCase(next)) {
                String firstLetter = word.substring(word.length() - 1).toUpperCase(Locale.ROOT);
                List<City> cityList = citiesService.findByFirstLetter(firstLetter);
                int a = (int) (Math.random() * cityList.size());
                City nextcity = cityList.get(a);
                model.addAttribute("city", nextcity);
                model.addAttribute("message", continuegame);
                return "new-game";
            }
        }

        City city = citiesService.findByName(prevcity);
        model.addAttribute("city", city);

        if (prev.equalsIgnoreCase(next) & word.length() > 2) {
            String helpLeters = word.substring(0, 1).toUpperCase() + word.substring(1, 3);
            List<City> listhelp = citiesService.findByFirstLetter(helpLeters);
            if (!(listhelp.isEmpty())) {
                City helpcity = listhelp.get(0);
                model.addAttribute("message", incorrectword + ". " + tohelp + ": " + helpcity.getName());
                return "new-game";
            }
        }
        List<City> helpList = citiesService.findByFirstLetter(prev.toUpperCase(Locale.ROOT));
        if (!(helpList.isEmpty())) {
            int a = (int) (Math.random() * (helpList.size() - 1));
            City helpcity = helpList.get(a);
            model.addAttribute("message", incorrectword + ". " + tohelp + ": " + helpcity.getName());
            return "new-game";
        }
        model.addAttribute("message", incorrectword);
        return "new-game";
    }

    @PostMapping("/end")
    public String endGame(Model model) {
        model.addAttribute("message", endgame);
        return "end-game";
    }

    private boolean checkExistCity(String word) {
        if (word.strip().length() > 2) {
            City checkCity = citiesService.findByName(word);
            return !(checkCity == null);
        }
        return false;
    }
}