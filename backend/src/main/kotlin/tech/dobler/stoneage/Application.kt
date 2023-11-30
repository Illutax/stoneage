package tech.dobler.stoneage

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class, UserDetailsServiceAutoConfiguration::class])
@Configuration
@EnableJpaRepositories(basePackages = ["tech.dobler.stoneage"])
class Application {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }
}