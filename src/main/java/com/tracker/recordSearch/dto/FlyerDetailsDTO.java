package com.tracker.recordSearch.dto;


public class FlyerDetailsDTO {
    private Long id;

    private String topic;

    private String details;

    private String documentUpload;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }
    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }

    public String getDocumentUpload() {
        return documentUpload;
    }
    public void setDocumentUpload(String documentUpload) {
        this.documentUpload = documentUpload;
    }
}
