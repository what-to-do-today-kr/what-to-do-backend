package keo.whattodo.controller

import dev.kord.core.Kord
import dev.kord.core.event.gateway.ReadyEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.annotation.PreDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private val log = KotlinLogging.logger {}

@Configuration
class KordConfig {
    private val botJob = SupervisorJob()
    private val botScope = CoroutineScope(Dispatchers.Default + botJob)

    @Bean
    fun kord(@Value("\${discord.bot.token}") token: String): Kord {
        return runBlocking { Kord(token) }
    }

    @Bean
    @OptIn(PrivilegedIntent::class)
    fun run(kord: Kord): ApplicationRunner {
        return ApplicationRunner {
            botScope.launch {
                kord.on<ReadyEvent> { log.info { "Kord Login Complete! (Logged in as ${self.username})" } }
                kord.login { intents += Intent.MessageContent }
            }
        }
    }

    @PreDestroy
    fun shutdown() {
        runBlocking { botJob.cancel() }
    }
}