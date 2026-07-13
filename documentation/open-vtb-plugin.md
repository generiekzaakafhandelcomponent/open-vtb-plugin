# Example Application

This repository contains a working example application that showcases the Open-VTB plugin. It runs
the plugin inside a full GZAC/Valtimo stack and registers berichten (messages) in
[Open-VTB](https://github.com/maykinmedia/open-vtb).

## Prerequisites

- Java 21
- Node 20 (for the frontend)
- [Docker (Desktop)](https://www.docker.com/products/docker-desktop/)

## Running the example application

Make sure Docker is running. Unless noted otherwise, the commands below are run from the **project root**.

### 1. Start Open-VTB

The plugin posts berichten to an Open-VTB instance. A local stack is bundled under `open-vtb/`:

Download and install https://github.com/maykinmedia/open-vtb

1. start the Open VTB docker stack
```shell
docker-compose up -d --no-build 
```
2. load data fixtures
```shell
docker-compose exec web src/manage.py loaddata verzoeken taken berichten 
```
3. create a superuser to be able to login
```shell
docker-compose exec web src/manage.py createsuperuser
```

Open-VTB is then available on `http://localhost:8000`; its Berichten Plugin root is
`http://localhost:8000/berichten/api/v1`. The plugin configuration
(`backend/app/src/main/resources/config/plugin/berichten.pluginconfig.json`) points at this URL — set
its `token` to a valid Open-VTB API token.

### 2. Start the Valtimo backend

1. start the ZGW/Valtimo docker stack
```shell
./gradlew :backend:app:composeUp
```
2. start the backend
```shell
./gradlew :backend:app:bootRun
```

### 3. Start the frontend

Run from the `frontend/` directory:

```
nvm use 21
npm run clean
npm install
npm run build
npm start
```

### Keycloak users

The example application has a few preconfigured test users.

| Name         | Role           | Username  | Password  |
|--------------|----------------|-----------|-----------|
| James Vance  | ROLE_USER      | user      | user      |
| Asha Miller  | ROLE_ADMIN     | admin     | admin     |
| Morgan Finch | ROLE_DEVELOPER | developer | developer |

## Trying the plugin

Sign in and start the **Berichten** case. Its process fills in the bericht fields and runs the
`create-bericht` action, which registers the bericht in Open-VTB and stores the resulting URN on the case.
If the Open-VTB call fails (a 4xx/5xx response or a connection error), the error message is stored in the
`berichtError` field instead. See the [Plugin Documentation](plugin.md) for the action's parameters.

## Plugin development

The plugin source code is located in:
- Backend: `backend/plugin/src/`
- Frontend: `frontend/projects/plugin/src/`

For more information on how to build a plugin, see
the [Custom Plugin Definition](https://docs.valtimo.nl/features/plugins/plugins/custom-plugin-definition) documentation.
