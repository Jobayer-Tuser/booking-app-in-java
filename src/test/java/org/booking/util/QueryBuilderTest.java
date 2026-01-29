package org.booking.util;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class QueryBuilderTest {

    @Test
    void testWhereEqual() {
        JpaSpecificationExecutor<Object> repository = mock(JpaSpecificationExecutor.class);
        var builder = new QueryBuilder<Object>();
        
        builder.where("name", "John").get(repository);
        
        ArgumentCaptor<Specification<Object>> specCaptor = ArgumentCaptor.forClass(Specification.class);
        verify(repository).findAll(specCaptor.capture());
        
        // In a real integration test we would check the predicate, 
        // but for unit test without Hibernate context, we primarily verify the flow and capturing.
        assertNotNull(specCaptor.getValue());
    }

    @Test
    void testWhereOperator() {
        JpaSpecificationExecutor<Object> repository = mock(JpaSpecificationExecutor.class);
        var builder = new QueryBuilder<Object>();

        builder.where("age", ">", 18).get(repository);

        ArgumentCaptor<Specification<Object>> specCaptor = ArgumentCaptor.forClass(Specification.class);
        verify(repository).findAll(specCaptor.capture());
        assertNotNull(specCaptor.getValue());
    }

    @Test
    void testOrderBy() {
        JpaSpecificationExecutor<Object> repository = mock(JpaSpecificationExecutor.class);
        var builder = new QueryBuilder<Object>();

        builder.orderBy("name", "asc").get(repository);

        ArgumentCaptor<Sort> sortCaptor = ArgumentCaptor.forClass(Sort.class);
        verify(repository).findAll(any(Specification.class), sortCaptor.capture());
        
        Sort sort = sortCaptor.getValue();
        assertEquals(Sort.Direction.ASC, sort.getOrderFor("name").getDirection());
    }
    
    @Test
    void testPaginate() {
        JpaSpecificationExecutor<Object> repository = mock(JpaSpecificationExecutor.class);
        var builder = new QueryBuilder<Object>();
        
        builder.paginate(repository, 0, 10);
        
        ArgumentCaptor<PageRequest> pageCaptor = ArgumentCaptor.forClass(PageRequest.class);
        verify(repository).findAll(any(Specification.class), pageCaptor.capture());
        
        PageRequest page = pageCaptor.getValue();
        assertEquals(0, page.getPageNumber());
        assertEquals(10, page.getPageSize());
    }
}
