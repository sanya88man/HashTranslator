package tt.hashtranslator.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tt.hashtranslator.entity.Application;
import tt.hashtranslator.repository.ApplicationRepository;
import tt.hashtranslator.service.HashService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HashServiceImpl implements HashService {
    private static final String MD5_URL = "md5 url";

    private final ApplicationRepository applicationRepository;
    private final RestTemplate client;

    @Async
    @Override
    public void decode(Application application) {
        List<String> resultHashes = new ArrayList<>();
        application.getHashes().forEach(h -> {
            String response = client.getForObject(MD5_URL, String.class);
            resultHashes.add(response);
        });
        application.setHashes(resultHashes);
        applicationRepository.save(application);
    }
}
