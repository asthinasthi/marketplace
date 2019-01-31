# Marketplace

# Available API's

## ```/project``` 
### 1. Create a Project <br/>
Type: POST

**Input/Body Params**
- name (String) : Name of the Project
- description (String): Brief Description of the Project
- deadline (yyyy-MM-ddTHH:MM:SS+0000): Deadline of the project
- sellerId (Integer): Seller uniqueId

**Response**
- requestId (UUID String): Unique requestId for every request made
- message (String): Message about the success/failure of the operation
- projectPOJO (JSON):
  - id (Integer): Project UniqueId
  - name (String)
  - description (String)
  - deadline (Timestamp)

**Example** <br/>
Input
```
{
"name": "Project Req: 238",
"description": "Required competent Financial Service Professional ...",
"deadline": "2019-02-01T05:30:00+0000",
"sellerId": 1
}
```
Output
```
{
    "requestId": "2fb56043-7c0a-42cc-a03b-bf661aac6375",
    "message": "Success",
    "projectPOJO": {
        "id": 608,
        "name": "Project Req: 238",
        "sellerId": 1,
        "description": "Required competent Financial Service Professional ...",
        "deadline": "2019-02-01T05:30:00.000+0000"
    }
}
```
### 2. Retreive Projects
Type GET <br/>
**Input Params**
By default no input params is required. Input params can be used to further filter the query.
- name (String): Name of the project that contains this string
- description (String): Filter by this description
- deadline (yyyy-MM-ddTHH:MM:SS+0000): Filter for this deadline
- nextId (Integer): Use 0 to get first Page. To scroll further send the nextId from the response
**Output**
- requestId (UUID String): Unique requestId for every request made
- message (String): Message about the success/failure of the operation
- total (Integer): Total results retrieved
- nextId (Integer): nextId that can be sent as a parameter to retrieve further pages
- data (List of projects)
  - id (Integer): UniqueId of the project
  - name (String): Name of the Project
  - sellerId (Integer): UniqueId of the Seller of this Project
  - description (String): Description of the project
  - deadline (yyyy-MM-ddTHH:MM:SS+0000): Deadline of the project
**Example** <br/>
Input
```{}```
Ouput
```
{
    "requestId": "24375985-4b25-41cf-8fda-3979029601f4",
    "message": "Success",
    "total": 100,
    "nextId": 306,
    "data": [
        {
            "id": 207,
            "name": "Name-0",
            "sellerId": 1,
            "description": "Description-0",
            "deadline": "2019-01-31T00:25:34.000+0000"
        },
        {
            "id": 208,
            "name": "Name-1",
            "sellerId": 2,
            "description": "Description-1",
            "deadline": "2019-01-31T00:25:34.000+0000"
        },
        {
            "id": 209,
            "name": "Name-2",
            "sellerId": 3,
            "description": "Description-2",
            "deadline": "2019-01-31T00:25:34.000+0000"
        },
        ...
    ]
}
```
## ```/bid ```
### 1. Create a Bid <br/>
Type POST
**Input Params**
**Output**
**Example**
Input
Output
