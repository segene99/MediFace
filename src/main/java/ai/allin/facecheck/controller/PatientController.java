package ai.allin.facecheck.controller;

import ai.allin.facecheck.model.Patient;
import ai.allin.facecheck.service.DeepFaceService;
import ai.allin.facecheck.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.springframework.web.bind.annotation.PostMapping;

//환자 정보를 웹 인터페이스를 통해 등록하고 삭제하기 위한 컨트롤러 클래스
@Controller
public class PatientController {
    private final PatientService patientService;
    private final DeepFaceService deepFaceService;

    @Autowired
    public PatientController(PatientService patientService, DeepFaceService deepFaceService) {

        this.patientService = patientService;
        this.deepFaceService = deepFaceService; // 초기화

    }

    // 환자 표정분석 페이지 호출
    @GetMapping("/analyzeForm")
    public String showAnalyzeForm() {
        return "patientAnalyze"; // Name of the HTML file
    }

    // 환자 등록인증 페이지 호출
    @GetMapping("/verifyForm")
    public String showVerifyForm() {
        return "patientVerify"; // Name of the HTML file
    }

    // 환자 본인확인 페이지 호출
    @GetMapping("/recognizeForm")
    public String showRecognizeForm() {
        return "patientRecognize"; // Name of the HTML file
    }


    // 환자 이미지 표정분석
    @PostMapping("/analyze")
    public String analyzeImage(@RequestParam String patientId, Model model) {
        if (patientId == null || patientId.isEmpty()) {
            model.addAttribute("message", "patientId is required");
            return "error"; // 에러 페이지를 만들어서 사용할 수도 있음
        }

        // 환자 ID를 기반으로 이미지 경로를 찾는 로직
//        String imgPath = findImagePathByPatientId(Long.valueOf(patientId)); // Convert String to Long
        //환자 id를 기반으로 photo컬럼 값 가져오기
        String imgPath = patientService.findImageFilenameByPatientId(patientId); // Convert String to Long

        //path 확인작업
        if (imgPath != null && imgPath.length() >= 6) {
            String firstThree = imgPath.substring(0, 3);
            String lastThree = imgPath.substring(imgPath.length() - 3);
            System.out.println("First 3 characters: " + firstThree);
            System.out.println("Last 3 characters: " + lastThree);
        } else {
            System.out.println("Invalid imgPath: " + imgPath);
        }

        if (imgPath == null || imgPath.isEmpty()) {
            model.addAttribute("message", "Image not found for given patientId");
            return "error"; // 에러 페이지를 만들어서 사용할 수도 있음
        }

        Map<String, Object> analysisResult = deepFaceService.analyzeImage(imgPath);
        model.addAttribute("results", analysisResult.get("results"));

        return "patientAnalysisResult";
    }

    //환자 등록여부 검증
    @PostMapping("/verify")
    public String verifyImage(@RequestParam String phoneNum, @RequestParam String photo, Model model) {
        // 이미지 경로를 서비스를 통해 가져오기
        String img1Path = deepFaceService.getPatientImageByPhoneNum(phoneNum);

        if (img1Path == null) {
            model.addAttribute("error", "Patient not found for the given phone number");
            return "error"; // 에러 페이지로 이동
        }

        // 이미지 확인 작업을 서비스를 통해 수행
        Map<String, Object> verificationResult = deepFaceService.verifyImage(img1Path, photo);

        // 결과를 모델에 추가
        model.addAttribute("result", verificationResult);

        return "verifyResult"; // 결과 페이지로 이동
    }


    //환자 등록여부 검증
    @PostMapping("/recognize")
    public String recognizeImage(@RequestParam String photo, Model model) {

        // 이미지 확인 작업을 서비스를 통해 수행
        Map<String, Object> recognitionResult = deepFaceService.recognizeImage(photo);

        // 결과를 모델에 추가
        model.addAttribute("result", recognitionResult);

        return "patientRecognitionResult"; // 결과 페이지로 이동
    }

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
                String base64encoded = patient.getPhoto();
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
//                patient.setPhoto(filename);

                //set photo as base64 encoded strings
                patient.setPhoto(base64encoded);
            }
        } else {
            System.out.println("Photo data is null or empty.");
        }

        // Assuming the service method saves the patient object, including the photo filename
        patientService.addPatient(patient);

        return "redirect:/list";
    }


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

