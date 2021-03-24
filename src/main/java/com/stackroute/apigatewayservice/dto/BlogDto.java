package com.stackroute.apigatewayservice.dto;

public class BlogDto {
    private int blogId;
    private String title;
    private String content;
    private String author;
    private String statusMessage;
    private int countOfInformalWords;

    public BlogDto() {
    }

    public BlogDto(int blogId, String title, String content, String author,String statusMessage) {
        this.blogId = blogId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.statusMessage = statusMessage;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public int getCountOfInformalWords() {
        return countOfInformalWords;
    }

    public void setCountOfInformalWords(int countOfInformalWords) {
        this.countOfInformalWords = countOfInformalWords;
    }
}
