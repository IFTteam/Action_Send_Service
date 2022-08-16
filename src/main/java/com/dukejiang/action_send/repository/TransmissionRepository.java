package com.dukejiang.action_send.repository;

import com.dukejiang.action_send.model.Transmission;
import com.dukejiang.action_send.model.Audience;
import com.dukejiang.action_send.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransmissionRepository extends JpaRepository<Transmission, String> {
}
