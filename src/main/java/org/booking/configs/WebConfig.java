package org.booking.configs;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
@RequiredArgsConstructor
public class WebConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public Faker faker() {
        return new Faker();
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public AuditorAware<Long> auditorAware () {
        return new ApplicationAuditAware();
    }
}
