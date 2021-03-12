package com.stgson.security;

public enum ApplicationUserPermission {
    DEPOT_READ("depot:read"),
    DEPOT_WRITE("depot:write"),
    TRANSFERT_READ("transfert:read"),
    TRANSFERT_WRITE("transfert:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
