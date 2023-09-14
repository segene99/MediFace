package ai.allin.facecheck.repository;

import ai.allin.facecheck.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//환자 데이터를 데이터베이스에 저장하고 검색하기 위한 Spring Data JPA 리포지토리
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByPhoneNum(String phoneNum);
}

