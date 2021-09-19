# daouAPI 테스트 과제
## 스텍
#### Java  1.8
#### Srping  4.10.0.RELEASE
#### Spring Boot 2.5.4
#### Junit5
#### h2database(embeded)
#### build : gradle
#### Enconding : UTF-8
#
## API 테스트 
#### 크롬 확장 프로그램 Talend API Tester
#
## 프로젝트 다운로드 전 유의사항
### 1.프로젝트 인코딩 타입이 UTF-8로 되어있지 않으면 프로젝트 내 한글파일이 깨지는걸 방지하기 위해 미리 인코딩을 맞춰주어야 한다.
## 프로젝트 다운로드 2가지 방식
### Git Clone 방식
#### 1.깃허브 url을통해 이클립스에서 repository를 연결한다.
#### 2.master branch를 선택하고 내부 repository를 추가한다.
#### 3.추가된 repository에서 daouAPI 폴더 하위의 'daou' 폴더를 Gradle 프로젝트로 import 한다.
## 
### 소스파일 다운로드 방식
#### 1.소스파일을 다운로드 받는다.
#### 2.압축해제한다.
#### 3.이클립스 기준 project import -> Gradle -> Import Gradle Project를 선택한다.
#### 4.압축해제된 폴더안의 'daou'폴더를 선택한다.
## 프로젝트 구동 전
### lombok.jar 파일이 이클립스에 적용되어 있어야 오류가 나지 않는다.
## 프로젝트 구동
### Run as -> Spring Boot APP 으로 프로젝트를 구동 한다.
## Database & TEST DATA
### 내장형 H2데이터베이스를 사용하고 있다.
### 생성한 Entity 파일을 토대로 테이블을 자동으로 생성한다.
### TestDataInsert.java 파일에서 테스트 데이터를 APP 구동시에 넣어주고 있다.
## API PORT
### port는 기본포트 8080을 사용하고 있다.

## API TEST

## 항목  | Method  | URL
### 조직도 조회 | GET | http://{서버URL}/org/organizations
### 부서 추가	| POST	| http://{서버URL}/org/dept   
### 부서 수정	| PUT |	http://{서버URL}/org/dept/{deptId}
### 부서 삭제	| DELETE	| http://{서버URL}/org/dept/{deptId}
#### Body Example) {  "code" : "A140",	"name":"테스트팀", 	"type":"Division",	"parentId":1 }
### 부서원 추가	| POST	| http://{서버URL}/org/member
### 부서원 수정	| PUT	| http://{서버URL}/org/member/{memberId}
### 부서원 삭제	| DELETE  |	http://{서버URL}/org/member/{memberId}
#### Body Example) { "name":"테스트인원", "manager": true, "team":[1,2]	}

