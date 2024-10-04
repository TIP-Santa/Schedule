# README
## 과제 소개
일정 관리 앱 서버입니다.<br>
일정을 작성 및 조회, 수정, 삭제가 가능합니다.<br>
일정을 작성하기 전 먼저 사용자 등록이 필요합니다.

## 기능
### Schedule
 - 일정 작성 가능
 - 일정 조회 가능
   - 페이지와 페이지 당 보여줄 일정 갯수를 조절 가능
   - 전체일정 조회 가능
   - 특정 `user` 일정 조회 가능
   - 특정 `date` 일정 조회 가능
   - 특정 `name` 일정 조회 가능
 - 일정 수정 가능
   - 일정 수정은 `name`, `schedule`만 수정 가능
   - 일정 수정 시 작성시 입력한 `password` 필요
 - 일정 삭제 가능
   - 일정 삭제 시 작성시 입력한 `password` 필요 

### Users
 - user 추가 가능
 - user 정보 조회 가능
   - 정보 조회는 `user_id`와 `password` 필요
 - user 정보 수정 가능
   - `name`과 `eamil`만 수정 가능
   - 수정 시 `password` 필요
 - user 정보 삭제 가능
   - 삭제 시 `password` 필요

---
# API 명세서
https://www.notion.so/Schedule-API-85e423564b1847edb264194587353a37

---
# ERD

## 개체
- Schedule
- Users
## 속성
- Schedule : `Schedule_key`, `user_id`, `name`, `date`, `schedule`, `password`, `create_date`, `modified_date` 
- Users : `user_key`, `user_id`, `name`, `email`, `password`, `create_date`, `modified_date`
## 관계
- 사용자는 일정을 작성, 조회, 수정, 삭제할 수 있다.

![ERD](https://ifh.cc/g/Md8CXg.png)
 