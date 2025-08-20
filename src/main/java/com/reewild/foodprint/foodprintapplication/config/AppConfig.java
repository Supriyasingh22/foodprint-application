package com.reewild.foodprint.foodprintapplication.config;

import com.reewild.foodprint.foodprintapplication.ai.AiNlpProvider;
import com.reewild.foodprint.foodprintapplication.ai.AiVisionProvider;
import com.reewild.foodprint.foodprintapplication.ai.OpenAiNlp;
import com.reewild.foodprint.foodprintapplication.ai.OpenAiVision;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${app.openai.apiKey}")
    private String openAiKey;

    @Bean
    public AiNlpProvider aiNlpProvider() {
        return new OpenAiNlp(openAiKey);
    }

    @Bean
    public AiVisionProvider aiVisionProvider() {
        return new OpenAiVision(openAiKey);
    }
}
