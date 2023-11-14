# API Documentation for UC15 Emergency Health Records
| Function | Endpoint | Method | Request Payload | Success Response | Error Responses |
|----------|----------|--------|------------------|-------------------|-----------------|
| Search Emergency Health Records | `/api/emergency_health_records/search` | `POST` | `searchType` (String), `searchQuery` (String) | `200 OK` - List of Patients | `400 Bad Request` - Invalid search type or query<br>`404 Not Found` - No matching patients<br>`401 Unauthorized` - User not authenticated |
| View Patient's Emergency Health Records | `/api/emergency_health_records/view/{patientMID}` | `GET` | `patientMID` (String) | `200 OK` - Patient's Emergency Health Record | `404 Not Found` - Patient not found<br>`401 Unauthorized` - User not authenticated |

# Logging

| Log Entry | Transaction Code | Verbose Description | Logged In MID | Secondary MID | Transaction Type | Patient Viewable |
|-----------|-------------------|----------------------|---------------|---------------|-------------------|-------------------|
| View Patient's Emergency Health Records | 1501 (HCP), 1502 (ER) | "HCP views a patient's Emergency Health Records" or "ER views a patient's Emergency Health Records" | MID of the authenticated user | MID of the patient being viewed | View | Yes |

# Data Format

| Field | Format |
|-------|--------|
| First Name | Up to 20 alpha characters and symbols -, ', and space |
| Last Name | Up to 30 alpha characters and symbols -, ', and space |
| Patient MID | Between 6 and 20 alpha characters and symbols - or _ |



## Authentication

Before making any requests, users (HCPs and Emergency Responders) need to authenticate through the iTrust2 system.

## 1. Search Emergency Health Records

- **Endpoint:** `/api/emergency_health_records/search`
- **Method:** `POST`
- **Request Payload:**
  - Parameters:
    - `searchType` (String): Type of search, either "name" or "username".
    - `searchQuery` (String): The search query for the specified search type.

- **Success Response:**
  - Status: 200 OK
  - Content:
    ```json
    {
      "patients": [
        {
          "firstName": "Siegwardof",
          "lastName": "Catarina",
          "mid": "siegofc",
          "age": 45,
          "dob": "1978-05-12",
          "gender": "Male",
          "bloodType": "AB+",
          "diagnoses": [
            {
              "code": "D001",
              "description": "Example Diagnosis 1",
              "date": "2023-11-14"
            },
            // More diagnoses...
          ],
          "prescriptions": [
            {
              "id": "P001",
              "medication": "Medicine ABC",
              "date": "2023-11-12"
            },
            // More prescriptions...
          ]
        },
        // More patients...
      ]
    }
    ```

- **Error Responses:**
  - Status: 400 Bad Request
    - Content: `{ "error": "Invalid search type. Must be 'name' or 'username'." }`
  - Status: 404 Not Found
    - Content: `{ "error": "No matching patients found." }`
  - Status: 401 Unauthorized
    - Content: `{ "error": "User not authenticated." }`

## 2. View Patient's Emergency Health Records

- **Endpoint:** `/api/emergency_health_records/view/{patientMID}`
- **Method:** `GET`
- **Request Parameters:**
  - `patientMID` (String): MID (username) of the selected patient.

- **Success Response:**
  - Status: 200 OK
  - Content:
    ```json
    {
      "firstName": "Siegwardof",
      "lastName": "Catarina",
      "age": 45,
      "dob": "1978-05-12",
      "gender": "Male",
      "bloodType": "AB+",
      "diagnoses": [
        {
          "code": "D001",
          "description": "Example Diagnosis 1",
          "date": "2023-11-14"
        },
        // More diagnoses...
      ],
      "prescriptions": [
        {
          "id": "P001",
          "medication": "Medicine ABC",
          "date": "2023-11-12"
        },
        // More prescriptions...
      ]
    }
    ```

- **Error Responses:**
  - Status: 404 Not Found
    - Content: `{ "error": "Patient not found." }`
  - Status: 401 Unauthorized
    - Content: `{ "error": "User not authenticated." }`

# Logging

Every successful view of a patient's Emergency Health Records triggers a log entry.

## Log Entry

- **Transaction Code:** 1501 (HCP), 1502 (ER)
- **Verbose Description:**
  - "HCP views a patient's Emergency Health Records" or "ER views a patient's Emergency Health Records"
- **Logged In MID:** MID of the authenticated user
- **Secondary MID:** MID of the patient being viewed
- **Transaction Type:** View
- **Patient Viewable:** Yes

# Data Format

- **First Name:** Up to 20 alpha characters and symbols -, ', and space
- **Last Name:** Up to 30 alpha characters and symbols -, ', and space
- **Patient MID:** Between 6 and 20 alpha characters and symbols - or _
