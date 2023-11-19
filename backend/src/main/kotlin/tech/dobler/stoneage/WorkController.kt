package tech.dobler.stoneage

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Duration

@RestController
class WorkController(private val service: WorkService, private val durationCalculationService: DurationCalculationService) {
    companion object: Log()
    @GetMapping
    fun index(): ResponseEntity<Duration> = get()
    @GetMapping("/", produces = ["application/json"])
    fun get(): ResponseEntity<Duration> {
        val work = service.find()
        val duration = work?.finishing?.let{ durationCalculationService.duration(it) }
        return ResponseEntity.ok(duration)
    }

    @GetMapping("/start", produces = ["application/json"])
    fun startWorking(): ResponseEntity<Work> {
        val current = service.find()
        if (current != null) {
            log.error("already started at ${current.finishing}")
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build()
        }

        return ResponseEntity.ok(service.startNew())
    }

}