package br.com.axur.axurcrawling;

import br.com.axur.axurcrawling.controller.CrawlingController;
import br.com.axur.axurcrawling.request.CrawRequest;
import br.com.axur.axurcrawling.response.CrawlResponse;
import br.com.axur.axurcrawling.service.CrawService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CrawlingControllerTest {

    @Autowired
    private MockMvc mvc;

    @InjectMocks
    private CrawlingController controller;

    @Mock
    private CrawService crawService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void getCrawlTest() throws Exception {
        when(crawService.getUrlById(anyLong())).thenReturn(getCrawlResponse());

        mvc.perform(MockMvcRequestBuilders.get("/crawl/1").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        verify(crawService).getUrlById(anyLong());
    }

    @Test
    public void saveCrawlTest() throws Exception {

        String json = getCrawRequestJson();

        when(crawService.saveCraw(any(CrawRequest.class))).thenReturn(getCrawlResponse());

        mvc.perform(MockMvcRequestBuilders.post("/crawl/").content(json).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

        verify(crawService).saveCraw(any(CrawRequest.class));
    }

    private CrawlResponse getCrawlResponse() {
       return new CrawlResponse(1l, "https://axur.com/pt/",
               "https://axur.com/pt/solucoes/apps-falsos/, "
                       + "https://axur.com/pt/solucoes/apropriacao-de-identidade/");
    }

    private String getCrawRequestJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CrawRequest crawRequest = new CrawRequest();
        crawRequest.setUrl("https://axur.com/pt/");
        return  mapper.writeValueAsString(crawRequest);
    }
}
