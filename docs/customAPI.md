# API Documentation for UC15 Emergency Health Records

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
