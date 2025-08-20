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
  - [Running via Docker](#running-via-docker)  
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
