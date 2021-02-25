package de.carinaundzasch.zreader.service.proxyproviders;

public interface ImageProxyProvider {

    boolean isResponsible(String url);

    String getImageUrl(String url);
}
