package keo.whattodo.client.recommend

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.ai.converter.BeanOutputConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class RecommendClient(
    private val client: AiClient,
    @Value("\${app.prompt.recommend.system}") private val systemPromptTemplate: String,
    @Value("\${app.prompt.recommend.user}") private val userPromptTemplate: String,
) {

    fun recommendToDo(request: RecommendRequest): RecommendResponse {
        val systemPrompt = systemPromptTemplate
            .replace("{jsonFormat}", RESPONSE_JSON_FORMAT)
        val userPrompt = userPromptTemplate
            .replace("{energy}", request.energy)
            .replace("{time}", request.time)
            .replace("{feeling}", request.mood)
            .replace("{environment}", request.environment)

        val response = client.call(systemPrompt, userPrompt)
            .replace(RESPONSE_IGNORE_REGEX, "")
            .trim()
        return parseResponse(response)
    }

    private fun parseResponse(response: String): RecommendResponse {
        try {
            return OBJECT_MAPPER.readValue(response, RecommendResponse::class.java)
        } catch (e: JsonProcessingException) {
            throw IllegalArgumentException("Failed to parse response: $response", e)
        }
    }

    companion object {
        private val RESPONSE_JSON_FORMAT: String = BeanOutputConverter(RecommendResponse::class.java).jsonSchema
        private val RESPONSE_IGNORE_REGEX: Regex = Regex("```\\s*[a-zA-Z]*\\s*|```")
        private val OBJECT_MAPPER: ObjectMapper = jacksonObjectMapper()
    }
}
