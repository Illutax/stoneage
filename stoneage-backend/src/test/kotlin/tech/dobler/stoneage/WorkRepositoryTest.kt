package tech.dobler.stoneage

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import javax.persistence.EntityManager

@DataJpaTest
class WorkRepositoryTest(
    @Autowired private val systemUnderTest: WorkRepository,
    @Autowired private val entityManager: EntityManager
) {
    @Test
    @Transactional
    fun empty() {
        val result = systemUnderTest.findByCompletedIsFalseOrderByFinishing()

        assertThat(result).isEmpty()
    }

    @Test
    @Transactional
    fun `one completed returns nothing`() {
        systemUnderTest.save(Work(WorkID.new(), LocalDateTime.now(), true))
        entityManager.flushAndClear()

        val result = systemUnderTest.findByCompletedIsFalseOrderByFinishing()

        assertThat(result).isEmpty()
    }

    @Test
    @Transactional
    fun `one not completed returns one`() {
        val work = Work(WorkID.new(), LocalDateTime.parse("2023-10-29T03:40:43.12"), false)
        systemUnderTest.save(work)
        entityManager.flushAndClear()

        val result = systemUnderTest.findByCompletedIsFalseOrderByFinishing()

        assertThat(result).containsExactly(work)
        assertThat(result[0]).isNotSameAs(work)
    }
}