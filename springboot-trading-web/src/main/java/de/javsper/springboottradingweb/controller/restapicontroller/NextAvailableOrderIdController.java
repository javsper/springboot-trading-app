package de.javsper.springboottradingweb.controller.restapicontroller;

import de.javsper.springboottradingibkr.client.config.PropertiesConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NextAvailableOrderIdController {

    private final PropertiesConfig propertiesConfig;

    public NextAvailableOrderIdController(PropertiesConfig propertiesConfig) {
        this.propertiesConfig = propertiesConfig;
    }

    @GetMapping("/get-next-order-id")
    public Integer getNextAvailableOrderId(){
        return propertiesConfig.getNextValidOrderId();
        }
    }
