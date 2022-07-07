# Producer

## Tech Stack
- SpringBoot
- RabbitMQ

## Tasks
1. Send request to Twitter
2. Send response from (1) to Python NLP Server
3. Send the result to Vue Component

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


## System Architecture 

Web: Spring boot
NLP: Python (FAST API)
WAS: Spring boot 

NLP and WAS servers are resource-intensive which is why we separated these two. 
Each request/response is colored differently to show each task
- Green: Axios requests for newest chart data
- Red: Spring requests for newest tweets about a given keyword (from user) and receives latest tweets
- Blue: Processed text is sent to python NLP Servers. NLP models analyse sentiment of the tweets. (eg. Positive, Negative, Neutral)
- Purple: Spring saves latest data about the keyword

![image](https://user-images.githubusercontent.com/58447982/175815663-f00fd396-58fc-44f6-b495-a944cc1c6049.png)
