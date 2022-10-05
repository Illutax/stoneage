package tech.dobler.stoneage

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StoneageApplication

fun main(args: Array<String>) {
	runApplication<StoneageApplication>(*args)
}
