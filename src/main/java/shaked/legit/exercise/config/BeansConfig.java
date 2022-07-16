package shaked.legit.exercise.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shaked.legit.exercise.logic.GithubEventAnomalyDetector;
import shaked.legit.exercise.util.json.GsonJsonParser;
import shaked.legit.exercise.util.json.JsonParser;
import shaked.legit.exercise.util.routing.GithubEventRoutingHolder;

import java.util.Arrays;

@Configuration
public class BeansConfig {

    @Bean
    public Gson gson() {
        return new GsonBuilder().create();
    }

    @Bean
    public JsonParser jsonParser(Gson gson) {
        return new GsonJsonParser(gson);
    }

    @Bean
    public GithubEventRoutingHolder githubEventRoutingHolder(GithubEventAnomalyDetector[] processors) {
        GithubEventRoutingHolder routingHolder = new GithubEventRoutingHolder();
        Arrays.asList(processors).forEach(routingHolder::registerProcessor);
        return routingHolder;
    }

}
