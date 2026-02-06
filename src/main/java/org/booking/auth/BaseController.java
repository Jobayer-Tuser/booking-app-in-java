package org.booking.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class BaseController {

    protected Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof SecuredUser securedUser) {
            return securedUser.getUserId();
        }

        // Throw an exception if this method is called in a non-secured route
        throw new IllegalStateException("Cannot find user ID in the current security context");
    }

    @ModelAttribute("currentUserId")
    public Long currentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth.getPrincipal() instanceof SecuredUser u) ? u.getUserId() : null;
    }
}
