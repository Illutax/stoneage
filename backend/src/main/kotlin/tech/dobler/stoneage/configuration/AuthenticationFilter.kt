package tech.dobler.stoneage.configuration

import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import tech.dobler.stoneage.configuration.AuthenticationService.getAuthentication
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Suppress("unused", "Justification: Used by Spring implicitly")
class AuthenticationFilter : GenericFilterBean() {
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {
        try {
            val authentication = getAuthentication(
                (request as HttpServletRequest)
            )
            SecurityContextHolder.getContext().authentication = authentication
        } catch (exp: Exception) {
            val httpResponse = response as HttpServletResponse
            httpResponse.status = HttpServletResponse.SC_UNAUTHORIZED
            httpResponse.contentType = MediaType.APPLICATION_JSON_VALUE
            val writer = httpResponse.writer
            writer.print(exp.message)
            writer.flush()
            writer.close()
        }
        filterChain.doFilter(request, response)
    }
}