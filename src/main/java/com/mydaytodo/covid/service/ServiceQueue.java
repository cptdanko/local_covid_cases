package com.mydaytodo.covid.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

public class ServiceQueue {
    final String CN_API = "https://api.chucknorris.io/jokes/random";
    public void getChuckNorrisJokes() {
        WebClient client = WebClient.create();

        //WebClient client = WebClient.cre
    }
}

@NoArgsConstructor
@Getter
@Setter
class Joke {
    private String iconUrl;
    private String id;
}