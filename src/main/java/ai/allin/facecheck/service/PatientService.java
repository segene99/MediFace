package ai.allin.facecheck.service;

import ai.allin.facecheck.model.Patient;
import ai.allin.facecheck.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//환자 관련 비즈니스 로직을 처리, 리포지토리를 사용하여 데이터를 조작
@Service
public class PatientService {
    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllPatients() {
        // 모든 환자 정보를 조회
        return patientRepository.findAll();
    }

    public void addPatient(Patient patient) {
        // 환자 정보 추가
        patientRepository.save(patient);
    }

    public void deletePatient(Long seq) {
        // 환자 정보 삭제
        patientRepository.deleteById(seq);
    }


    public Patient getPatientById(Long id) {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        return patientOptional.orElse(null);
    }

    public String findImageFilenameByPatientId(String patientId) {
        Optional<Patient> patientOptional = patientRepository.findById(Long.valueOf(patientId));

        if (patientOptional.isPresent()) {
            return patientOptional.get().getPhoto();
        }

        return null;
    }

    public Patient findPatientByPhoneNum(String phoneNum) {
        return patientRepository.findByPhoneNum(phoneNum);
    }
}
