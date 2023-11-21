package tech.dobler.stoneage.controller

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import tech.dobler.stoneage.domain.Work
import tech.dobler.stoneage.domain.WorkID
import tech.dobler.stoneage.service.WorkService
import java.time.LocalDateTime
import org.mockito.Mockito.`when` as mockitoWhen

class WorkControllerTest {

    private val basePath = "/work"
    private val workService = mock(WorkService::class.java)

    private lateinit var systemUnderTest: MockMvc

    @BeforeEach
    fun setUp() {
        val workController = WorkController(workService)
        systemUnderTest = MockMvcBuilders.standaloneSetup(workController).build()
    }

    @Nested
    inner class Status {
        @Test
        @Throws(Exception::class)
        fun `none running returns empty duration`() {
            systemUnderTest.perform(get(basePath))
                .andExpect(status().isOk())
                .andExpect(content().string(""))
        }

        @Test
        @Throws(Exception::class)
        fun `running returns its duration`() {
            val work = Work(WorkID.new(), LocalDateTime.parse("2023-11-17T12:07"), false)
            mockitoWhen(workService.find()).thenReturn(work)

            systemUnderTest.perform(get(basePath))
                .andExpect(status().isOk())
//                .andDo(print())
                .andExpect(content().json("""{"completed":false,"finishing":"2023-11-17T12:07:00"}"""))
        }
    }

    @Nested
    inner class Start {
        @Test
        @Throws(Exception::class)
        fun `none running should return new Work obj`() {
            mockitoWhen(workService.startNew()).thenReturn(Work(WorkID("7a273f32-6a1f-415b-91b2-7c9bee8b87d3"), LocalDateTime.parse("2023-11-17T12:07").plusMinutes(2), false))
            systemUnderTest.perform(post(basePath))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("""{"completed":false,"finishing":"2023-11-17T12:09:00"}"""))
        }

        @Test
        @Throws(Exception::class)
        fun `already running should return 412`() {
            mockitoWhen(workService.find()).thenReturn(Work(WorkID("7a273f32-6a1f-415b-91b2-7c9bee8b87d3"), LocalDateTime.parse("2023-11-17T12:07").plusMinutes(2), false))
            systemUnderTest.perform(post(basePath))
                .andExpect(status().isPreconditionFailed())
        }
    }

}