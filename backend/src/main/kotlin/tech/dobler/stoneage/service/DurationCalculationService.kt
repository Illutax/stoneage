package tech.dobler.stoneage.service

import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime

@Service
class DurationCalculationService(private val timeProvider: ITimeProvider) {
    fun duration(time: LocalDateTime): Duration {
        val duration = Duration.between(timeProvider.instant, time)
        return if (duration.toSeconds() < 0L) Duration.ZERO else duration
    }
}