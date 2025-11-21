# 파일 확장자 차단 시스템

파일 확장자 기반 업로드 제한 관리 시스템

## 📋 프로젝트 개요

파일 업로드 시 위험한 확장자를 차단하기 위한 웹 애플리케이션입니다.

- 고정 확장자 관리 (bat, cmd, com, cpl, exe, scr, js 등)
- 커스텀 확장자 추가/삭제 (최대 200개)
- 커스텀 확장자 중복 등록 확인 (무조건 소문자로 변환)
- 이전에 등록된 적이 있는 커스텀 확장자에 대한 처리

<br>

## 🚀 기술 스택

### Backend

- **Java 21**
- **Spring Boot**
- **MyBatis**
- **PostgreSQL**
- **Jasypt**

### Frontend

- **Thymeleaf**
- **Vanilla JavaScript**

### Security

- **Spring Security**
- **XSS Filter**
- **Apache Commons Text**

### Deployment

- **Railway** - 프로덕션 배포
- **Docker/Nixpacks** - 컨테이너화

<br>

## 🛡️ 보안 기능

### XSS (Cross-Site Scripting) 방어

- HTML 특수문자 이스케이프 처리
- \`HTMLCharacterEscapes\` (Jackson)
- Apache Commons Text 활용
- 모든 JSON 응답에 전역 적용

### SQL Injection 방어

- MyBatis \`#{}\` 사용으로 PreparedStatement 생성
- \`\${}\` 사용 금지

## 📁 프로젝트 구조

```
src/main/
├── java/com/cyantree/check/
│ ├── api/ # Rest API 관련
│ ├── web/
│ ├── config/ # 설정 클래스
│ └── secure/ # XSS 보안 관련
└── resources/
├── config/
│ ├── application.properties
│ ├── local # profile : local
│ └── test  # profile : test
├── sql/ # MyBatis SQL 매퍼 XML
├── templates/ # Thymeleaf 템플릿
└── static/css/ # CSS 파일
```

<br>

## 🔧 환경 설정

### Profile 구성

#### Local (개발 환경)

```properties
spring.profiles.active=local
```

- Swagger UI 활성화
- 로컬 PostgreSQL 연결
- 스케줄러 비활성화

#### Test (프로덕션 환경)

```shell
java -Dspring.profiles.active=test ...
```

- Swagger UI 비활성화
- Railway PostgreSQL 연결
- 스케줄러 활성화

<br>

## 📊 API 엔드포인트

### 고정 확장자 관리

```
POST /api/update/fix

- 고정 확장자 상태 변경
- Request Body: { seqno, extensionName, isBlocked }
```

### 커스텀 확장자 관리

```
POST /api/insert/custom

- 커스텀 확장자 추가
- Request Body: { extensionName, isBlocked }

POST /api/del/custom

- 커스텀 확장자 삭제
- Request Body: { extensionName, isBlocked }
```

<br>

## 🏷️ 추가 요건 정리

### 커스텀 확장자 중복 등록 확인

```javascript
// 중복 체크 (대소문자 구분 없이)
const fixedExtensions = Array.from(
  document.querySelectorAll('input[name="fixedExtension"]')
).map((checkbox) => checkbox.value.toLowerCase());
if (fixedExtensions.includes(extensionName.toLowerCase())) {
  alert("고정 확장자로 이미 등록되어 있습니다.");
  return;
}

// 커스텀 확장자와 중복 체크 (대소문자 구분 없이)
const existingExtensions = Array.from(currentTags).map((tag) =>
  tag.querySelector(".name").textContent.toLowerCase()
);
if (existingExtensions.includes(extensionName.toLowerCase())) {
  alert("이미 등록된 커스텀 확장자입니다.");
  return;
}
```

- 기본적으로 스크립트를 통해 공백 제거 및 소문자로 변경 실시 후 비교

<br>

```sql
CREATE UNIQUE INDEX idx_extension_name ON file_extension(extension_name);
```

- 테이블 생성 시 동일 동일 확장자명을 가질 수 없도록 제약 조건 설정

---

### 이전에 등록된 적이 있는 커스텀 확장자에 대한 처리

- 삭제 버튼(X 버튼) 클릭 시 실제 DB 상에서 삭제하지 않음
- is_blocked 값 false 처리

```sql
CREATE TABLE IF NOT EXISTS file_extension (
    seqno BIGSERIAL PRIMARY KEY,
    extension_name VARCHAR(200) NOT NULL,
    is_fixed BOOLEAN NOT NULL DEFAULT FALSE,
    is_blocked BOOLEAN NOT NULL DEFAULT FALSE,
    update_dt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
...
COMMENT ON COLUMN public.file_extension.is_blocked is '차단 여부 확인';
```

- DB 상에서 실제 삭제의 경우, <span style="color:red">**차단 상태가 아니며, 30일 이상 상태가 변경되지 않은**</span> 데이터에 한하여 스케줄러가 삭제

## 📝 참고 자료

### XSS 방어 구현

- [HTMLCharacterEscapes 참조 블로그](https://shinechan.tistory.com/entry/Spring-XSSCross-site-scripting-JSON-%EB%8D%B0%EC%9D%B4%ED%84%B0-XSS-%EC%B2%98%EB%A6%AC-%ED%95%98%EA%B8%B0)
- [XssFilter 참조 이슈](https://github.com/dlask913/vue3-spring-project/issues/51)

### 기술 문서

- [Spring Boot 3.x Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [MyBatis Spring Boot](https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)
- [Apache Commons Text](https://commons.apache.org/proper/commons-text/)

## 👤 개발자

- 프로젝트 링크: https://check-production-dbf2.up.railway.app/web/main
