package com.oracle.api.topic.repository;

import com.oracle.api.topic.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TopicRepository extends JpaRepository<Topic, UUID> { }
