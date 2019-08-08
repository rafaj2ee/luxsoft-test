package pl.com.luxsoft.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import pl.com.luxsoft.dto.ResultDTO;

@Service
public class SearchService {
	private static final String VOLUME_INFO = "volumeInfo";
	private static final String AUTHORS = "authors";
	private static final String TITLE = "title";
	private static final String KIND = "kind";
	private static final String ITEMS = "items";
	private static final String RESULTS = "results";
	private static final String ARTIST_NAME = "artistName";
	private static final String COLLECTION_NAME = "collectionName";
	private static final String COLLECTION_TYPE = "collectionType";
	private static final String TERM = ":term";
	private static final String REG_EXP = "[^\\w\\d\\s]";
    @Value("${url.google}")
    private String google;
    @Value("${url.itunes}")
    private String itunes;
    
    public List<ResultDTO> search(String term) throws Exception {
    	List<ResultDTO> list = new ArrayList<>();
        RestTemplate restTemplate = restTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        //restTemplate.getForEntity(url, responseType)
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        try {
            ResponseEntity<String> googleReturn = restTemplate.exchange(google, HttpMethod.GET, entity, String.class, term);
        	if (googleReturn.getStatusCode() == HttpStatus.OK) {
	        	JsonNode rootNodeGoogle = mapper.readTree(googleReturn.getBody());
				ArrayNode googleNode = (ArrayNode) rootNodeGoogle.get(ITEMS);
				int counter1 = 0;
				for(JsonNode node:googleNode) {
					ResultDTO resultDTO = new ResultDTO();
					resultDTO.setAuthor(node.findValue(VOLUME_INFO).findValue(AUTHORS)!=null?node.findValue(VOLUME_INFO).findValue(AUTHORS).toString().replaceAll(REG_EXP, ""):"");
					resultDTO.setTitle(node.findValue(VOLUME_INFO).findValue(TITLE)!=null?node.findValue(VOLUME_INFO).findValue(TITLE).toString().replaceAll(REG_EXP, ""):"");
					resultDTO.setInformation(node.findValue(KIND)!=null?node.findValue(KIND).toString().replaceAll(REG_EXP, ""):"");
					list.add(resultDTO);
					counter1++;
					if(counter1==5) {
						break;
					}
				}
            }
		} catch (Exception e) {
		}
        try {
	        ResponseEntity<String> itunesReturn = restTemplate.exchange(itunes.replace(TERM, term), HttpMethod.GET, entity, String.class);
            if (itunesReturn.getStatusCode() == HttpStatus.OK) {
		        JsonNode rootNodeItunes = mapper.readTree(itunesReturn.getBody());
				ArrayNode iTunesNode = (ArrayNode) rootNodeItunes.get(RESULTS);
				int counter2 = 0;
				for(JsonNode node:iTunesNode) {
					ResultDTO resultDTO = new ResultDTO();
					resultDTO.setAuthor(node.findValue(ARTIST_NAME)!=null?node.findValue(ARTIST_NAME).toString().replaceAll(REG_EXP, ""):"");
					resultDTO.setTitle(node.findValue(COLLECTION_NAME)!=null?node.findValue(COLLECTION_NAME).toString().replaceAll(REG_EXP, ""):"");
					resultDTO.setInformation(node.findValue(COLLECTION_TYPE)!=null?node.findValue(COLLECTION_TYPE).toString().replaceAll(REG_EXP, ""):"");
					list.add(resultDTO);
					counter2++;
					if(counter2==5) {
						break;
					}
				}
            }
		} catch (Exception e) {
		}
        return list;
    }
    
    public RestTemplate restTemplate() {

    	SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        factory.setConnectTimeout(5000);
        factory.setReadTimeout(15000);

        return new RestTemplate(factory);
    }

}
