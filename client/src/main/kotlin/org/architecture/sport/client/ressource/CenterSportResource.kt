package org.architecture.sport.client.ressource

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/center-sport")
@RestController
class CenterSportResource {

    @PostMapping
    fun createCenterSport() {
        TODO()
    }

    @GetMapping
    fun getCenterSport() {
        TODO()
    }
}