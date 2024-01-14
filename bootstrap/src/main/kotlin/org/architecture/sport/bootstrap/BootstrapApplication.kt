package org.architecture.sport.bootstrap

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@ComponentScan(basePackages = ["org.architecture.sport"])
@EnableMongoRepositories(basePackages = ["org.architecture.sport"])
class BootstrapApplication

fun main(args: Array<String>) {
    runApplication<BootstrapApplication>(*args)
}
