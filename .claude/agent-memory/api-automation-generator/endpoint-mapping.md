# Backend API Endpoint Mapping

## Controller Package
`com.eamata.master.controller`

## All Features — Endpoint Summary

| Feature | Controller | Base Path |
|---------|-----------|-----------|
| Authentication / Users | UserController | `/api/master` |
| Provider/Nurse | ProviderController | `/api/master/provider` |
| Provider Group | ProviderGroupController | `/api/master/provider-group` |
| Device | DeviceController | `/api/master/device` |
| Patient | PatientController | `/api/master/patient` |
| Task | TaskController | `/api/master/task` |
| Roles | RolesAndPrivilegesController | `/api/master/role` |
| Consent Forms | ConsentFormController | `/api/master` (prefix `consent-form`) |
| Care Plan | CarePlanController | `/api/master/care-plan` |
| Medical Codes | MedicalCodeController | `/api/master/medical-codes` |
| Scheduling | AppointmentController | `/api/master/appointment` |
| Audit Logs | ActivityController | `/api/master/activity` |

## Key DTOs
- Provider: `firstName`, `lastName`, `email`, `phone`, `gender`, `npi` (@NotBlank), `role` (Roles enum), `address`
- Device: `name` (@NotBlank, @Pattern alphanumeric+spaces), `deviceType`, `description`, `category` (MECHANICAL/ELECTRICAL/DIGITAL)
- Patient: `firstName`, `lastName`, `email`, `gender`, `birthDate`, `mrn`, `address`
- Task: `title` (@NotBlank), `dueDate` (@NotNull), `status` (TaskStatus), `priority` (TaskPriority)
- ConsentForm: `name` (@NotBlank), `document` (@NotBlank base64 PDF)
- Roles enum: SUPER_ADMIN, ADMIN, FRONTDESK, BILLER, SITE_ADMIN, PROVIDER_GROUP_ADMIN, PROVIDER, NURSE, PATIENT
- TaskStatus: OPEN, IN_PROGRESS, CLOSED
- TaskPriority: LOW, MEDIUM, HIGH
- DeviceCategory: MECHANICAL, ELECTRICAL, DIGITAL
- Gender: MALE, FEMALE, OTHER

## Authentication
- Login: POST `/api/master/login` with `{username, password}` — returns `data.access_token`, `data.refresh_token`
- Refresh: POST `/api/master/access-token?refreshToken={token}`
- Logout: POST `/api/master/logout`
- HCP endpoints require `x-tenant-id` header
