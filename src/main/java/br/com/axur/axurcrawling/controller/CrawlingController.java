package br.com.axur.axurcrawling.controller;

import br.com.axur.axurcrawling.request.CrawRequest;
import br.com.axur.axurcrawling.response.CrawlResponse;
import br.com.axur.axurcrawling.service.CrawService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "Axur-Crawling")
public class CrawlingController {

    @Autowired
    private CrawService crawService;


    @ApiOperation(value = "Search terms on websites")
    @RequestMapping(path = "/crawl/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrawlResponse> getCrawl(@PathVariable Long id) {
        return new ResponseEntity<CrawlResponse>(crawService.getUrlById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Search terms on websites")
    @RequestMapping(path = "/crawl/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrawlResponse> postCrawl(CrawRequest request){
        return new ResponseEntity<CrawlResponse>(crawService.saveCraw(request), HttpStatus.CREATED);
    }
}
