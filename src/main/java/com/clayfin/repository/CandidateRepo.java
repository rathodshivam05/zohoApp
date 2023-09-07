package com.clayfin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clayfin.entity.Candidate;
import com.clayfin.exception.CandidateException;

@Repository
public interface CandidateRepo extends JpaRepository<Candidate, Integer>{

	public Optional<Candidate> findByEmail(String email) throws CandidateException;

	
}
