package com.inditex.zarachallenge.shared.utils;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public static ResponseEntity<ErrorDTO> createErrorInfoDtoResponseEntity(
        @NonNull HttpStatus httpStatus, @NonNull Exception exception, @NonNull WebRequest request,
        boolean translate) {

        var offsetDateTime = OffsetDateTime.now(TimeZone.getDefault().toZoneId());

        ErrorDTO errorDTO;
        if ( translate ) {
            errorDTO = errorInfoWithTranslate(httpStatus.value(),
                MessageUtils.translate(LocaleContextHolder.getLocale(), exception.getMessage()),
                request.getDescription(false),
                offsetDateTime);
        } else {
            errorDTO = errorInfoWithTranslate(httpStatus.value(),
                exception.getMessage(),
                request.getDescription(false),
                offsetDateTime);
        }

        return new ResponseEntity<>(errorDTO, HttpStatus.valueOf(errorDTO.getCode()));
    }

    private static ErrorDTO errorInfoWithTranslate(
        Integer code, String message, String description, OffsetDateTime offsetDateTime) {

        return ErrorDTO.builder()
                .timestamp(offsetDateTime)
                .date(offsetDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .code(code)
                .message(message)
                .path(description)
                .build();
    }

}
