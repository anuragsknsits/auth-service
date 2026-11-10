# auth-service knowledge graph

## Runtime relationships

Client → UserController → UserServiceImpl → UserRepository → MySQL. Login issues an authToken cookie through JWTUtil. JwtFilter → UserDetailsServiceImpl → UserDTO → Role → servlet SecurityContext. RoleController → RoleServiceImpl → RoleRepository. UserManagementController → UserServiceImpl → UserProfileRepository. Auth registers with Eureka.

## Static validation findings

- Method-security activation was not found for the @PreAuthorize checks on role and profile controllers.
- The authority loader grants the role name on UserDTO. The role-permission and employee-role assignment models are outside that authority-loading path.
- Dockerfile, Spring and Compose consistently use port 8081.

## File nodes and reference edges

Java edges come from local imports and same-package type references. Build artifacts are excluded.

| File | Local references or file role |
| --- | --- |
| [.gitignore](.gitignore) | Git ignore rules |
| [compose.yaml](compose.yaml) | Container deployment Spring runtime configuration |
| [Dockerfile](Dockerfile) | Packages the Maven JAR |
| [pom.xml](pom.xml) | Maven dependencies and build |
| [src/main/java/com/codewithanurag/authentication/AuthServiceApplication.java](src/main/java/com/codewithanurag/authentication/AuthServiceApplication.java) | Java entry point or standalone type |
| [src/main/java/com/codewithanurag/authentication/config/JwtFilter.java](src/main/java/com/codewithanurag/authentication/config/JwtFilter.java) | JWTUtil |
| [src/main/java/com/codewithanurag/authentication/config/WebSecurityConfig.java](src/main/java/com/codewithanurag/authentication/config/WebSecurityConfig.java) | JwtFilter |
| [src/main/java/com/codewithanurag/authentication/controller/RoleController.java](src/main/java/com/codewithanurag/authentication/controller/RoleController.java) | Role, RoleService |
| [src/main/java/com/codewithanurag/authentication/controller/UserController.java](src/main/java/com/codewithanurag/authentication/controller/UserController.java) | AuthenticationRequest, AuthResponse, ChangePassword, ForgotEmail, SignUp, UserService, CommonUtils |
| [src/main/java/com/codewithanurag/authentication/controller/UserManagementController.java](src/main/java/com/codewithanurag/authentication/controller/UserManagementController.java) | UserProfile, Profile, SignUp, UserService |
| [src/main/java/com/codewithanurag/authentication/entity/EmployeeRole.java](src/main/java/com/codewithanurag/authentication/entity/EmployeeRole.java) | EmployeeRoleAssignment |
| [src/main/java/com/codewithanurag/authentication/entity/EmployeeRoleAssignment.java](src/main/java/com/codewithanurag/authentication/entity/EmployeeRoleAssignment.java) | EmployeeRoleAssignmentId |
| [src/main/java/com/codewithanurag/authentication/entity/EmployeeRoleAssignmentId.java](src/main/java/com/codewithanurag/authentication/entity/EmployeeRoleAssignmentId.java) | EmployeeRole, UserDTO |
| [src/main/java/com/codewithanurag/authentication/entity/Permission.java](src/main/java/com/codewithanurag/authentication/entity/Permission.java) | RolePermission |
| [src/main/java/com/codewithanurag/authentication/entity/Role.java](src/main/java/com/codewithanurag/authentication/entity/Role.java) | RolePermission, UserDTO |
| [src/main/java/com/codewithanurag/authentication/entity/RolePermission.java](src/main/java/com/codewithanurag/authentication/entity/RolePermission.java) | Permission, Role, RolePermissionId |
| [src/main/java/com/codewithanurag/authentication/entity/RolePermissionId.java](src/main/java/com/codewithanurag/authentication/entity/RolePermissionId.java) | Permission, Role |
| [src/main/java/com/codewithanurag/authentication/entity/UserDTO.java](src/main/java/com/codewithanurag/authentication/entity/UserDTO.java) | Role, UserProfile |
| [src/main/java/com/codewithanurag/authentication/entity/UserProfile.java](src/main/java/com/codewithanurag/authentication/entity/UserProfile.java) | UserDTO |
| [src/main/java/com/codewithanurag/authentication/exception/ApiErrorResponse.java](src/main/java/com/codewithanurag/authentication/exception/ApiErrorResponse.java) | Java entry point or standalone type |
| [src/main/java/com/codewithanurag/authentication/exception/ExceptionControllerAdvice.java](src/main/java/com/codewithanurag/authentication/exception/ExceptionControllerAdvice.java) | ApiErrorResponse, UserExistException |
| [src/main/java/com/codewithanurag/authentication/exception/UserExistException.java](src/main/java/com/codewithanurag/authentication/exception/UserExistException.java) | Java entry point or standalone type |
| [src/main/java/com/codewithanurag/authentication/model/AuthenticationRequest.java](src/main/java/com/codewithanurag/authentication/model/AuthenticationRequest.java) | Java entry point or standalone type |
| [src/main/java/com/codewithanurag/authentication/model/AuthResponse.java](src/main/java/com/codewithanurag/authentication/model/AuthResponse.java) | Java entry point or standalone type |
| [src/main/java/com/codewithanurag/authentication/model/ChangePassword.java](src/main/java/com/codewithanurag/authentication/model/ChangePassword.java) | Java entry point or standalone type |
| [src/main/java/com/codewithanurag/authentication/model/ForgotEmail.java](src/main/java/com/codewithanurag/authentication/model/ForgotEmail.java) | Java entry point or standalone type |
| [src/main/java/com/codewithanurag/authentication/model/Profile.java](src/main/java/com/codewithanurag/authentication/model/Profile.java) | Java entry point or standalone type |
| [src/main/java/com/codewithanurag/authentication/model/SignUp.java](src/main/java/com/codewithanurag/authentication/model/SignUp.java) | Java entry point or standalone type |
| [src/main/java/com/codewithanurag/authentication/repository/EmployeeRoleAssignmentRepository.java](src/main/java/com/codewithanurag/authentication/repository/EmployeeRoleAssignmentRepository.java) | EmployeeRoleAssignment, EmployeeRoleAssignmentId |
| [src/main/java/com/codewithanurag/authentication/repository/EmployeeRoleRepository.java](src/main/java/com/codewithanurag/authentication/repository/EmployeeRoleRepository.java) | EmployeeRole |
| [src/main/java/com/codewithanurag/authentication/repository/PermissionRepository.java](src/main/java/com/codewithanurag/authentication/repository/PermissionRepository.java) | Permission |
| [src/main/java/com/codewithanurag/authentication/repository/RolePermissionRepository.java](src/main/java/com/codewithanurag/authentication/repository/RolePermissionRepository.java) | RolePermission, RolePermissionId |
| [src/main/java/com/codewithanurag/authentication/repository/RoleRepository.java](src/main/java/com/codewithanurag/authentication/repository/RoleRepository.java) | Role |
| [src/main/java/com/codewithanurag/authentication/repository/UserProfileRepository.java](src/main/java/com/codewithanurag/authentication/repository/UserProfileRepository.java) | UserDTO, UserProfile |
| [src/main/java/com/codewithanurag/authentication/repository/UserRepository.java](src/main/java/com/codewithanurag/authentication/repository/UserRepository.java) | UserDTO |
| [src/main/java/com/codewithanurag/authentication/service/impl/RoleServiceImpl.java](src/main/java/com/codewithanurag/authentication/service/impl/RoleServiceImpl.java) | Role, RoleRepository, RoleService |
| [src/main/java/com/codewithanurag/authentication/service/impl/UserDetailsServiceImpl.java](src/main/java/com/codewithanurag/authentication/service/impl/UserDetailsServiceImpl.java) | UserDTO, UserRepository |
| [src/main/java/com/codewithanurag/authentication/service/impl/UserServiceImpl.java](src/main/java/com/codewithanurag/authentication/service/impl/UserServiceImpl.java) | Role, UserDTO, UserProfile, AuthenticationRequest, AuthResponse, ChangePassword, Profile, SignUp, RoleRepository, UserProfileRepository, UserRepository, UserService, JWTUtil |
| [src/main/java/com/codewithanurag/authentication/service/RoleService.java](src/main/java/com/codewithanurag/authentication/service/RoleService.java) | Role |
| [src/main/java/com/codewithanurag/authentication/service/UserService.java](src/main/java/com/codewithanurag/authentication/service/UserService.java) | UserProfile, AuthenticationRequest, AuthResponse, ChangePassword, Profile, SignUp |
| [src/main/java/com/codewithanurag/authentication/util/CommonUtils.java](src/main/java/com/codewithanurag/authentication/util/CommonUtils.java) | Java entry point or standalone type |
| [src/main/java/com/codewithanurag/authentication/util/JWTUtil.java](src/main/java/com/codewithanurag/authentication/util/JWTUtil.java) | Java entry point or standalone type |
| [src/main/resources/application.yaml](src/main/resources/application.yaml) | Spring runtime configuration |
| [src/test/java/com/codewithanurag/authentication/AuthServiceApplicationTests.java](src/test/java/com/codewithanurag/authentication/AuthServiceApplicationTests.java) | Spring context smoke test |
## Container build and deployment

- [Dockerfile](Dockerfile): Maven/Temurin 21 Alpine dependency cache → source build → extracted Spring Boot dependency/loader/snapshot/application layers. A separate `jlink` stage produces a stripped Java 21 runtime; final Alpine 3.23 image contains only runtime libraries and application layers, running as UID 10001. No host JDK or prebuilt `target/` is needed.
- [.dockerignore](.dockerignore): allowlists only the POM, source and Docker build files, excluding host outputs, Git and local configuration.
- [compose.yaml](compose.yaml): selects the `production` build target and shares external network `finworkspace-backend` with sibling backends. Create the network once before Compose startup. Eureka registration uses the container IP (`EUREKA_INSTANCE_PREFER_IP_ADDRESS=true`) so peer services can reach discovered instances.
- Java modules include Java SE, EC TLS, unsupported APIs, management, DNS naming, character sets, locales and ZIP filesystem. Revalidate modules if dependencies or runtime integrations change. Packaging skips tests requiring external services; run those separately with their dependencies available.
- See [workspace Docker guide](../DOCKER.md) for build/start commands, size verification and service prerequisites.
