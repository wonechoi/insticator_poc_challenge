# questions-app-ws
To manage various types of questions and serve them up to a widget.

## Prerequistites
Spring Tool Suite 4, Postman

## Running the tests
This will give you the instuction to run this project on your local machine for testing purposes.

### Access H2 Database
* localhost:8080/h2-console
* JDBC URL: jdbc:h2:mem:testdb
* userID: wony
* password: wony

### Creating sample questions
Common header:
  "Content-Type: application/json" _(Required)_
  "Accept: application/json"
  
1. Create a Site  
    POST  
    http://localhost:8080/sites  
    {"url": "www.bob.com"}  
      
2. Create three questions for the Site  
    POST  
    http://localhost:8080/questions  
    {  
      "siteId":1,  
      "question": "How many legs a pig have?",  
      "type": "Trivia"  
    }  
    {  
      "siteId":1,  
      "question": "Who is the most beauty?",  
      "type": "Poll"  
    }  
    {  
      "siteId":1,  
      "question": "Please tell us a bit about yourself",  
      "type": "Matrix"  
    }  
      
3. Create some header for the question of the Matrix type  
    POST  
    http://localhost:8080/questions/4/headers  
    {  
      "headerType":"row",  
      "headerOrder":"0",  
      "header":"Age/Gender"  
    }  
    {  
      "headerType":"row",  
      "headerOrder":"1",  
      "header":"< 18"  
    }  
    {  
      "headerType":"row",  
      "headerOrder":"2",  
      "header":"18 to 35"  
    }  
    {  
      "headerType":"row",  
      "headerOrder":"3",  
      "header":"35 to 55"  
    }  
    {  
      "headerType":"row",  
      "headerOrder":"3",  
      "header":"> 55"  
    }  
    {  
      "headerType":"col",  
      "headerOrder":"1",  
      "header":"Female"  
    }  
    {  
      "headerType":"col",  
      "headerOrder":"2",  
      "header":"Male"  
    }  
    
4. Create some answer for the questions  
    POST  
    http://localhost:8080/questions/2/answers  
    {"answer": "4 toes","isCorrectAnswer": true}  
    {"answer": "3 toes","isCorrectAnswer": false}  
    {"answer": "They do not have toes silly","isCorrectAnswer": false}  
  
    POST  
    http://localhost:8080/questions/3/answers  
    {"answer": "It's me","isCorrectAnswer": true}  
    {"answer": "Emma Stone","isCorrectAnswer": true}  
    {"answer": "Scarlett Johansson","isCorrectAnswer": true}  
  
    POST  
    http://localhost:8080/questions/4/answers  
    {"answer": "Female < 18","isCorrectAnswer": true}  
    {"answer": "Female 18 to 35","isCorrectAnswer": true}  
    {"answer": "Female 35 to 55","isCorrectAnswer": true}  
    {"answer": "Female > 55","isCorrectAnswer": true}  
    {"answer": "Male < 18","isCorrectAnswer": true}  
    {"answer": "Male 18 to 35","isCorrectAnswer": true}  
    {"answer": "Male 35 to 55","isCorrectAnswer": true}  
    {"answer": "Male > 55","isCorrectAnswer": true}  
      
5. Create an answer from user  
   (For get the site uuid, you can check h2 database   
    or  
    The site uuid for "www.bob.com" and two different uuids for user uuids are printed on the Console log after creating new site at 1.)  
     
    POST  
    http://localhost:8080/widgets/{Site UUID}/{User UUID}  
    {  
      "questionId":"2",  
      "answerId":"12"  
    }  
      
### Test case  
1. To get a list of all questions brings related answers and headers nested in each question  
    GET  
    http://localhost:8080/questions  
2. To get an unique question for a specific user  
    GET  
    http://localhost:8080/widgets/{site_UUID}/{user_UUID}  
3. To save a user response  
    POST  
    http://localhost:8080/widgets/{site_UUID}/{user_UUID}  
    {  
      "questionId": ,  
      "answerId":  
    }  
4. You can repeat 2 and 3 to check if a user can always see a unique question as long as one exists.  
  
5. To get answers for a question  
    GET  
    http://localhost:8080/questions/{question_id}/answers  
6. To get headers for a question  
    GET  
    http://localhost:8080/questions/{question_id}/headers  
7. To delete an answer  
    DELETE  
    http://localhost:8080/questions/{answer_id}/answers  
8. To delete a header  
    DELETE  
    http://localhost:8080/questions/{header_id}/headers  
9. To delete a question occurs deleting related answers, headers and results together.  
    DELETE  
    http://localhost:8080/questions/{question_id}  
10. To run unit test cases  
    WidgetServiceImplTest: for testing WidgetServiceImpl  
  
## Assumptions  
1. Questions, answers can be deleted **only when** any result related them has not been saved.  
  
## Any security considerations  
1. To create web tokens or authentication filter to prevent abuse managing data of questions, answers and headers.  
  
## What to scale this solution  
1. To give detailed information for each error, create custom exceptions   
  such as Question-Not-Found-Exception for the case question cannot be found when an answer is created.  
2. For user convenience in front-end, create APIs which accept a list of answers or headers and process it.  
3. To send the statistical results of a question after saving a response from a user  
