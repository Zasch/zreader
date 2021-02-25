package de.carinaundzasch.zreader.service.proxyproviders;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class DilbertProxyProvider implements ImageProxyProvider {

    @Override
    public boolean isResponsible(String url) {
        return url.toLowerCase().contains("dilbert.com");
    }

    @SneakyThrows
    @Override
    public String getImageUrl(String url) {
        Document doc = Jsoup.connect(url).get();
        var comic = doc.getElementsByClass("img-comic");
        return comic.attr("src");
    }
}
