package tech.dobler.stoneage.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.transaction.annotation.Transactional
import tech.dobler.stoneage.domain.EMail
import tech.dobler.stoneage.domain.User
import tech.dobler.stoneage.domain.UserID
import tech.dobler.stoneage.flushAndClear
import javax.persistence.EntityManager

@DataJpaTest
class UserRepositoryTest(
    @Autowired private val systemUnderTest: UserRepository,
    @Autowired private val entityManager: EntityManager
) {
    @Test
    @Transactional
    fun empty() {
        val result = systemUnderTest.findAll()

        assertThat(result).isEmpty()
    }

    @Test
    @Transactional
    fun `one existing, should return it`() {
        val user = User(UserID.new(), EMail("a@b.c"), "foobar123")
        systemUnderTest.save(user)
        entityManager.flushAndClear()

        val result = systemUnderTest.findAll()

        assertThat(result).containsExactly(user)
    }
}