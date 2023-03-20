package com.nisum.test.apigmc.utils;

import org.springframework.context.MessageSource;

import java.util.Locale;

public class MessageUtils {

    public MessageUtils() {
    }

    public static String getMessage(MessageSource messageSource, String messageKey, Object... arguments){
        return messageSource.getMessage(messageKey, arguments, Locale.getDefault());
    }
}
