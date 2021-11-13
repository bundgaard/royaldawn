package org.tretton63.dookuu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.rometools.rome.feed.synd.SyndFeed;
import org.apache.camel.Body;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
public class Config extends RouteBuilder {

    private static final Logger logger = LoggerFactory.getLogger(Config.class);

    @Value("${feed-url}")
    private String feedUrl;

    @Override
    public void configure() throws Exception {
        Path tmpFolder = Files.createTempDirectory("dookuu");

        from(feedUrl)
                .marshal().rss()

                .log("Saving RSS ${body}")
                .to("file:" + tmpFolder.toString());
    }

    @Bean
    public JsonMapper jsonMapper() {
        return new JsonMapper();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    public static class ConverterBean {

        private ObjectMapper objectMapper;

        public ConverterBean(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        public String converter(@Body SyndFeed feed) throws JsonProcessingException {
            List<Article> items = feed.getEntries().stream().map(entry -> new Article(
                            entry.getTitle(),
                            entry.getDescription().getValue(),
                            entry.getLink()
                    )
            ).toList();
            return objectMapper.writeValueAsString(items);

        }
    }


}
