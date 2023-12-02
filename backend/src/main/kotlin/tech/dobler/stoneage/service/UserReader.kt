package tech.dobler.stoneage.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.springframework.util.ResourceUtils
import tech.dobler.stoneage.domain.User
import tech.dobler.stoneage.domain.UserID
import tech.dobler.stoneage.repository.UserRepository
import java.io.IOException

@Service
class UserReader(private val userRepository: UserRepository) {

    init {
        userRepository.saveAll(read())
    }

    // inspired by https://stackoverflow.com/a/65485176/7082956
    final fun read(): List<User> {
        val mapper = ObjectMapper()
        try {
            val file = ResourceUtils.getFile("classpath:users.json")
            val users = mapper.readValue<List<User>>(
                file,
                mapper.typeFactory.constructCollectionType(ArrayList::class.java, User::class.java)
            )
            return users.map {
                it.id = it.id ?: UserID.new()
                it
            }.toList()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return listOf()
    }
}