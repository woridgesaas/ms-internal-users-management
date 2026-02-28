package org.woridge.ms_internal_users_management.model;

import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.woridge.ms_internal_users_management.utils.SecurityUtils;

import java.util.Collection;
import java.util.List;
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
    private final User user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(SecurityUtils.getCurrentUserRole()));
    }
    @Override
    public @Nullable String getPassword() {
        return "";
    }
    @Override
    public String getUsername() {
        return SecurityUtils.getCurrentUserCIN();
    }
}
