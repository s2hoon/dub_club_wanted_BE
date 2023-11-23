package com.likelion.dub.common.configuration;

import java.util.Optional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class AwareAuditor implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return Optional.of(email);
    }

}
