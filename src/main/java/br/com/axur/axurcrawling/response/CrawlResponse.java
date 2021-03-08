package br.com.axur.axurcrawling.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CrawlResponse {

    public CrawlResponse(Long code, String url, String urlComposite) {
        this.code = code;
        this.url = url;
        this.urlComposite = urlComposite;
    }

    @JsonProperty("id")
    @ApiModelProperty(value = "Identifier code")
    private Long code;

    @JsonProperty("url")
    @ApiModelProperty(value = "Registered main url")
    private String url;

    @JsonProperty("urlComposite")
    @ApiModelProperty(value = "Urls that make up the informed main url")
    private String urlComposite;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlComposite() {
        return urlComposite;
    }

    public void setUrlComposite(String urlComposite) {
        this.urlComposite = urlComposite;
    }
}
