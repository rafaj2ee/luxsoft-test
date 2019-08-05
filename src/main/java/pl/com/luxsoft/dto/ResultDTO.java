package pl.com.luxsoft.dto;

import java.io.Serializable;

import lombok.Data;
@Data
public class ResultDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4536131393424515470L;
	private String title;
	private String author;
	private String information;
}
