package com.example.backend.model;

import com.example.backend.service.IdService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IdServiceTest {
    @Test
    void testReturnedId_whenIdServiceUuidIsCalled(){
        //GIVE
        //WHEN
        String actual = IdService.uuid();
        //THEN
        Assertions.assertEquals(String.class, actual.getClass());
    }
}
