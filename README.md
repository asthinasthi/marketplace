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

**Example**
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
## Bid
1. Create a Bid
