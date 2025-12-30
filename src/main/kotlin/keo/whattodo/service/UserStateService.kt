package keo.whattodo.service

import jakarta.transaction.Transactional
import keo.whattodo.domain.UserState
import keo.whattodo.repository.UserStateRepository
import keo.whattodo.repository.findByIdOrElseThrow
import org.springframework.stereotype.Service

@Service
class UserStateService(private val userStateRepository: UserStateRepository) {

    fun create(platform: String, userId: String, roomId: String) : UserState {
        val userState = UserState(platform, userId, roomId)
        return userStateRepository.save(userState)
    }

    @Transactional
    fun update(id: Long, update: UserState.() -> Unit) {
        val userState = userStateRepository.findByIdOrElseThrow(id)
        userState.update()
    }
}
