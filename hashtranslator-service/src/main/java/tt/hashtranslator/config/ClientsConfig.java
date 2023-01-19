package tt.hashtranslator.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

/**
 * Represents clients configuration.
 */
@Configuration
public class ClientsConfig {
    private static final String MD5_DECODER_URL = "https://md5decrypt.net/en/Api";

    /**
     * Creates rest template client bean.
     *
     * @return new {@link RestTemplate}
     */
    @Bean
    public RestTemplate restTemplateClient() {
        return new RestTemplate();
    }

    /**
     * Creates hash decoder client bean.
     *
     * @return configured {@link WebClient}
     */
    @Bean
    public WebClient hashDecoderClient() {
        HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(client ->
                        client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                                .doOnConnected(connection -> connection
                                        .addHandlerLast(new ReadTimeoutHandler(10))
                                        .addHandlerLast(new WriteTimeoutHandler(10))));
        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
        return WebClient.builder()
                .baseUrl(MD5_DECODER_URL)
                .clientConnector(connector)
                .build();
    }
}
