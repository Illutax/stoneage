package tech.dobler.stoneage

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext

@SpringBootTest
class ApplicationTests(private var applicationContext: ApplicationContext) {

    @Test
    fun contextLoads() {
        assertThat(applicationContext).isNotNull;
    }

}
