package tech.dobler.stoneage

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.mock
import java.time.LocalDateTime
import org.mockito.Mockito.`when` as mockitoWhen

class DurationCalculationServiceTest {

    private var timeProvider = mock(ITimeProvider::class.java)
    private val now = LocalDateTime.now()

    private lateinit var durationCalculationService: DurationCalculationService

    @BeforeEach
    fun setUp() {
        durationCalculationService = DurationCalculationService(this.timeProvider)
        mockitoWhen(timeProvider.instant).thenReturn(now)
    }


    @Test
    fun `zero duration`() {
        val work = Work(WorkID.new(), now)
        Assertions.assertThat(durationCalculationService.duration(work.finishing).toSeconds()).isZero()
    }

    @Test
    fun `positive duration`() {
        val inThePast = now.plusMinutes(2)
        val work = Work(WorkID.new(), inThePast)
        Assertions.assertThat(durationCalculationService.duration(work.finishing)).isPositive()
    }

    @Test
    fun `negative duration overrides to zero`() {
        val inTheFuture = now.minusMinutes(2)
        val work = Work(WorkID.new(), inTheFuture)
        Assertions.assertThat(durationCalculationService.duration(work.finishing).toSeconds()).isZero()
    }
}