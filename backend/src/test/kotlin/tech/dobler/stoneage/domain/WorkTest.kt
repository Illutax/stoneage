package tech.dobler.stoneage.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class WorkTest {

    @Test
    fun `work is reflexive`() {
        val work1 = Work(WorkID("abc"), LocalDateTime.parse("2023-11-17T13:18"))
        val work2 = Work(WorkID("abc"), LocalDateTime.parse("2023-11-17T13:19"))

        assertThat(work1).isEqualTo(work1)
        assertThat(work1).isEqualTo(work2)
        assertThat(work2).isEqualTo(work1)

        assertThat(work2).hasSameHashCodeAs(work1)
        assertThat(work1).hasSameHashCodeAs(work2)
    }

    @Test
    fun `ctor finished default=false`() {
        val work = Work(WorkID("abc"), LocalDateTime.parse("2023-11-17T13:18"))
        assertThat(work.id.value).isEqualTo("abc")
        assertThat(work.finishing.toString()).isEqualTo("2023-11-17T13:18")
        assertThat(work.completed).isEqualTo(false)
    }

    @Test
    fun `ctor completed`() {
        val uncompleted = Work(WorkID("abc"), LocalDateTime.parse("2023-11-17T13:18"))
        val work = uncompleted.complete()
        assertThat(work.id.value).isEqualTo("abc")
        assertThat(work.finishing.toString()).isEqualTo("2023-11-17T13:18")
        assertThat(work.completed).isEqualTo(true)
        assertThat(work)
            .usingRecursiveComparison(RecursiveComparisonConfiguration.builder().withIgnoredFields("completed").build())
            .isEqualTo(uncompleted)
    }

    @Test
    fun `ctor not finished`() {
        val work = Work(WorkID("abc"), LocalDateTime.parse("2023-11-17T13:18"), false)
        assertThat(work.id.value).isEqualTo("abc")
        assertThat(work.finishing).hasToString("2023-11-17T13:18")
        assertThat(work.completed).isEqualTo(false)
    }
}