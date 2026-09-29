# Software Requirements Specification (SRS) - CEGCT

## 1. Introduction
The CEGCT app enables citizens to report civil infrastructure issues (potholes, water leaks, uncollected trash, faulty streetlights, etc.), track resolution progress, communicate with municipal officials, and inspect case history.

## 2. Functional Requirements
- **FR-01: User Management:** Citizens can register via email/password, verify email, log in, reset password, or continue anonymously.
- **FR-02: Issue Reporting:** Citizens can submit reports with category, description, photo, and geolocation.
- **FR-03: Case Tracking:** Users can view submitted reports, check real-time status updates (Submitted, In Progress, Resolved), rate resolution, and reopen cases if needed.
- **FR-04: Chat & Notifications:** Real-time messaging with assigned officers and push notifications for status updates.
- **FR-05: Localization:** Multi-language support in English and Chichewa.

## 3. Non-Functional Requirements
- **NFR-01: Performance:** Fast UI rendering using Jetpack Compose with responsive layouts.
- **NFR-02: Security:** Secure token storage, network security configuration, and intent security.
- **NFR-03: Offline Capabilities:** Local caching and offline sync banner support.
