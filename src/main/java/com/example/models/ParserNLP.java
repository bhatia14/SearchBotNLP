package com.example.models;

import java.util.List;

public class ParserNLP {
	private String analyzerId;
	private List<String> results;
	public String getAnalyzerId() {
		return analyzerId;
	}
	public void setAnalyzerId(String analyzerId) {
		this.analyzerId = analyzerId;
	}
	public List<String> getResults() {
		return results;
	}
	public void setResults(List<String> results) {
		this.results = results;
	}

}
