package de.sedriz.smartboot.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationEventListener {
    private static final long DEFAULT_TIMEOUT = 1000;
    private static long timeout = DEFAULT_TIMEOUT;
    private static boolean isAccountLocked = false;

    @EventListener
    public void authenticationFailed(AuthenticationFailureBadCredentialsEvent event) throws InterruptedException {
        Thread.sleep(timeout);
        doubleTimout();

        if(timeout >= 100_000) {
            lockAccount();
        }
    }

    @EventListener
    public void authenticationSuccess(AuthenticationSuccessEvent event) {
        resetTimeout();
    }

    private static void doubleTimout() {
        timeout *= 2;
    }

    public static void resetTimeout() {
        timeout = DEFAULT_TIMEOUT;
    }

    private static void lockAccount() {
        isAccountLocked = true;
    }

    public static boolean isAccountLocked() {
        return isAccountLocked;
    }
}
