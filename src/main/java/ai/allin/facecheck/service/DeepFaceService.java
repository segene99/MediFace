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

            System.out.println("=========어떤거 보내니?====" + requestBody);
            ResponseEntity<Map> response = restTemplate.exchange(analyzeUrl, HttpMethod.POST, entity, Map.class);
            System.out.println("=========어떤거 받니?====" + response);
            return response.getBody();

        } catch (RestClientException e) {
            // 오류 처리 및 로깅
            throw new RuntimeException("외부 API 호출 중 오류가 발생했습니다.", e);
        }
    }

    public Map<String, Object> verifyImage(String img1Path, String img2Path) {
        try {
            // verifyImage용 URL 생성
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

    // 이미지 경로를 가져오는 메서드
    public String getPatientImagePathByPhoneNum(String phoneNum) {
        // phoneNum을 사용하여 환자를 DB에서 검색
        Patient patient = patientService.findPatientByPhoneNum(phoneNum);

        if (patient != null) {
            return patient.getPhoto(); // 환자의 사진 경로를 반환
        }

        return null; // 환자가 없을 경우 null 반환 또는 예외 처리
    }

}
