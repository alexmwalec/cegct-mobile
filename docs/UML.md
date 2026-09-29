# UML Class & Sequence Diagram Notes - CEGCT

## Architecture Overview
The app follows Clean Architecture and MVVM pattern across features:
- **Presentation Layer:** Jetpack Compose screens (`*Screen.kt`) observing ViewModels (`*ViewModel.kt`).
- **Domain Layer:** Repositories (Interfaces) and UseCases (e.g., `LoginUseCase`, `SubmitReportUseCase`).
- **Data Layer:** Repository implementations, Local Data Sources (Room DB), Remote Data Sources (API clients/DTOs).

## Sequence Diagram: Issue Reporting Flow
1. User interacts with `ReportScreen` -> `ReportViewModel`.
2. `ReportViewModel` calls `SubmitReportUseCase`.
3. `SubmitReportUseCase` invokes `ReportRepository`.
4. `ReportRepository` uploads photo and payload to backend API (or queues offline via WorkManager).
5. Success response updates UI state to success screen.
