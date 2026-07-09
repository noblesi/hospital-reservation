# Tomcat9 / Java17 Deployment Notes

This project targets Tomcat 9, Java 17, Servlet 4.0, and `javax.servlet.*`.
Tomcat 10 or newer requires a separate `javax` to `jakarta` migration.

## Required Server Settings

Set these values in the Tomcat startup environment. On Windows, put them in
`setenv.bat`. On Linux, put them in `setenv.sh` with `CATALINA_OPTS` or
`JAVA_OPTS`.

```bat
set "JAVA_OPTS=%JAVA_OPTS% -Dhospital.db.url=jdbc:oracle:thin:@DB_HOST:1521:orcl"
set "JAVA_OPTS=%JAVA_OPTS% -Dhospital.db.username=DB_USER"
set "JAVA_OPTS=%JAVA_OPTS% -Dhospital.db.password=DB_PASSWORD"
set "JAVA_OPTS=%JAVA_OPTS% -Dhospital.key.file=C:/secure/qoeryqoeryqoe.txt"
set "JAVA_OPTS=%JAVA_OPTS% -Dhospital.doctor.image.dir=C:/hospital/uploads/doctors"
```

## Configuration Keys

| Key | Required | Description |
|---|---|---|
| `hospital.db.url` | Yes | Oracle JDBC URL used by `jdbc/hospitalDB`. |
| `hospital.db.username` | Yes | Database username. |
| `hospital.db.password` | Yes | Database password. Do not commit real credentials. |
| `hospital.key.file` or `HOSPITAL_KEY_FILE` | Recommended | External key file used to decrypt `server_property.secret_key`. If omitted, the legacy local path `C:/qoeryqoeryqoe.txt` is used. |
| `hospital.doctor.image.dir` or `HOSPITAL_DOCTOR_IMAGE_DIR` | Recommended | External directory for uploaded doctor images. If omitted, the app falls back to the exploded webapp path for local development compatibility. |

## Deployment Checks

- Confirm Tomcat 9 is running on Java 17.
- Confirm `java:comp/env/jdbc/hospitalDB` resolves successfully.
- Confirm the key file exists and the Tomcat process can read it.
- Confirm the doctor image upload directory exists or can be created by Tomcat.
- Upload a doctor image, redeploy the WAR, and confirm the image is still visible.
- Keep only `jstl-1.2.jar` in `WEB-INF/lib`; do not re-add duplicate `jstl.jar` or `standard.jar`.
