# Plugin Documentation

<!-- Use this page to document your plugin. Below is a suggested structure. -->

## Overview

This is a sample plugin demonstrating an API call action. It fetches data from a time API endpoint.

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

List the plugin configuration properties and how to set them.

| Property | Type   | Required | Description                          |
|----------|--------|----------|--------------------------------------|
| apiUrl   | string | Yes      | The URL of the time API to call      |

## Actions

### Time API test action

Sends a GET request to the configured API URL and returns the timezone response.

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
|           |      |          |             |

## Usage

Explain how to use the plugin in a process, with examples if applicable.
