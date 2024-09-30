# API 명세서

## 1. 일정 생성
- **URL** : '/api/schedule'
- **Method** : POST
- **Return** : ScheduleResponseDto

## 2. 일정 조회
- **URL** :'/api/schedule'
- **Method** : GET
- **Return** : List<ScheduleResponseDto'>

## 3. 일정 수정
- **URL** : '/api/schedule/{id}'
- **Method** : PUT
- **Return** :

## 4. 일정 삭제
- **URL** :'/api/schedule/{id}'
- **Method** : DELETE
- **Return** :

---
# ERD

## 개체
- 일정
- 사용자
## 속성
- 일정 : 작성자, 내용, 작성일, 수정일
- 사용자 : 작성자
## 관계
- 사용자는 일정을 작성, 조회, 수정, 삭제할 수 있다.

![ERD](https://ifh.cc/v-bjmwNF)
 