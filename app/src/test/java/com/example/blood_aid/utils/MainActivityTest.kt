package com.example.blood_aid.utils

import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun username_is_empty(){
        val username=""
         val assert = LoginUtils.validate(username, password = "sassa")
        assert(assert=="username is required")
    }
}