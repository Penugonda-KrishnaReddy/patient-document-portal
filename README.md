**Patient Document Upload Portal**

A Full-Stack Application for Managing Medical PDF Documents

**Overview**

This project is a simple patient document management system that allows users to upload, view, download and delete PDF files. It is built using Spring Boot for the backend and MySQL for data storage, with a basic HTML-based frontend interface for interaction.
The application runs locally and is suitable as a demonstration of full-stack development capability.

**Features**
Functionality	Description
Upload Document	Upload medical documents in PDF format
List Documents	View all previously uploaded documents
Download Document	Download a specific file using its document ID
Delete Document	Remove a document from the system and database
Technology Stack
Layer	Technology
Backend	Spring Boot, Java
Database	MySQL
Frontend	HTML, CSS, JavaScript
API Format	REST
Local Setup and Execution
**1. Prerequisites**
Requirement	Purpose
Java 17+	Required to run Spring Boot
Maven	Builds and runs the backend
MySQL Server	Stores file metadata
Postman (Optional)	For API testing
**2. Database Configuration**

Run the following SQL commands in MySQL:

CREATE DATABASE patient_portal;
CREATE USER 'portal_user'@'localhost' IDENTIFIED BY 'StrongPassword123!';
GRANT ALL PRIVILEGES ON patient_portal.* TO 'portal_user'@'localhost';
FLUSH PRIVILEGES;

**3. Backend Configuration**

Update backend/src/main/resources/application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/patient_portal?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=portal_user
spring.datasource.password=StrongPassword123!

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

file.upload-dir=uploads

**4. Running the Backend**
cd backend
mvn spring-boot:run


The backend will be available at:

**http://localhost:8081/**


This page provides a user interface for document upload, retrieval, and deletion.

**API Endpoints**
Method	Endpoint	Description
POST	/documents/upload	Upload a PDF document (form-data → key=file)
GET	/documents	Retrieve all uploaded documents
GET	/documents/{id}	Download a document by ID
DELETE	/documents/{id}	Delete a document by ID
Example API Calls (Postman)

Upload File

POST http://localhost:8080/documents/upload
Body → form-data → file = <select PDF file>


Retrieve All Files

GET http://localhost:8080/documents


Download File

GET http://localhost:8080/documents/{id}


Delete File

DELETE http://localhost:8080/documents/{id}

**Reviewer Notes**

Application runs fully on local environment

Handles PDF storage and metadata persistence

APIs are REST-based and testable via Postman

Simple UI provided for user interactions
