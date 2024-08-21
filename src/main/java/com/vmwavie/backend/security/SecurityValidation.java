package com.vmwavie.backend.security;

import java.util.regex.Pattern;

import com.vmwavie.backend.constants.GlobalMessage;
import org.springframework.stereotype.Component;

@Component
public class SecurityValidation {

    private static final Pattern XSS_PATTERN = Pattern.compile(
            "<script>(.*?)</script>|on\\w+\\s*=\\s*\"[^\"]*\"|on\\w+\\s*=\\s*'[^']*'|on\\w+\\s*=\\s*[^\\s>]+|javascript:",
            Pattern.CASE_INSENSITIVE
    );
    /**
     * This function should validate if code contains xss injection.
     *
     * @param input String.
     * @return true if is code is safe.
     * @throws IllegalArgumentException if code contains xss injection.
     */
    public boolean xss(String input) {
        if (XSS_PATTERN.matcher(input).find()) {
            throw new IllegalArgumentException(GlobalMessage.COMMON_ERRORS.UNDEFINED);
        } else {
            return true;
        }
    }
}