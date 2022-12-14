package tech.dobler.stoneage

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext

@SpringBootTest
class StoneageApplicationTests(var applicationContext: ApplicationContext) {

    @Test
    fun contextLoads() {
        assertThat(applicationContext).isNotNull
    }

}
