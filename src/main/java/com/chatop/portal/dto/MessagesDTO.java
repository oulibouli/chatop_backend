package com.chatop.portal.dto;

public class MessagesDTO {

    private String message;
    private Integer rental_id;
    private Integer user_id;
    
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Integer getRental_id() {
        return rental_id;
    }
    public void setRental_id(Integer rental_id) {
        this.rental_id = rental_id;
    }
    public Integer getUser_id() {
        return user_id;
    }
    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
    
}
