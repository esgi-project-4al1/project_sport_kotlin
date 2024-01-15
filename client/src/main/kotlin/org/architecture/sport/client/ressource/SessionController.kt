package org.architecture.sport.client.ressource

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/session")
class SessionController {


    @PostMapping
    fun createSession(
    ) {
        TODO()
    }


    @GetMapping
    fun getSession(
    ) {
        TODO()
    }



    @PostMapping
    fun getCaution(
    ) {
        TODO()
    }

}