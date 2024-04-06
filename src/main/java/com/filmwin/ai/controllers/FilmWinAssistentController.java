package com.filmwin.ai.controllers;

import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/filmwin")
public class FilmWinAssistentController {

    private final OpenAiChatClient chatClient;

    public FilmWinAssistentController(OpenAiChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/informations")
    public String filmWinChat(@RequestParam(value = "message",
            defaultValue = "Quais são os melhores filmes dos ultimos anos?") String message) {
        return chatClient.call(message);
    }

    @GetMapping("/reviews")
    public String filmWinReview(@RequestParam(value = "film", defaultValue = "Corra!") String film) {
        PromptTemplate promptTemplate = new PromptTemplate("""
                  Por favor, me forneça
                  um breve resumo do filme {film}
                  e também informações sobre o elenco.
                """);
        promptTemplate.add("film", film);
        return this.chatClient.call(promptTemplate.create()).getResult().getOutput().getContent();
    }

    @GetMapping("/stream/informations")
    public Flux<String> filmWinChatStream(@RequestParam(value = "message",
            defaultValue = "Quais são os melhores filmes dos ultimos anos?") String message) {
        return chatClient.stream(message);
    }
}
