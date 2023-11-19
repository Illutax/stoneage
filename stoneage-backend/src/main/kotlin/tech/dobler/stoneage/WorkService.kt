package tech.dobler.stoneage

import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

@Service
class WorkService(private val repo: WorkRepository, private val timeProvider: TimeProvider) {
    companion object: Log()
    fun find(): Work? {
        val now = timeProvider.instant
        val all = repo.findByCompletedIsFalseOrderByFinishing()
        val remaining = maintain(all, now)
        if (remaining.size > 1) {
            log.warn("Got more than one ({}) remaining", remaining)
        }
        return remaining.firstOrNull()
    }

    private fun maintain(all: List<Work>, now: LocalDateTime): List<Work> {
        val newCompleted = all.asSequence()
            .filter { now.isAfter(it.finishing) }
            .map { it.complete() }
            .toSet()
        repo.saveAll(newCompleted)
        log.info("Maintainance: found ${all.size}, ${newCompleted.size} were completed in the meantime")
        return all.asSequence()
            .filter { !newCompleted.contains(it) }
            .toList()
    }

    fun startNew(): Work {
        return startWork(2)
    }

    private fun startWork(durationInMinutes: Long): Work {
        val now = timeProvider.instant
        val end = now.plusSeconds(TimeUnit.MINUTES.toSeconds(durationInMinutes))
        log.info("started to work for $durationInMinutes minutes. Will end at $end")
        val work = Work(WorkID.new(), end, false)
        return repo.save(work)
    }
}