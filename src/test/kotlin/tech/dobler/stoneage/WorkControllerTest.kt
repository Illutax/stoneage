package tech.dobler.stoneage

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDateTime
import org.mockito.Mockito.`when` as mockitoWhen

class WorkControllerTest() {

    private val workService = mock(WorkService::class.java)
    private lateinit var systemUnderTest: MockMvc

    @BeforeEach
    fun setUp() {
        val workController = WorkController(workService)
        systemUnderTest = MockMvcBuilders.standaloneSetup(workController).build()
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnDefaultMessage() {
        systemUnderTest.perform(get("/"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(""))
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturn() {
        mockitoWhen(workService.startNew()).thenReturn(Work(WorkID("7a273f32-6a1f-415b-91b2-7c9bee8b87d3"), LocalDateTime.parse("2023-11-17T12:07").plusMinutes(2), false))
        systemUnderTest.perform(get("/start"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json("""
                {"id":{"value":"7a273f32-6a1f-415b-91b2-7c9bee8b87d3"},"finishing":[2023,11,17,12,9],"completed":false}
                """))
    }
}