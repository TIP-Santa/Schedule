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
 