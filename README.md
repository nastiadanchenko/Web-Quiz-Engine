# Web Quiz Engine

## API /api

### POST /register

Register a new user

Consumes a JSON:

    {
      "email": "your@email.com", // Must have a valid format (with @ and .)
       "password": "secret" // Must have at least five characters
     }
     
### GET /quizzes/{id}

 Get a quiz by id
 
 Response example:
 
    {
      "id": 1,
      "title": "The Java Logo",
      "text": "What is depicted on the Java logo?",
      "options": ["Robot","Tea leaf","Cup of coffee","Bug"]
    }

### GET /quizzes

Get all existing quizzes in the service

Response contains a JSON array of quizzes:

    [
      {
        "id": 1,
        "title": "The Java Logo",
        "text": "What is depicted on the Java logo?",
        "options": ["Robot","Tea leaf","Cup of coffee","Bug"]
      },
      {
        "id": 2,
        "title": "The Ultimate Question",
        "text": "What is the answer to the Ultimate Question of Life, the Universe and Everything?",
        "options": ["Everything goes right","42","2+2=4","11011100"]
      }
    ]

### POST /quizzes

Post your own quiz

Consumes a JSON:

    {
      "title": "Coffee drinks",
      "text": "Select only coffee drinks.",
      "options": ["Americano","Tea","Cappuccino","Sprite"],
      "answer": [0,2]
    }

 Response is a JSON:
 
    {
      "id": 1,
       "title": "Coffee drinks",
       "text": "Select only coffee drinks.",
       "options": ["Americano","Tea","Cappuccino","Sprite"]
      }
      
### POST /quizzes/{id}/solve

Solve quiz by id specified by the path variable

Consumes a JSON:

    {
     "answer": [0,1]
    }
    
 Return:
 
 -    If the passed answer is correct:
 
    {
    "success":true,"feedback":"Congratulations, you're right!"
    }
    
 -    If the answer is incorrect:
 
    {
    "success":false,"feedback":"Wrong answer! Please, try again."
    }

### DELETE /quizzes/{id}

Delete your quiz by id specified by the path variable