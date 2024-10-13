# GSM Manager - Telephony Allocation Management System

## Overview
The **GSM Manager** is an enterprise application developed to manage the allocation of telephony resources at **RADEEMA** (Regie Autonome de Distribution d'Eau et d'Électricité de Marrakech). This system centralizes and simplifies the management of SIM cards used for voice and data, offering a robust and intuitive solution to track allocations and monitor usage.

## Features
- **Personnel Management:** Store and manage information related to employees eligible for telephony allocations.
- **SIM Card Allocation:** Assign SIM cards (voice, data, or both) to employees, track the status (assigned or unassigned), and ensure that a SIM can only be allocated to one person at a time.
- **Search and Filter:** Search personnel and SIM cards by various criteria to streamline administration.
- **Statistics and Reporting:** Generate regular statistics on SIM allocation status for informed decision-making.
- **User Roles:** Admins, operators, and observers with role-based access controls.
- **Dashboard:** A user-friendly dashboard with key performance indicators for better management oversight.

## Non-Functional Requirements
- **Secure Access:** Role-based access control to ensure data security and integrity.
- **User-Friendly Interface:** Intuitive design to enhance user experience.
- **Database:** Uses MySQL for secure and scalable data storage.
- **Performance:** Designed for efficient management and timely data access.

## Application Structure

### Login Page:
The login page allows users to securely access their accounts with real-time validation for accurate data entry. Clear error messages guide users in case of failed login attempts.

### Dashboard:
The dashboard presents an overview of key statistics, including the number of SIM cards allocated by department, along with an easy navigation bar.

### Entities Table:
Manage different organizational entities, with features to create, read, update, and delete (CRUD) entries. Includes search functionality for efficient entity management.

### Personnel Table:
Store and manage employee data with full CRUD operations. Search and filter employees based on various attributes.

### SIM Card Table:
Manage SIM cards and track their number, status (assigned or unassigned), and type (voice, data, or both).

### Dotation Table:
Allocate specific balances to SIM cards and track allocation dates. Full CRUD operations and search functionalities are supported.

### User Management:
Admins can manage user accounts within the system, with features to create, update, and delete accounts. This section is only accessible to administrators.

## Technologies Used
- **Java EE:** Backend development using Java Enterprise Edition for robust web services.
- **HTML5 & CSS3:** Frontend technologies for creating dynamic, user-friendly interfaces.
- **MySQL:** Database management system for storing and handling application data.

## Conclusion
This project is aimed at enhancing RADEEMA’s internal processes by providing a reliable, secure, and efficient solution to manage telephony resources. The project allowed for the development of both technical and professional skills, contributing to the overall organizational efficiency.

## Author
**Ikrame Loukridi**

## Acknowledgments
Special thanks to the support division and development teams at RADEEMA for their guidance and collaborative efforts throughout the development of this project.
