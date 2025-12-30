package keo.whattodo.domain

import jakarta.persistence.*

@Entity
class UserState(
    @Column(nullable = false) val userId: String,
    @Column(nullable = false) val platform: String,

    @Column(name = "energy") private var _energy: String? = null,
    @Column(name = "time") private var _time: String? = null,
    @Column(name = "environment") private var _environment: String? = null,
    @Column(name = "mood") private var _mood: String? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
) {

    private fun <T : Any> ensureNotNull(value: T?, name: String): T =
        value ?: throw IllegalStateException("$name is not set")

    var energy: String
        get() = ensureNotNull(_energy, "Energy")
        set(value) { _energy = value }

    var time: String
        get() = ensureNotNull(_time, "Time")
        set(value) { _time = value }

    var environment: String
        get() = ensureNotNull(_environment, "Environment")
        set(value) { _environment = value }

    var mood: String
        get() = ensureNotNull(_mood, "Mood")
        set(value) { _mood = value }
}
