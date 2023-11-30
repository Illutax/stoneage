package tech.dobler.stoneage.configuration

import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import javax.servlet.http.HttpServletRequest

object AuthenticationService {
    private const val AUTH_TOKEN_HEADER_NAME = "X-API-KEY"
    private const val AUTH_TOKEN = "Baeldung"
    @JvmStatic
    fun getAuthentication(request: HttpServletRequest): Authentication {
        val apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME)
        if (apiKey == null || apiKey != AUTH_TOKEN) {
            throw BadCredentialsException("Invalid API Key")
        }
        return ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES)
    }
}