package org.abol.springstarter.actuator;

import org.abol.springstarter.models.BaseUser;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Component
@RestController
public class SessionInfoEndpoint {

    @GetMapping("/actuator/session-info")
    public Map<String, Object> sessionInfo(@SessionAttribute("baseUser") BaseUser baseUser) {
        Map<String, Object> sessionData = new HashMap<>();
        sessionData.put("name", baseUser.getName());
        sessionData.put("email", baseUser.getEmail());
        sessionData.put("cart", baseUser.getCart());
        return sessionData;
    }
}
