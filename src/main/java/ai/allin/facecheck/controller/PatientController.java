package ai.allin.facecheck.controller;

import ai.allin.facecheck.model.Patient;
import ai.allin.facecheck.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

//환자 정보를 웹 인터페이스를 통해 등록하고 삭제하기 위한 컨트롤러 클래스
@Controller
public class PatientController {
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // 환자 등록 및 삭제를 위한 메서드 및 매핑 구현

    //메인페이지
    @GetMapping("/")
    public String main() {
        return "index";
    }

    // 환자 목록 조회
    @GetMapping("/list")
    public String listPatients(Model model) {
        List<Patient> patients = patientService.getAllPatients();
        model.addAttribute("patients", patients);
        return "patientDetails";
    }

    // 환자 등록 form 호출
    @GetMapping("/register")
    public String showPatientRegistrationForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patientRegistration"; // Name of the registration form HTML file
    }

    //환자 등록
    @PostMapping("/register")
    public String registerPatient(@ModelAttribute Patient patient) throws IOException {
        System.out.println(patient);
        if (patient.getPhoto() != null && !patient.getPhoto().isEmpty()) {
            String[] parts = patient.getPhoto().split(",");
            if (parts.length == 2) {
                String base64Image = parts[1];
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);

                // Generate a unique filename
                String filename = UUID.randomUUID().toString() + ".png";

                // Create the "uploads" directory if it doesn't exist
                Path uploadDirectory = Paths.get("src/main/resources/static/images");
                if (!Files.exists(uploadDirectory)) {
                    Files.createDirectories(uploadDirectory);
                }

                // Specify the complete file path
                Path imagePath = uploadDirectory.resolve(filename);

                // Write the image data to the file
                Files.write(imagePath, imageBytes);

                // Store the filename in the patient object, so it can be saved in the database
                patient.setPhoto(filename);
            }
        } else {
            System.out.println("Photo data is null or empty.");
        }

        // Assuming the service method saves the patient object, including the photo filename
        patientService.addPatient(patient);

        return "redirect:/list";
    }

    //환자 수정


    //환자 삭제
    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id);
        if (patient != null) {
            // Get the photo filename from the patient object
            String photoFilename = patient.getPhoto();

            // Delete the photo file if it exists
            if (photoFilename != null && !photoFilename.isEmpty()) {
                try {
                    Path photoPath = Paths.get("src/main/resources/static/images", photoFilename);
                    Files.deleteIfExists(photoPath);
                } catch (IOException e) {
                    // Handle the exception if needed
                    e.printStackTrace();
                }
            }

            // Delete the patient from the database
            patientService.deletePatient(id);
        }

        return "redirect:/list"; // Redirect back to the patient list after deleting
    }
}

