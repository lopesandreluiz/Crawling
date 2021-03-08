package br.com.axur.axurcrawling.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Entity
public class CrawEntity {

    public CrawEntity() {
        super();
    }

    public CrawEntity(String url, String urlComposite, LocalDateTime includeDate) {
        this.url = url;
        try {
            this.urlComposite = new SerialBlob(urlComposite.getBytes());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.includeDate = includeDate;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String url;

    private Blob urlComposite;

    private LocalDateTime includeDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Blob getUrlComposite() {
        return urlComposite;
    }

    public void setUrlComposite(Blob urlComposite) {
        this.urlComposite = urlComposite;
    }

    public LocalDateTime getIncludeDate() {
        return includeDate;
    }

    public void setIncludeDate(LocalDateTime includeDate) {
        this.includeDate = includeDate;
    }
}
