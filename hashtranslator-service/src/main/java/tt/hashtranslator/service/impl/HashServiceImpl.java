package tt.hashtranslator.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tt.hashtranslator.entity.Application;
import tt.hashtranslator.repository.ApplicationRepository;
import tt.hashtranslator.service.HashService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HashServiceImpl implements HashService {
    private final static String HASH_API_EMAIL = "sanya88man@gmail.com";
    private final static String HASH_TYPE = "md5";
    private final static String HASH_API_CODE = "c48637088f7f771d";
    private final static String HASH_API_PATH = "/api.php";


    private final ApplicationRepository applicationRepository;
    private final WebClient hashDecoderClient;

    @Async
    @Override
    public void decode(Application application) {
        log.debug("Start decoding hashes of application with id: {}", application.getId());
        List<String> resultHashes = new ArrayList<>();
        application.getHashes().forEach(h -> {
            Mono<String> response = sendRequestToDecode(h);
            resultHashes.add(response.block());
        });
        application.setHashes(resultHashes);
        applicationRepository.save(application);
    }

    private Mono<String> sendRequestToDecode(String hash) {
        log.debug("Start sending request to decode hash: {}", hash);
        return hashDecoderClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(HASH_API_PATH)
                        .queryParam("hash", hash)
                        .queryParam("hash_type", HASH_TYPE)
                        .queryParam("email", HASH_API_EMAIL)
                        .queryParam("code", HASH_API_CODE).build())
                .retrieve().bodyToMono(String.class);
    }
}
