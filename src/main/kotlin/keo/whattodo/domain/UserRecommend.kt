package keo.whattodo.domain

import jakarta.persistence.*

@Entity
class UserRecommend(
    @ManyToOne
    @JoinColumn(name = "user_state_id", nullable = false)
    val userState: UserState,

    @Column(nullable = false) val order: Int,
    @Column(nullable = false) val title: String,
    @Column(nullable = false) val reason: String,
    @Column(nullable = false) val step1: String,
    @Column(nullable = false) val step2: String,
    @Column(nullable = false) val step3: String,
    @Column(nullable = false) val tip: String,
    @Column(nullable = false) val isChoose: Boolean = false,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
)
