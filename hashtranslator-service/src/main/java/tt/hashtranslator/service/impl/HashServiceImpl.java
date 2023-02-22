package tt.hashtranslator.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tt.hashtranslator.entity.Application;
import tt.hashtranslator.repository.ApplicationRepository;
import tt.hashtranslator.service.HashService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.OK;
import static reactor.core.publisher.Mono.just;

/**
 * Implementation of {@link HashService}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HashServiceImpl implements HashService {
    private final static String HASH_API_PATH = "/api.php";
    private final static String EMPTY_STRING = "";
    private final static String ERROR = "ERROR";
    private final static String HASH = "hash";
    private final static String HASH_TYPE = "hash_type";
    private final static String EMAIL = "email";
    private final static String CODE = "code";

    private final ApplicationRepository applicationRepository;
    private final WebClient hashDecoderClient;
    @Value("${hash-api.email}")
    private String hashApiEmail;
    @Value("${hash-api.code}")
    private String hashApiCode;
    @Value("${hash-api.hash-type}")
    private String hashType;

    @Async
    @Override
    public void decode(Application application) {
        log.debug("Start decoding hashes of application with id: {}", application.getId());
        List<String> resultHashes = decode(application.getHashes()).collect(toList()).share().block();
        List<String> validatedResultHashes = Objects.nonNull(resultHashes)
                ? getValidatedResultHashes(resultHashes)
                : new ArrayList<>(application.getHashes());
        application.setHashes(validatedResultHashes);
        applicationRepository.save(application);
    }

    private List<String> getValidatedResultHashes(List<String> resultHashes) {
        return resultHashes.stream().map(h -> h.startsWith(ERROR) ? EMPTY_STRING : h).collect(toList());
    }

    private Mono<String> sendRequestToDecode(String hash) {
        log.debug("Start sending request to decode hash: {}", hash);
        return hashDecoderClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(HASH_API_PATH)
                        .queryParam(HASH, hash)
                        .queryParam(HASH_TYPE, hashType)
                        .queryParam(EMAIL, hashApiEmail)
                        .queryParam(CODE, hashApiCode).build())
                .exchangeToMono(response ->
                        response.statusCode().equals(OK) ? response.bodyToMono(String.class) : just(EMPTY_STRING));
    }

    private Flux<String> decode(List<String> hashes) {
        return Flux.fromIterable(hashes).flatMap(this::sendRequestToDecode);
    }
}
