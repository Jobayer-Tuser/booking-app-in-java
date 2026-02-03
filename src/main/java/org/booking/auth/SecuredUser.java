package org.booking.auth;

import org.booking.permission.Permission;
import org.booking.roles.Role;
import org.booking.users.User;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class SecuredUser implements UserDetails {

    private final Long id;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public SecuredUser(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.authorities = getSimpleGrantedAuthorities(user.getRole());
    }

    public Long getUserId() {
        return id;
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public @NullMarked String getUsername() {
        return email;
    }

    @Override
    public @NullMarked Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    private static @NonNull List<SimpleGrantedAuthority> getSimpleGrantedAuthorities(Role role) {
        Stream<SimpleGrantedAuthority> roleStream = Stream.of(
                new SimpleGrantedAuthority("ROLE_" + role.getName())
        );

        Stream<SimpleGrantedAuthority> permissionStream = role.getPermissions().stream()
                .map(Permission::getName)
                .map(SimpleGrantedAuthority::new);

        return Stream.concat(roleStream, permissionStream).toList();
    }
}
