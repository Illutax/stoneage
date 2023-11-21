package tech.dobler.stoneage.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.dobler.stoneage.Log
import tech.dobler.stoneage.domain.Work
import tech.dobler.stoneage.service.WorkService
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/work")
class WorkController(
    private val service: WorkService,
) {
    companion object : Log()

    @GetMapping("", produces = ["application/json"])
    fun getLatestWork(): ResponseEntity<WorkDto?> {
        val maybeWork = service.find()
            ?.let { WorkDto.of(it) }

        if (maybeWork == null) {
            log.info("Serving no work")
        } else {
            log.info("Serving {}", maybeWork)
        }

        return ResponseEntity.ok(maybeWork)
    }

    @PostMapping("", produces = ["application/json"])
    fun startWorking(): ResponseEntity<WorkDto> {
        val current = service.find()
        if (current != null) {
            log.error("already started at ${current.finishing}")
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build()
        }

        val work = WorkDto.of(service.startNew())
        log.info("Started new {}", work)
        return ResponseEntity.ok(work)
    }
}

data class WorkDto(val completed: Boolean, val finishing: String) {
    companion object {
        private val format = DateTimeFormatter.ISO_LOCAL_DATE_TIME

        fun of(work: Work): WorkDto {
            return WorkDto(work.completed, work.finishing.format(format))
        }
    }
}