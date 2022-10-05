package tech.dobler.stoneage

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class WorkTest {
    @Test
    fun `zero duration`() {
        val now = LocalDateTime.now()
        val work = Work(WorkID.new(), now)
        assertThat(work.duration().toSeconds()).isZero()
    }

    @Test
    fun `positive duration`() {
        val now = LocalDateTime.now()
        val inThePast = now.plusMinutes(2)
        val work = Work(WorkID.new(), inThePast)
        assertThat(work.duration()).isPositive()
    }

    @Test
    fun `negative duration overrides to zero`() {
        val now = LocalDateTime.now()
        val inTheFuture = now.minusMinutes(2)
        val work = Work(WorkID.new(), inTheFuture)
        assertThat(work.duration().toSeconds()).isZero()
    }
}