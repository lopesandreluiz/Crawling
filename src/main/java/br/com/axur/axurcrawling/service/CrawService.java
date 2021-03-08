package br.com.axur.axurcrawling.service;

import br.com.axur.axurcrawling.exceptions.RegisterNotFoundException;
import br.com.axur.axurcrawling.model.CrawEntity;
import br.com.axur.axurcrawling.repository.CrawRepository;
import br.com.axur.axurcrawling.request.CrawRequest;
import br.com.axur.axurcrawling.response.CrawlResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class CrawService {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) "
            + "AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

    @Autowired
    private CrawRepository crawRepository;


    public CrawlResponse saveCraw(CrawRequest request) {

        CrawEntity responseEntity =
                crawRepository.save(new CrawEntity(request.getUrl(), getCrawUrlComposite(request), LocalDateTime.now()));

        return new CrawlResponse(responseEntity.getId(), responseEntity.getUrl(), convertBlobForString(responseEntity.getUrlComposite()));
    }

    public CrawlResponse getUrlById(Long id) {

         Optional<CrawEntity> responseEntity =  crawRepository.findById(id);

         if (!responseEntity.isPresent()) {
             throw new RegisterNotFoundException();
         }

        return new CrawlResponse(responseEntity.get().getId(), responseEntity.get().getUrl(),
                convertBlobForString(responseEntity.get().getUrlComposite()));
    }

    private String getCrawUrlComposite(CrawRequest request) {

        StringBuilder elements = new StringBuilder();

        try {

            Document document = Jsoup.connect(request.getUrl()).userAgent(USER_AGENT).get();

            Elements elementLinks = document.select("a[href]");

            for (Element element : elementLinks) {
                elements.append(element.attr("abs:href")+", ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return elements.toString();
    }

    private String convertBlobForString(Blob urlComposite) {
        String response = "";
        if (Objects.nonNull(urlComposite)) {
            try {
                response = new String(urlComposite.getBytes(1l, (int) urlComposite.length()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return response;
    }
}
