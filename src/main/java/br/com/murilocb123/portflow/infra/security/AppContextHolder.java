package br.com.murilocb123.portflow.infra.security;

import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class AppContextHolder {

    private static final ThreadLocal<UUID> nCurrentUserId = new ThreadLocal<>();

    private static final ThreadLocal<UUID> nCurrentTenant = new ThreadLocal<>();

    public static void setUserId(UUID nUserId) {
        nCurrentUserId.set(nUserId);
    }

    public static UUID getUserId() {
        return nCurrentUserId.get();
    }

    public static void setTenant(UUID nTenant) {
        nCurrentTenant.set(nTenant);
    }

    public static UUID getTenant() {
        return nCurrentTenant.get();
    }

    public static void clear() {
        nCurrentTenant.remove();
        nCurrentUserId.remove();
    }
}
