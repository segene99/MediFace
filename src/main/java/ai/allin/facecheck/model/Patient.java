package ai.allin.facecheck.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq") // 엔티티 클래스의 필드와 데이터베이스 열을 연결
    private Long id; // seq 열로 변경

    @Column(name = "name") // 엔티티 클래스의 필드와 데이터베이스 열을 연결
    private String name;

    @Column(name = "phone") // 엔티티 클래스의 필드와 데이터베이스 열을 연결
    private String phoneNum; // phone 열로 변경

    @Column(name = "photo", columnDefinition = "LONGTEXT")
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
