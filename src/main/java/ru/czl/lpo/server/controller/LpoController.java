package ru.czl.lpo.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/lpo") // привязка у url, может конфликтовать с привязкой в ApplicationInitializer
public class LpoController {

    @RequestMapping(value= "/get", method = RequestMethod.GET)
    @ResponseBody // метод должен вернуть String
    public String getLpo(ModelMap model) {
        return "My lpo";
    }

}
