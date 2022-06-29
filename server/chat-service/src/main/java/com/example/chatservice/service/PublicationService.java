package com.example.chatservice.service;

import com.example.chatservice.model.Publication;

import java.util.List;

public interface PublicationService {
    Publication getPublicationById(Long id); // получить публикацию по id
    List<Publication> getAllPublications(); // получить все публикации
    void addPublication(Publication publication); // создать публикацию
}
