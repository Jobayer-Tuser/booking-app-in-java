package org.booking.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for handling complex user queries and pagination.
 * Separates query/read operations from core CRUD operations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Retrieves users with pagination and sorting.
     *
     * @param field the field to sort by
     * @param page the page number (0-indexed)
     * @param pageSize the number of items per page
     * @return paginated and sorted users
     */
    public Page<UserDto> getUsersWithPagination(String field, int page, int pageSize) {
        log.debug("Retrieving users with pagination - field: {}, page: {}, size: {}",
                field, page, pageSize);

        Sort sort = Sort.by(Sort.Direction.ASC, field);
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        return userRepository.findAll(pageable)
                .map(userMapper::toSingleDto);
    }

    /**
     * Retrieves users using cursor-based pagination for better performance on large datasets.
     *
     * @param cursor the cursor (last user ID from previous page), null for first page
     * @param pageSize the number of items to retrieve
     * @return cursor page response with users and next cursor
     */
    public CursorPageResponse<UserDto> getUsersWithCursorPagination(Long cursor, int pageSize) {
        log.debug("Retrieving users with cursor pagination - cursor: {}, size: {}",
                cursor, pageSize);

        Pageable pageable = PageRequest.of(0, pageSize);
        List<User> users = userRepository.cursorPaginationPattern(cursor, pageable);

        boolean hasNext = users.size() == pageSize;
        Long nextCursor = hasNext ? users.getLast().getId() : null;

        List<UserDto> userDtos = userMapper.toMultipleDto(users);

        return new CursorPageResponse<>(userDtos, pageSize, nextCursor, hasNext);
    }

    /**
     * Searches users by various criteria.
     *
     * @param searchCriteria the search criteria
     * @return list of matching users
     */
    public List<UserDto> searchUsers(UserSearchCriteria searchCriteria) {
        log.debug("Searching users with criteria: {}", searchCriteria);

        // This would use a custom repository method or specification
        // Example placeholder - implement based on your needs
        throw new UnsupportedOperationException("Search functionality to be implemented");
    }

    /**
     * Counts total number of users.
     *
     * @return total user count
     */
    public long getTotalUserCount() {
        log.debug("Counting total users");
        return userRepository.count();
    }

    /**
     * Counts verified users.
     *
     * @return verified user count
     */
    public long getVerifiedUserCount() {
        log.debug("Counting verified users");
        // Implement custom repository method if needed
        throw new UnsupportedOperationException("Verified user count to be implemented");
    }
}