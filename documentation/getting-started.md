# Getting Started

This guide covers setting up the Open-VTB plugin for development. To simply run the bundled
example application, see the [Example Application](open-vtb-plugin.md) guide instead.

## Prerequisites

- Java 21
- Node 20 (for the frontend)
- [Docker (Desktop)](https://www.docker.com/products/docker-desktop/)
- An [Open-VTB](https://github.com/maykinmedia/open-vtb) instance to send berichten to (a local stack
  is available under `open-vtb/`)

## Project layout

The plugin is delivered as two artifacts that must stay in sync:

- **Backend** — a Kotlin/Spring library (`com.ritense.valtimoplugins:open-vtb-plugin`) that contributes
  the plugin actions, under [`backend/plugin`](../backend/plugin)
- **Frontend** — an Angular library (`@valtimo-plugins/open-vtb`) that provides the configuration
  UI, under [`frontend/projects/plugin`](../frontend/projects/plugin)

The repository also ships a runnable example application ([`backend/app`](../backend/app) and
[`frontend/src`](../frontend/src)) that mounts the plugin against a full ZGW/Valtimo stack.

## Build and test

```shell
# Backend build and tests (integration tests start Postgres via Docker; Docker must be running)
./gradlew build
./gradlew :backend:plugin:test

# Kotlin lint
./gradlew ktlintCheck

# Frontend (run in frontend/)
npm install
npm run build
npm run lint
```

## Configuration

Configure a plugin instance with the Open-VTB connection details:

| Property  | Type   | Required | Description                                                                  |
|-----------|--------|----------|------------------------------------------------------------------------------|
| `baseUrl` | string | Yes      | The Open VTB Berichten API root, e.g. `https://example.com/berichten/api/v1` |
| `token`   | string | Yes      | API token, sent as `Authorization: Token <token>`                            |

For details on the available actions, see the [Plugin Documentation](plugin.md). For background on how
Valtimo plugins are defined, see the
[Custom Plugin Definition](https://docs.valtimo.nl/features/plugins/plugins/custom-plugin-definition)
documentation.
