package org.tretton63.dokuu;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.feed.dsl.Feed;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.transformer.AbstractPayloadTransformer;
import org.springframework.messaging.MessageHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

@EnableIntegration
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Value("${application.feed-url}")
    private Resource feedResource;

    @Bean
    public IntegrationFlow feedFlow() throws IOException {
        return IntegrationFlows.from(
                        Feed.inboundAdapter(feedResource,
                                "message"
                        ),
                        e -> e.poller(
                                p -> p.fixedDelay(300)
                        )
                )
                .transform(extractLinkFromFeed())
                .handle(targetDirectory()).log()
                .get();
    }


    @Bean
    public MessageHandler targetDirectory() throws IOException {
        Path tmpFolder = Files.createTempDirectory("messages");
        logger.info("tmpFolder {}", tmpFolder.toString());
        FileWritingMessageHandler handler = new FileWritingMessageHandler(
                tmpFolder.toAbsolutePath().toFile()
        );
        handler.setAutoCreateDirectory(true);
        handler.setCharset(StandardCharsets.UTF_8.toString());
        handler.setExpectReply(false);
        return handler;

    }

    @Bean
    public AbstractPayloadTransformer<SyndEntry, String> extractLinkFromFeed() {

        return new AbstractPayloadTransformer<>() {
            @Override
            protected String transformPayload(SyndEntry payload) {
                return new Article(
                        payload.getTitle(),
                        payload.getLink(),
                        payload.getContents()
                                .stream().map(SyndContent::getMode)
                                .collect(Collectors.joining("\n")), payload.getComments()).toString();
            }
        };

    }


    @Bean
    public CommandLineRunner runner() {
        return args -> logger.info("Starting 🎈");
    }
}
