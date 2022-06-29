package com.example.chatservice.service;

import com.example.chatservice.entity.PublicationEntity;
import com.example.chatservice.exception.PublicationNotFoundException;
import com.example.chatservice.mapper.PublicationToEntityMapper;
import com.example.chatservice.model.Publication;
import com.example.chatservice.repository.PublicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultPublicationService implements PublicationService {
    private final PublicationRepository publicationRepository;
    private final PublicationToEntityMapper publicationMapper;

    @Override
    public Publication getPublicationById(Long id) {
        PublicationEntity publicationEntity = publicationRepository
                .findById(id) //прочитать запись из БД по первичному ключу id
                .orElseThrow(() -> new PublicationNotFoundException("Publication not found: id = " + id));
        return  publicationMapper.publicationEntityToPublication(publicationEntity);
    }

    @Override
    public List<Publication> getAllPublications() {
        Iterable<PublicationEntity> iterable = publicationRepository.findAll(); //прочитать все записи из БД и вернуть их в виде списка

        ArrayList<Publication> publications = new ArrayList<>();
        for (PublicationEntity publicationEntity : iterable) {
            publications.add(publicationMapper.publicationEntityToPublication(publicationEntity));
        }

        return publications;
    }

    @Override
    public void addPublication(Publication publication) {
        PublicationEntity publicationEntity = publicationMapper.publicationToPublicationEntity(publication);
        publicationRepository.save(publicationEntity); //сохранить объект в БД
    }
}
