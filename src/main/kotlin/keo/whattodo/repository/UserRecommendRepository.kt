package keo.whattodo.repository

import keo.whattodo.domain.UserRecommend
import org.springframework.data.jpa.repository.JpaRepository

fun UserRecommendRepository.findByIdOrElseThrow(id: Long): UserRecommend {
    return findById(id).orElseThrow { IllegalArgumentException("UserRecommend not found for id: $id") }
}

interface UserRecommendRepository : JpaRepository<UserRecommend, Long>
