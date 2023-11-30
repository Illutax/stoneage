package tech.dobler.stoneage.domain

import org.springframework.data.util.ProxyUtils
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "work")
data class Work(
    @Id override var id: WorkID,
    @Column(name = "finishing") val finishing: LocalDateTime,
    @Column(name = "completed") var completed: Boolean = false
) :
    AbstractJpaPersistable<WorkID>(id), IEntity<WorkID> {

    fun complete(): Work {
        return Work(id, finishing, true)
    }

    @SuppressWarnings(value = ["kotlin:S2097"])
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}

@Embeddable
data class WorkID(@Column(name = "id") val value: String) : Serializable {
    companion object {
        fun new(): WorkID {
            return WorkID(UUID.randomUUID().toString())
        }
    }
}

@Entity
@Table(name = "users")
data class User(
    @Id override var id: UserID,
    val email: EMail,
    @Column(name = "password") val password: String,
    @Column(name = "is_admin") var isAdmin: Boolean = false
) :
    AbstractJpaPersistable<UserID>(id), IEntity<UserID> {

    @SuppressWarnings(value = ["kotlin:S2097"])
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}

@Embeddable
data class UserID(@Column(name = "id") val value: String) : Serializable {
    companion object {
        fun new(): UserID {
            return UserID(UUID.randomUUID().toString())
        }
    }
}

@Embeddable
data class EMail(@Column(name = "email") val value: String)

interface IEntity<TID> {
    val id: TID?
}

@MappedSuperclass
abstract class AbstractJpaPersistable<TID : Serializable>(@Id override var id: TID) :
    IEntity<TID> {

    override fun equals(other: Any?): Boolean {
        other ?: return false

        if (this === other) return true

        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as AbstractJpaPersistable<*>

        return this.id == other.id
    }

    override fun hashCode(): Int {
        return this.id.hashCode()
    }

    override fun toString() = "Entity of type ${this.javaClass.name} with id: $id"

}