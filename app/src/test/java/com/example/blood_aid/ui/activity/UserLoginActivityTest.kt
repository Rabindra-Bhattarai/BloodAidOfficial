package com.example.blood_aid.ui.activity

import com.example.blood_aid.utils.LoginUtils
import org.junit.After
import org.junit.Before
import org.junit.Test

class UserLoginActivityTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun email_is_empty(){
        val email=""
        val assert = LoginUtils.validate(email, password = "sassa")
        assert(assert=="Email is required")

    }
}