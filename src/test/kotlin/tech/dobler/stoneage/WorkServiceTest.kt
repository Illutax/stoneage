package tech.dobler.stoneage

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class WorkServiceTest {
    @Mock
    private lateinit var workRepository: WorkRepository

    @Mock
    private lateinit var timeProvider: TimeProvider

    private lateinit var systemUnderTest: WorkService

    @BeforeEach
    fun setUp() {
        systemUnderTest = WorkService(workRepository, timeProvider)
    }

    @Test
    fun `find where one is present returns one`() {
        // Arrange
        val now = LocalDateTime.parse("2023-10-29T02:38:15.00")
        `when`(timeProvider.instant).thenReturn(now)
        `when`(workRepository.findByCompletedIsFalseOrderByFinishing())
            .thenReturn(listOf(Work(WorkID("1"), now.plusSeconds(15))))

        // Act
        val result = systemUnderTest.find()

        // Assert
        assertThat(result).isNotNull
    }

    @Test
    fun `find where one is present but it is completed returns none`() {
        // Arrange
        val now = LocalDateTime.parse("2023-10-29T02:38:15.00")
        `when`(timeProvider.instant).thenReturn(now)
        `when`(workRepository.findByCompletedIsFalseOrderByFinishing())
            .thenReturn(listOf(Work(WorkID("1"), now.minusSeconds(15))))

        // Act
        val result = systemUnderTest.find()

        // Assert
        assertThat(result).isNull()
    }
}