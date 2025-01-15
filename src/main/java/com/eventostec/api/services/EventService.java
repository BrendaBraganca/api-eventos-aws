package com.eventostec.api.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.eventostec.api.domain.event.Event;
import com.eventostec.api.domain.event.EventRequestDTO;
import com.eventostec.api.domain.event.EventResponseDTO;
import com.eventostec.api.repositories.EventRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class EventService {

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private EventRepository repository;

    @Autowired
    private AddressService addressService;

    private File convertMultipartToFile (MultipartFile multipartFile) throws IOException{
        File convFile =  new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convFile;
    }

    private String uploadImg(MultipartFile multipartFile){
        String fileName = UUID.randomUUID() + "-"+ multipartFile.getOriginalFilename();

        try {
            File file =  this.convertMultipartToFile(multipartFile);
            s3Client.putObject(bucketName, fileName, file);
            file.delete();
            return s3Client.getUrl(bucketName, fileName).toString();
        } catch (Exception e){
            System.out.println("Bucket usado: " + bucketName);
            System.out.println("Erro ao subir arquivo");
        }
        return "";
    }

    public Event createEvent(EventRequestDTO data){
        String imgUrl = null;

        if(data.image() !=null){
            imgUrl = this.uploadImg(data.image());
        }

        Event newEvent = new Event();
        newEvent.setTitle(data.title());
        newEvent.setDescription(data.description());
        newEvent.setEventUrl(data.eventUrl());
        newEvent.setDate(new Date(data.date()));
        newEvent.setImgUrl(imgUrl);
        newEvent.setRemote(data.remote());
        repository.save(newEvent);

        if(!data.remote()){
            this.addressService.createAddress(data, newEvent);
        }

        return newEvent;

    }

    public List<EventResponseDTO> getUpComingEvents(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventsPage =  this.repository.findUpComingEvents(new Date(), pageable);
        return eventsPage.map(event -> new EventResponseDTO(event.getId(), event.getTitle(), event.getDescription(), event.getDate(),  "", "", event.getRemote(), event.getEventUrl(), event.getImgUrl())).stream().toList();
        
    }

    public List<EventResponseDTO> getFilteredEvents(int page, int size, String city, String uf, Date starDate, Date enDate){
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventsPage =  this.repository.findFilteredEvents( city, uf, enDate, enDate, pageable);

        return eventsPage.map(event -> new EventResponseDTO(event.getId(), event.getTitle(), event.getDescription(), event.getDate(),  "", "", event.getRemote(), event.getEventUrl(), event.getImgUrl())).stream().toList();
    }

}
