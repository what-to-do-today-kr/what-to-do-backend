package keo.whattodo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WhatToDoApplication

fun main(args: Array<String>) {
    runApplication<WhatToDoApplication>(*args)
}
