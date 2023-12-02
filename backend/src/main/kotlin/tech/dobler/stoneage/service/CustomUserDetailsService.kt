package tech.dobler.stoneage.service

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import tech.dobler.stoneage.domain.EMail
import tech.dobler.stoneage.domain.User
import tech.dobler.stoneage.repository.UserRepository

@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    private val userCache: Map<String, CustomUserDetails> = HashMap()
    override fun loadUserByUsername(username: String): UserDetails =
        userCache.getOrElse(username) {
            userRepository.findByEmail(EMail(username)).map { CustomUserDetails(it) }.orElseThrow()
        }

}

class CustomUserDetails(private val user: User) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = when {
        user.isAdmin -> AuthorityUtils.createAuthorityList("ADMIN_ROLE", "USER_ROLE")
        else -> AuthorityUtils.createAuthorityList("USER_ROLE")
    }

    override fun getPassword(): String = user.password

    override fun getUsername(): String = user.email.value

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}