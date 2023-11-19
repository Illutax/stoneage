package tech.dobler.stoneage

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WorkRepository: JpaRepository<Work, WorkID>{
    fun findByCompletedIsFalseOrderByFinishing(): List<Work>
}