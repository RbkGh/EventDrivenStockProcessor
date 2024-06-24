package com.stockprocessor.eventupdatelistener.repository;

import com.stockprocessor.eventupdatelistener.db.EventDetail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface EventDetailRepository extends MongoRepository<EventDetail,String> {
    @Query("{eventType:'?0'}")
    List<EventDetail> findAllByEventType(String eventType);
}
