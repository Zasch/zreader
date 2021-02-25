package de.carinaundzasch.zreader.service;

import de.carinaundzasch.zreader.service.proxyproviders.ImageProxyProvider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageProxyService {

    private final RestTemplateBuilder templateBuilder;

    private final List<ImageProxyProvider> providers;

    @SneakyThrows
    private void getImage(String url) {
        Document doc = Jsoup.connect(url).get();
        var comic = doc.getElementsByClass("img-comic");
        var src = comic.attr("src");

        var img = src.substring(src.lastIndexOf('/'));

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<byte[]> response = templateBuilder.build()
                    .exchange(src, HttpMethod.GET, entity, byte[].class);
            Files.write(Paths.get("/Users/samu/Desktop/" + img), Objects.requireNonNull(response.getBody()));
        } catch (Exception e) {
            log.warn("Cannot download image from {}", url, e);
        }
    }

    public String getImageUrl(String url) {
        for (var p : providers) {
            if (p.isResponsible(url)) {
                return p.getImageUrl(url);
            }
        }
        return "";
    }

    public boolean isImageProxyNeeded(String url) {
        return providers.stream().anyMatch(p -> p.isResponsible(url));
    }
}
