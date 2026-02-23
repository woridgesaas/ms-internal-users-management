package org.woridge.ms_internal_users_management.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {

    CEO("ROLE_CEO"),
    HR("ROLE_HR"),
    MANAGER("ROLE_MANAGER"),
    TECHNICAL("ROLE_TECHNICAL"),
    EMPLOYEE("ROLE_EMPLOYEE");

    private final String roleName;
}
