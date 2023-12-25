package com.epam.victor.model;

import lombok.*;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket implements BookingEntity {

    public enum Category {STANDARD, PREMIUM, BAR}

    Long id;

    Long eventId;

    Long userId;

    Category category;

    int place;

}
