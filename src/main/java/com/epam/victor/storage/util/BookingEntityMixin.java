package com.epam.victor.storage.util;

import com.epam.victor.model.Event;
import com.epam.victor.model.Ticket;
import com.epam.victor.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Ticket.class, name = "com.epam.victor.model.Ticket"),
        @JsonSubTypes.Type(value = User.class, name = "com.epam.victor.model.User"),
        @JsonSubTypes.Type(value = Event.class, name = "com.epam.victor.model.Event")}
)
public interface BookingEntityMixin {

    Long getId();

}
