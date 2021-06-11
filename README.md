# EmployeeApplication
created rest endpoints with get, post, put, delete implementations. Post is created by reading data from randomuser/api and request body.

Post request - http://localhost:8080/employee
request body - {      
    "employeetitle": "a"  
}  
Response - employeeid

Get request for specific employee - http://localhost:8080/employee/{employeeid}
Get request for all employees (default sort title, page size -10, page No-0) - http://localhost:8080/employee

Put request - http://localhost:8080/employee/{employeeid}
request body - 
    {
        "id": 1,
        "employeename": {
            "title": "Mr-updated",
            "fname": "Joona-updated",
            "lname": "Leino-updated"
        },
        "employeetitle": "a-updated",
        "picture": {
            "large": "https://randomuser.me/api/portraits/men/45-updated.jpg",
            "medium": "https://randomuser.me/api/portraits/med/men/45-updated.jpg",
            "thumbnail": "https://randomuser.me/api/portraits/thumb/men/45-updated.jpg"
        }
    }
responsebody - updated employee object

delete request - http://localhost:8080/employee/1
responsebody - employeeid

H2 Console - http://localhost:8080/h2-console/login.jsp
driver class - org.h2.Driver
JDBC url - jdbc:h2:mem:testdb
username - sa
password - password
