package com.likelion.dub.configuration;


import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleMappingConfig {
    @Bean
    public Map<String, String> roleMapping() {
        Map<String, String> roleMapping = new HashMap<>();
        roleMapping.put("USER", "ROLE_USER");
        roleMapping.put("CLUB", "ROLE_CLUB");
        roleMapping.put("ADMIN", "ROLE_ADMIN");
        return roleMapping;
    }

}
