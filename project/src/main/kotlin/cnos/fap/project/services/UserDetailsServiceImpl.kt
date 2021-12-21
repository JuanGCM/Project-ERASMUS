package cnos.fap.project.services

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service("userDetailsService")
class UserDetailsServiceImpl(
    var userService:UserService
):UserDetailsService
{
    override fun loadUserByUsername(username: String): UserDetails =
        userService.findByUsername(username).orElseThrow {
            UsernameNotFoundException("User $username not found")
        }

}