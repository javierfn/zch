package com.inditex.zarachallenge.shared.utils;

import java.util.Locale;
import org.apache.commons.lang3.Validate;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import com.inditex.zarachallenge.shared.Constants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MessageUtils {
    private static MessageSource messageSource;

    static {
        MessageUtils.messageSource = messageSource();
    }

    public static String translate(Locale locale, String key) {

        String translatedMessage;
        try {
            Validate.notNull(locale, "locale require not null");
            Validate.notEmpty(key, "key require not empty");

            translatedMessage = messageSource.getMessage(key, null, locale);
        } catch (Exception e) {
            log.error(Constants.EXCEPTION_MESSAGE, e.getMessage());
            log.error(Constants.EXCEPTION, e);

            translatedMessage = key;
        }

        return translatedMessage;
    }

    private static MessageSource messageSource() {

        var messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.addBasenames("messages/messages");
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }
}
