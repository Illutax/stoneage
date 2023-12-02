package tech.dobler.stoneage.service

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import tech.dobler.stoneage.domain.EMail
import tech.dobler.stoneage.domain.User
import tech.dobler.stoneage.domain.UserID
import tech.dobler.stoneage.repository.UserRepository

@ExtendWith(MockitoExtension::class)
class UserReaderTest(@Mock private val userRepository: UserRepository) {

    @Test
    fun read() {
        val u = UserReader(userRepository).read()
        assertThat(u).containsExactly(User(UserID("123"), EMail("admin@stoneage.com"), "foobar", true))
    }
}