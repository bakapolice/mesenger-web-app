package com.example.chatservice.controller;

import com.example.chatservice.mapper.PublicationToDtoMapper;
import com.example.chatservice.model.Publication;
import com.example.chatservice.payload.request.AddPublicationRequest;
import com.example.chatservice.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/publications")
@RequiredArgsConstructor
public class PublicationController {
    private final PublicationService publicationService;
    private final PublicationToDtoMapper mapper;

    @GetMapping("/{id}")
    public Publication getPublicationById(@PathVariable Long id){
        return publicationService.getPublicationById(id);
    }

    @GetMapping
    public List<Publication> getAllPublications(){
        return publicationService.getAllPublications();
    }

    @PostMapping("/new")
    public void addPublication(@RequestBody AddPublicationRequest request){
        Publication publication = mapper.AddPublicationRequestToPublication(request);
        System.out.println(publication.toString());
        publicationService.addPublication(mapper.AddPublicationRequestToPublication(request));
    }
}
