package org.booking.Utils.customquerybuilder;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.core.ResolvableType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Component
public class BeanUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    @SuppressWarnings("unchecked")
    public static <T> JpaSpecificationExecutor<T> getRepository(Class<T> entityClass) {
        String[] beanNames = context.getBeanNamesForType(
                ResolvableType.forClassWithGenerics(JpaSpecificationExecutor.class, entityClass));

        if (beanNames.length > 0) {
            return (JpaSpecificationExecutor<T>) context.getBean(beanNames[0]);
        }
        throw new RuntimeException("No JpaSpecificationExecutor found for entity: " + entityClass.getName());
    }

    public static jakarta.persistence.EntityManager getEntityManager() {
        return context.getBean(jakarta.persistence.EntityManager.class);
    }
}
