package tech.dobler.stoneage.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tech.dobler.stoneage.domain.Work
import tech.dobler.stoneage.domain.WorkID

@Repository
interface WorkRepository: JpaRepository<Work, WorkID>{
    fun findByCompletedIsFalseOrderByFinishing(): List<Work>
}