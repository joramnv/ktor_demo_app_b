# README.md

This project includes project ktor-demo-app-base as a common base.



## Create and publish container image app-b:
1. Run `gradlew clean build`.
2. Run `docker build -t "ktor-demo-app-b:0.0.1-SNAPSHOT-ejcio" .`.
3. Run `docker run -d -p 8223:8223 -p 48223:48223 ktor-demo-app-b:0.0.1-SNAPSHOT-ejcio`.
4. Verify working via browser: `http://localhost:8223` and/or `http://localhost:8223/json/gson`.
5. Tag the remote repository: `docker tag ktor-demo-app-b:0.0.1-SNAPSHOT-ejcio joramnv/ktor-demo-app-b:0.0.1-SNAPSHOT-ejcio`.
6. Push the image to the remote repository: `docker push joramnv/ktor-demo-app-b:0.0.1-SNAPSHOT-ejcio` <-- in this case it is my(!) public repo. (Use your own repo. You can also choose to make a private repo namespace first via the docker hub website.)



## Local development:

### Prerequisites:
- JDK 11
- Dapr CLI (Install if you haven’t already: https://docs.dapr.io/getting-started/install-dapr-cli/ )
- Docker

Running the Dapr side car locally can be done through:
(Prerequisite is that you've run `dapr init` on your machine.)
```
dapr run --app-id app-b --app-port 48223 --app-protocol grpc
```

- Note - In order for the secrets to load correctly, it is important to start the Dapr sidecar first.
- Note - I had some issues where locally Dapr could not find app-b from app-a and visa versa. I was connected to a VPN, when I turned off the VPN Dapr was functioning as normal again.

Running app-b locally can be done through: `gradlew run`
