package tech.dobler.stoneage

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestClientException
import java.time.Duration

@RestController
class WorkController(private val service: WorkService) {
    @GetMapping
    fun index(): ResponseEntity<Duration> = get()
    @GetMapping("/", produces = ["application/json"])
    fun get(): ResponseEntity<Duration> {
        val work = service.find()
        return ResponseEntity.ok(work?.duration())
    }

    @GetMapping("/start", produces = ["application/json"])
    fun startWorking(): ResponseEntity<Work> {
        val current = service.find()
        if (current != null) {
            throw RestClientException("already started at ${current.finishing}")
        }

        return ResponseEntity.ok(service.startNew())
    }

}