package tech.dobler.stoneage.domain

import java.io.Serializable
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "work")
data class Work(@Id val id: WorkID, val finishing: LocalDateTime, var completed: Boolean = false) {
    fun complete(): Work {
        return Work(id, finishing, true)
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Work

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

@Embeddable
data class WorkID(@Column(name = "id") val value: String): Serializable {
    companion object {
        fun new(): WorkID {
            return WorkID(UUID.randomUUID().toString())
        }
    }
}