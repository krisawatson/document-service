package com.watsonsoftware.document.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Converter
public class TimestampConverter implements AttributeConverter<LocalDateTime, Timestamp> {
    private static final String zoneId = "UTC";

    @Override
    public Timestamp convertToDatabaseColumn(final LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return new Timestamp(localDateTime.atZone(ZoneId.of(zoneId)).toInstant().toEpochMilli());
    }

    @Override
    public LocalDateTime convertToEntityAttribute(final Timestamp localTime) {
        if (localTime == null) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(localTime.getTime()),
                ZoneId.of(zoneId));
    }
}
