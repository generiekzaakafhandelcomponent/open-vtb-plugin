# Plugin Documentation

<!-- Use this page to document your plugin. Below is a suggested structure. -->

## Overview

The Berichten API plugin registers berichten (messages) in the VNG service [Open-VTB](https://github.com/maykinmedia/open-vtb) via its Berichten API. It provides a `create-bericht` action that a process can call to post a new bericht and capture the resulting URN.

## Dependencies

### Backend

```kotlin
dependencies {
    implementation("com.ritense.valtimoplugins:berichten-api:1.0.0")
}
```

### Frontend

```json
{
  "dependencies": {
    "@valtimo-plugins/berichten-api": "1.0.0"
  }
}
```

In your `app.module.ts`:

```typescript
import {
    BerichtenApiModule, berichtenApiPluginSpecification,
} from '@valtimo-plugins/berichten-api';

@NgModule({
    imports: [
        BerichtenApiModule,
    ],
    providers: [
        {
            provide: PLUGIN_TOKEN,
            useValue: [
                berichtenApiPluginSpecification,
            ]
        }
    ]
})
```

## Configuration

Create a plugin configuration with the Open-VTB connection details:

| Property  | Type   | Required | Description                                                                          |
|-----------|--------|----------|--------------------------------------------------------------------------------------|
| `baseUrl` | string | Yes      | The Open-VTB Berichten API root, e.g. `https://example.com/berichten/api/v1`.        |
| `token`   | string | Yes      | API token, sent as `Authorization: Token <token>`. Stored as a secret.              |

## Actions

### Create bericht (`create-bericht`)

Registers a new bericht via the Berichten API (`POST /berichten`). Bound to a service task
(`SERVICE_TASK_START`). On success it returns the created bericht's URN and, when `resultingVariable`
is set, stores that URN in the named process variable.

If the API call fails with a 4xx/5xx response or a transport error (e.g. connection refused, timeout)
and `errorVariable` is set, the error message is written to that process variable and the action
completes normally so the process can branch on it. When `errorVariable` is not set, the failure
propagates as an exception.

| Parameter                   | Type                                                 | Required | Description                                                                                   |
|-----------------------------|------------------------------------------------------|----------|-----------------------------------------------------------------------------------------------|
| `onderwerp`                 | string                                               | Yes      | Onderwerp van het bericht.                                                                    |
| `berichtTekst`              | string                                               | Yes      | Tekst van het bericht.                                                                        |
| `ontvanger`                 | string                                               | Yes      | URN van een NATUURLIJK PERSOON of NIET-NATUURLIJK PERSOON.                                     |
| `mijnOverheidBerichtenbox`  | boolean                                              | Yes      | Of het bericht geschikt is voor publicatie in de MijnOverheid Berichtenbox.                   |
| `publicatiedatum`           | date-time                                            | No       | Datum/tijd waarop het bericht zichtbaar moet worden voor de ontvanger.                        |
| `referentie`                | string (max 25)                                      | No       | Eigen optionele referentiegegevens.                                                           |
| `geopendOp`                 | date-time                                            | No       | Tijdstip waarop het bericht door de geadresseerde is geopend.                                 |
| `berichtType`               | string                                               | No       | Code voor het technisch identificeren van een berichtsoort & origine.                         |
| `handelingsPerspectief`     | enum                                                 | No       | Uit te voeren handeling: `betalen`, `incasso`, `informatie_geven`, `informatie_krijgen`, `reactie_ontvangen`, `vernieuwing_nodig`, `uitnodiging_voor_afspraak`. |
| `einddatumHandelingsTermijn`| date-time                                            | No       | Datum/tijd waarop de handeling afgerond moet zijn.                                            |
| `isGerelateerdAan`          | array of `{ urn }`                                   | No       | URN's naar de ZAAK of het PRODUCT. Rijen zonder `urn` worden genegeerd.                        |
| `bijlagen`                  | array of `{ informatieObject, omschrijving, isBerichtTypeBijlage }` | No | Bijlagen bij het bericht. Rijen zonder `informatieObject` worden genegeerd.                    |
| `resultingVariable`         | string                                               | No       | Naam van de procesvariabele waarin de URN van het aangemaakte bericht wordt opgeslagen.       |
| `errorVariable`             | string                                               | No       | Naam van de procesvariabele waarin een 4xx/5xx- of verbindingsfout wordt opgeslagen.          |

## Usage

1. Create a plugin configuration with the Open-VTB `baseUrl` and `token` (see [Configuration](#configuration)).
2. In your BPMN process, add a service task and link it to the `create-bericht` action.
3. Map the action parameters to values or process variables — at minimum `onderwerp`, `berichtTekst`,
   `ontvanger`, and `mijnOverheidBerichtenbox`.
4. Set `resultingVariable` to store the created bericht's URN in a process variable, and optionally
   `errorVariable` to capture a 4xx/5xx or connection error instead of failing the task with an incident.
5. Use those variables downstream — for example, a gateway that branches on `errorVariable` to handle
   failures.
6. Try the action in the example application in two ways: via the reusable building block
   `openvtb-bericht-creatie` (called as a subprocess), or directly through the case process
   `berichten-api-process.bpmn`, which links the `create-bericht` action to a service task.

The bundled example application demonstrates this end to end; see the
[Example Application](open-vtb-plugin.md) guide.
