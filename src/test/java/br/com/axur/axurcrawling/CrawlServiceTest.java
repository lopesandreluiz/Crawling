package br.com.axur.axurcrawling;

import br.com.axur.axurcrawling.exceptions.RegisterNotFoundException;
import br.com.axur.axurcrawling.model.CrawEntity;
import br.com.axur.axurcrawling.repository.CrawRepository;
import br.com.axur.axurcrawling.request.CrawRequest;
import br.com.axur.axurcrawling.response.CrawlResponse;
import br.com.axur.axurcrawling.service.CrawService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CrawlServiceTest {

    @Autowired
    private MockMvc mvc;

    @InjectMocks
    private CrawService crawService;

    @Mock
    private CrawRepository crawRepository;



    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(crawService).build();
    }

    @Test
    public void saveCrawlTest()  {
        when(crawRepository.save(any(CrawEntity.class))).thenReturn(getCrawEntity());
        crawService.saveCraw(getCrawRequest());
        verify(crawRepository).save(any(CrawEntity.class));
    }

    @Test(expected= RegisterNotFoundException.class)
    public void getUrlByIdTestNotFound()  {
        when(crawRepository.findById(anyLong())).thenReturn(Optional.empty());
        crawService.getUrlById(anyLong());
        verify(crawRepository).save(any(CrawEntity.class));
    }

    @Test
    public void getUrlByIdTestSucess()  {
        when(crawRepository.findById(anyLong())).thenReturn(Optional.of(getCrawEntity()));
        crawService.getUrlById(anyLong());
        verify(crawRepository).findById(anyLong());
    }

    private CrawlResponse getCrawlResponse() {
       return new CrawlResponse(1l, "https://axur.com/pt/",
               "https://axur.com/pt/solucoes/apps-falsos/, "
                       + "https://axur.com/pt/solucoes/apropriacao-de-identidade/");
    }

    private CrawRequest getCrawRequest(){
        CrawRequest crawRequest = new CrawRequest();
        crawRequest.setUrl("https://axur.com/pt/");
        return crawRequest;
    }

    private CrawEntity getCrawEntity(){
        CrawEntity entity = new CrawEntity();
        entity.setUrl("https://axur.com/pt/");
        entity.setIncludeDate(LocalDateTime.now());
        return entity;
    }
}
