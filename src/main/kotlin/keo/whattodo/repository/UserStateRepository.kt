package keo.whattodo.repository

import keo.whattodo.domain.UserState
import org.springframework.data.jpa.repository.JpaRepository

fun UserStateRepository.findByIdOrElseThrow(id: Long): UserState {
    return findById(id).orElseThrow { IllegalArgumentException("UserState not found for id: $id") }
}

interface UserStateRepository : JpaRepository<UserState, Long> {
}
