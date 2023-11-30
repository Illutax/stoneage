package tech.dobler.stoneage.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tech.dobler.stoneage.domain.User
import tech.dobler.stoneage.domain.UserID

@Repository
interface UserRepository: JpaRepository<User, UserID> {
}