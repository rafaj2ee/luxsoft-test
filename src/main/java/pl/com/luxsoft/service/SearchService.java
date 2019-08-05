package pl.com.luxsoft.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import pl.com.luxsoft.dto.ResultDTO;

@Service
public class SearchService {

    @Value("${url.google}")
    private String google;
    @Value("${url.itunes}")
    private String itunes;
    
    public List<ResultDTO> search(String term) throws Exception {
    	List<ResultDTO> list = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        //restTemplate.getForEntity(url, responseType)
        ResponseEntity<String> googleReturn = restTemplate.exchange(google, HttpMethod.GET, entity, String.class, term);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, false);
        try {
			JsonNode rootNodeGoogle = mapper.readTree(googleReturn.getBody());
			ArrayNode googleNode = (ArrayNode) rootNodeGoogle.get("items");
			int counter1 = 0;
			for(JsonNode node:googleNode) {
				ResultDTO resultDTO = new ResultDTO();
				resultDTO.setAuthor(node.findValue("volumeInfo").findValue("authors")!=null?node.findValue("volumeInfo").findValue("authors").toString().replaceAll("[^\\w\\d\\s]", ""):"");
				resultDTO.setTitle(node.findValue("volumeInfo").findValue("title")!=null?node.findValue("volumeInfo").findValue("title").toString().replaceAll("[^\\w\\d\\s]", ""):"");
				resultDTO.setInformation(node.findValue("kind")!=null?node.findValue("kind").toString().replaceAll("[^\\w\\d\\s]", ""):"");
				list.add(resultDTO);
				counter1++;
				if(counter1==5) {
					break;
				}
			}
	        ResponseEntity<String> itunesReturn = restTemplate.exchange(itunes.replace(":term", term), HttpMethod.GET, entity, String.class);
			JsonNode rootNodeItunes = mapper.readTree(itunesReturn.getBody());
			ArrayNode iTunesNode = (ArrayNode) rootNodeItunes.get("results");
			int counter2 = 0;
			for(JsonNode node:iTunesNode) {
				ResultDTO resultDTO = new ResultDTO();
				resultDTO.setAuthor(node.findValue("artistName")!=null?node.findValue("artistName").toString().replaceAll("[^\\w\\d\\s]", ""):"");
				resultDTO.setTitle(node.findValue("collectionName")!=null?node.findValue("collectionName").toString().replaceAll("[^\\w\\d\\s]", ""):"");
				resultDTO.setInformation(node.findValue("collectionType")!=null?node.findValue("collectionType").toString().replaceAll("[^\\w\\d\\s]", ""):"");
				list.add(resultDTO);
				counter2++;
				if(counter2==5) {
					break;
				}
			}
		} catch (IOException e) {
			throw e;
		}
        return list;
    }


}
