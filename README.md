# Producer

## Tech Stack
- SpringBoot
- RabbitMQ

## Process
1. Send request to Twitter
2. Parse API response for data analysis
3. Send to Consumer

## Commit Rule
```bash
type(타입) : title(제목)

body(본문, 생략 가능)

See also : #issue, ...(참고 이슈, 생략 가능)
##################################################
    types = {
      feat : 새로운 기능에 대한 커밋
      fix : 버그 수정에 대한 커밋
      build : 빌드 관련 파일 수정에 대한 커밋
      chore : 그 외 자잘한 수정에 대한 커밋
      ci : CI관련 설정 수정에 대한 커밋
      docs : 문서 수정에 대한 커밋
      style : 코드 스타일 혹은 포맷 등에 관한 커밋
      refactor :  코드 리팩토링에 대한 커밋
      test : 테스트 코드 수정에 대한 커밋
    }

ex)
feat: add regex method / fix: update twitter info
```