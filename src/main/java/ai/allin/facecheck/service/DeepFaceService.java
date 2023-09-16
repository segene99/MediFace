package ai.allin.facecheck.service;

import ai.allin.facecheck.model.Patient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeepFaceService {

    @Value("${deepface.api.url}")  // application.properties에서 설정
    private String API_URL; // API 서버 주소를 입력하세요.
    @Autowired
    private PatientService patientService;

    RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> analyzeImage(String imgPath) {
        try {
            // Check if imgPath starts and ends with single quotes
            if (imgPath.startsWith("'") && imgPath.endsWith("'")) {
                // Remove the single quotes from the beginning and end
                imgPath = imgPath.substring(1, imgPath.length() - 1);
            }

            // analyzeImage용 URL 생성
            String analyzeUrl = API_URL + "/analyze";

            // 요청 데이터 생성
            Map<String, Object> requestMap = new HashMap<>();

            requestMap.put("img_path", imgPath);
            requestMap.put("actions", List.of("age", "gender", "emotion", "race"));

            // 요청 JSON 데이터를 직접 생성
            JSONObject requestBody = new JSONObject(requestMap);

            // API 호출
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

            System.out.println("=========어떤거 보내니?====");
            ResponseEntity<Map> response = restTemplate.exchange(analyzeUrl, HttpMethod.POST, entity, Map.class);
            System.out.println("=========어떤거 받니?====");
            return response.getBody();

        } catch (RestClientException e) {
            // 오류 처리 및 로깅
            throw new RuntimeException("외부 API 호출 중 오류가 발생했습니다.", e);
        }
    }

    public Map<String, Object> verifyImage(String img1Path, String img2Path) {
        try {
            // verifyImage용 URL 생성

            // Check if imgPath starts and ends with single quotes
            if (img1Path.startsWith("'") && img1Path.endsWith("'")) {
                // Remove the single quotes from the beginning and end
                img1Path = img1Path.substring(1, img1Path.length() - 1);
            }
            // Check if imgPath starts and ends with single quotes
            if (img2Path.startsWith("'") && img2Path.endsWith("'")) {
                // Remove the single quotes from the beginning and end
                img2Path = img2Path.substring(1, img2Path.length() - 1);
            }


            String verifyUrl = API_URL + "/verify";


            // 요청 데이터 생성
            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("img1_path", img1Path);
            requestMap.put("img2_path", img2Path);
            requestMap.put("model_name", "Facenet");
            requestMap.put("detector_backend", "mtcnn");
            requestMap.put("distance_metric", "euclidean");

            // 요청 JSON 데이터를 직접 생성
            JSONObject requestBody = new JSONObject(requestMap);

            // API 호출
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

            ResponseEntity<Map> response = restTemplate.exchange(verifyUrl, HttpMethod.POST, entity, Map.class);
            return response.getBody();
        } catch (RestClientException e) {
            // 오류 처리 및 로깅
            throw new RuntimeException("외부 API 호출 중 오류가 발생했습니다.", e);
        }
    }


    public Map<String, Object> recognizeImage(String photo){
        try{
            // Check if imgPath starts and ends with single quotes
            if (photo.startsWith("'") && photo.endsWith("'")) {
                // Remove the single quotes from the beginning and end
                photo = photo.substring(1, photo.length() - 1);
            }

            java.lang.String recognizeUrl = API_URL + "/find";

            //customize your db path
            String dbPath = "/Users/segene/MediFace/src/main/resources/static/images";

            // 요청 데이터 생성
            Map<java.lang.String, java.lang.Object> requestMap = new HashMap<>();
            requestMap.put("img_path", photo);
            requestMap.put("db_path", dbPath);
            requestMap.put("model_name", "VGG-Face");
            requestMap.put("distance_metric", "cosine");
            requestMap.put("detector_backend", "opencv");
            requestMap.put("normalization", "base");

            // 요청 JSON 데이터를 직접 생성
            JSONObject requestBody = new JSONObject(requestMap);

            // API 호출
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<java.lang.String> entity = new HttpEntity<>(requestBody.toString(), headers);

            ResponseEntity<Map> response = restTemplate.exchange(recognizeUrl, HttpMethod.POST, entity, Map.class);
            return response.getBody();
        } catch (RestClientException e) {
            // 오류 처리 및 로깅
            throw new RuntimeException("외부 API 호출 중 오류가 발생했습니다.", e);
        }
    }

    // 이미지값을 phonenum으로 가져오는 메서드
    public String getPatientImageByPhoneNum(String phoneNum) {
        // phoneNum을 사용하여 환자를 DB에서 검색
        Patient patient = patientService.findPatientByPhoneNum(phoneNum);

        if (patient != null) {
            String photoFilename = patient.getPhoto();
            if (photoFilename != null && !photoFilename.isEmpty()) {
                // 이미지 파일 경로를 반환
                return photoFilename;
            }
        }

        return null; // 환자가 없거나 이미지 경로를 찾을 수 없는 경우 null 반환 또는 예외 처리
    }
}
