# README.md

This project includes project ktor-demo-app-base as a common base.



## Create and publish container image app-b:
1. Run `gradlew clean build`.
2. Run `docker build -t "ktor-demo-app-b:0.0.1-SNAPSHOT-kjwlu" .`.
3. Run `docker run -d -p 8223:8223 -p 48223:48223 ktor-demo-app-b:0.0.1-SNAPSHOT-kjwlu`.
4. Verify working via browser: `http://localhost:8223` and/or `http://localhost:8223/json/gson`.
5. Tag the remote repository: `docker tag ktor-demo-app-b:0.0.1-SNAPSHOT-kjwlu joramnv/ktor-demo-app-b:0.0.1-SNAPSHOT-kjwlu`.
6. Push the image to the remote repository: `docker push joramnv/ktor-demo-app-b:0.0.1-SNAPSHOT-kjwlu` <-- in this case it is public repo! (But you can make a private repo namespace first via the docker hub website.)



## Local development:
Running app-b locally can be done through: `gradlew run`

Running the Dapr side car locally can be done through:
```
dapr run --app-id app-b --app-port 48223 --app-protocol grpc -- waitfor FOREVER
```
(Prerequisite is that you've the Dapr CLI installed on your machine.)

- Note - I had some issues where locally Dapr could not find app-b from app-a and visa versa. I was connected to a VPN, when I turned off the VPN Dapr was functioning as normal again.
