# FoodPrint Application

**Carbon Footprint Estimator for Food Items**

A lightweight RESTful API built with Java and Spring Boot, designed to estimate the carbon footprint of food items using external data sources. Supports easy local development and Docker deployment, and integrates interactive API documentation via Swagger.

---

## Table of Contents

- [About the Project](#about-the-project)  
- [Tech Stack](#tech-stack)  
- [Getting Started](#getting-started)  
  - [Prerequisites](#prerequisites)  
  - [Running Locally](#running-locally) 
- [Configuration](#configuration)  
- [API Usage](#api-usage)  
  - [Example Requests & Responses](#example-requests--responses)  
  - [Swagger UI](#swagger-ui)  
- [Assumptions & Limitations](#assumptions--limitations)  
- [Design Decisions](#design-decisions)  
- [Production-Readiness Considerations](#production-readiness-considerations)  
- [Contributing](#contributing)  
- [License](#license)  
- [Contact](#contact)

---

## About the Project

**FoodPrint Application** estimates the environmental impact of food items in terms of carbon footprint using average emissions data. It was built to be simple, reliable, and extensible, offering a straightforward RESTful interface for integration into larger systems or quick testing via Swagger.

---

## Tech Stack

- **Language & Framework**: Java (Spring Boot) – chosen for its robustness and seamless REST setup with embedded server and Swagger support :contentReference[oaicite:1]{index=1}.
- **Build Tool**: Maven
- **API Documentation**: Swagger UI (OpenAPI)
- **Containerization**: Docker

---

## Getting Started

### Prerequisites

- Java 11 or higher (JDK installed)
- Maven
- Docker (for containerized usage)
- A valid **OpenAI API Key** (or relevant external API key) — required for data retrieval logic.

### Running Locally

1. Clone the repository  
   ```bash
   git clone https://github.com/Supriyasingh22/foodprint-application.git
   cd foodprint-application

2. Configure Your API Key

Before running the application, make sure to set your API key using one of the following methods:

- **In `application.properties`**  
  ```properties
  OPENAI_API_KEY=your_actual_api_key_here
you can use : app.openai.apiKey=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


###Configuration 
Before running the application, ensure the required configuration values are set:
```API Keys
app.openai.apiKey=YOUR_API_KEY_HERE

```
```Server Port
server.port=8081
```
###API Usage 
```API Base URL
http://localhost:8081/
```
```Swagger Documentation
http://localhost:8081/swagger-ui.html
```

### Example Request
![Swagger Request](D:\Downloads\response.jpg)

### Example Response
![Swagger Response](D:\Downloads\request.jpg)

















