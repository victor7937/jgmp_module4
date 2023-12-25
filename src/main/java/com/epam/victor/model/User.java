package com.epam.victor.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements BookingEntity {

    Long id;

    String name;

    String email;

}

