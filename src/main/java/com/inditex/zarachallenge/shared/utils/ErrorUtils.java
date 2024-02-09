package com.inditex.zarachallenge.shared.utils;

import java.time.OffsetDateTime;
import java.util.TimeZone;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;
import com.inditex.zarachallenge.infrastructure.dto.ErrorDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorUtils {

    public static ErrorDTO of(@NonNull HttpStatus httpStatus, @NonNull Exception exception, @NonNull WebRequest request) {

        return ErrorDTO.builder()
                .date(OffsetDateTime.now(TimeZone.getDefault().toZoneId()))
                .code(httpStatus.value())
                .message(MessageUtils.translate(LocaleContextHolder.getLocale(), exception.getMessage()))
                .path(request.getDescription(false))
                .build();
    }

}
