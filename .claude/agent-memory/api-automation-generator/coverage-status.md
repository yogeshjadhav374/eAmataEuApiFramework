# Automation Coverage Status

Last updated: 2026-03-15

## Super Admin Portal

| Feature | Feature File | Step Definitions | Payload Generator | Status |
|---------|-------------|-----------------|-------------------|--------|
| Authentication | Authentication.feature | AuthenticationStep.java | AuthPayloadGenerator.java | ✅ Complete |
| Provider Management | Provider_Management.feature | ProviderManagementStep.java | ProviderPayloadGenerator.java | ✅ Complete |
| HCP Group | HCP_Group.feature | HCPGroupStep.java | — | ✅ Complete |
| Care Plan Management | Care_Plan_Management.feature | CarePlanManagementStep.java | CarePlanPayloadGenerator.java | ✅ Complete |
| Care Plan | CarePlan.feature | CarePlanStep.java | CarePlanPayload.java | ✅ Complete |
| Staff Management | Staff_Management.feature | StaffManagementStep.java | StaffPayloadGenerator.java | ✅ Complete |
| Admin Staff | Admin_Staff.feature | SuperAdminStaffStep.java | — | ✅ Complete |
| Profile Management | Profile_Management.feature | ProfileManagementStep.java | ProfilePayloadGenerator.java | ✅ Complete |
| Device Management | Device_Management.feature | DeviceManagementStep.java | DevicePayloadGenerator.java | ✅ Complete (added 2026-03-15) |
| Roles Management | Roles_Management.feature | RolesManagementStep.java | — | ✅ Complete (added 2026-03-15) |
| Consent Forms | Consent_Forms.feature | ConsentFormsStep.java | ConsentFormPayloadGenerator.java | ✅ Complete (added 2026-03-15) |
| Audit Logs | Audit_Logs.feature | AuditLogsStep.java | — | ✅ Complete (added 2026-03-15) |

## Home Care Portal

| Feature | Feature File | Step Definitions | Payload Generator | Status |
|---------|-------------|-----------------|-------------------|--------|
| Authentication | Authentication.feature | HCPAuthenticationStep.java | — | ✅ Complete (added 2026-03-15) |
| Patient Management | Patient_Management.feature | PatientManagementStep.java | PatientPayloadGenerator.java | ✅ Complete (added 2026-03-15) |
| Care Plan | Care_Plan.feature | HCPCarePlanStep.java | CarePlanPayloadGenerator.java (reused) | ✅ Complete (added 2026-03-15) |
| Device Management | Device_Management.feature | HCPDeviceManagementStep.java | — | ✅ Complete (added 2026-03-15) |
| Scheduling | Scheduling.feature | SchedulingStep.java | AppointmentPayloadGenerator.java | ✅ Complete (added 2026-03-15) |
| Task Management | Task_Management.feature | TaskManagementStep.java | TaskPayloadGenerator.java | ✅ Complete (added 2026-03-15) |
| Users Management | Users_Management.feature | UsersManagementStep.java | ProviderPayloadGenerator+StaffPayloadGenerator (reused) | ✅ Complete (added 2026-03-15) |
| Roles Management | Roles_Management.feature | HCPRolesManagementStep.java | — | ✅ Complete (added 2026-03-15) |
| Consent Forms | Consent_Forms.feature | HCPConsentFormsStep.java | ConsentFormPayloadGenerator.java (reused) | ✅ Complete (added 2026-03-15) |
| Medical Codes | Medical_Codes.feature | MedicalCodesStep.java | MedicalCodePayloadGenerator.java | ✅ Complete (added 2026-03-15) |
| Audit Logs | Audit_Logs.feature | HCPAuditLogsStep.java | — | ✅ Complete (added 2026-03-15) |
