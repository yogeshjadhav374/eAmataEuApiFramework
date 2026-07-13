# Project Structure & Conventions

## Framework Location
`C:\Users\LNV-24\Desktop\eAmata-portal\tests\eAmataEuApiFramework`

## Repository Paths
- Backend: `C:\Users\LNV-24\Desktop\eAmata-portal\Eamata-backend\master-service`
- Admin Portal: `C:\Users\LNV-24\Desktop\eAmata-portal\eamata-admin-portal`
- Care Portal: `C:\Users\LNV-24\Desktop\eAmata-portal\eamata-care-portal`

## Base URL & Auth
- Base URI: `https://em-be.qa.api.eu.eamata.com`
- Base Path: `/api/master/`
- Config file: `src/test/resources/config.properties`
- Super Admin: `yogesh.jadhav+superadmin@thinkitive.com` / `Test@123`
- HCP Admin: `yogesh.jadhav+hcpAdmin4@thinkitive.com` / `Test@123`
- TenantId: `qa_ehs` (required for HCP endpoints as `x-tenant-id` header)

## Package Structure
- Payload generators: `src/main/java/entities/payloads/`
- Step definitions (Super Admin): `src/test/java/com/thinkitive/eAmata/stepDefinitions/SuperAdmin_Portal/`
- Step definitions (Care Portal): `src/test/java/com/thinkitive/eAmata/stepDefinitions/Home_Care_Portal/`
- Feature files (Super Admin): `src/test/resources/features/Super_Admin_Portal/`
- Feature files (Care Portal): `src/test/resources/features/Home_Care_Portal/`
- Runners: `src/test/java/com/thinkitive/eAmata/runners/`
- Utilities: `src/main/java/com/thinkitive/eAmata/` (ApiRequestBuilder, propertyHandler)
- Auth utility: `src/main/java/utils/AuthManager.java`

## Key Classes
- `ApiRequestBuilder` — base class with `superAdminToken`, `hcpAdminToken`, `request`, `response` static fields
- `AuthManager` — login helpers for superAdmin and HCP admin (uses x-tenant-id for HCP)
- `Hooks` — @Before that initializes both tokens once per run
- `propertyHandler` — reads config.properties

## Conventions
- Super Admin API calls: use `superAdminToken` (no TenantId header)
- HCP API calls: use `hcpAdminToken` + `x-tenant-id: qa_ehs` header
- UUID capture pattern: capture from list response `data.content[0].uuid`, store as static field for subsequent steps
- Feature tags: `@SuperAdminPortal` or `@HomeCarePortal` + `@FeatureName`
- Runner for Super Admin: `SuperAdminPortalTestRunner` — tags `@SuperAdminPortal and not @KnownIssue`
- Runner for Home Care: `HomeCarePortalTestRunner` — tags `@HomeCarePortal and not @KnownIssue`
