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
- **Return** : Long

## 4. 일정 삭제
- **URL** :'/api/schedule/{id}'
- **Method** : DELETE
- **Return** : Long

---
# ERD

## 개체
- Schedule
- Users
## 속성
- Schedule : Schedule_id, name, date, schedule, password, create_date, modified_date 
- Users : user_id, name
## 관계
- 사용자는 일정을 작성, 조회, 수정, 삭제할 수 있다.

![ERD](https://ifh.cc/v-TYyqdZ)
 