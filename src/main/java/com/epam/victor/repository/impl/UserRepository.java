package com.epam.victor.repository.impl;

import com.epam.victor.model.User;
import com.epam.victor.repository.BookingRepository;
import com.epam.victor.storage.ObjectStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends BookingRepository<User> {

    @Autowired
    public UserRepository(ObjectStorage objectStorage) {
        super(User.class, objectStorage);
    }
}
