package com.aaruu.ems.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {

			return Optional.of("SYSTEM");
		}

		return Optional.of(authentication.getName()// froom here we get current login user it returns jack t spring
		// spring automatiacally does createdBy="jack" updatedBy="Jack"..we need to
		// enable jpa auditing from main class
		);
	}
}

//AuditorAware (Interface)
//↓ implements
//AuditorAwareImpl (Our Class)
//↓
//getCurrentAuditor()
//↓
//Returns Current Username
//↓
//@EnableJpaAuditing uses it
//↓
//@CreatedBy and @LastModifiedBy are populated automatically