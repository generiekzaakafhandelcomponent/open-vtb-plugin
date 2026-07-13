/*
 * Copyright 2026 Ritense BV, the Netherlands.
 *
 * Licensed under EUPL, Version 1.2 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import {PluginSpecification} from "@valtimo/plugin";
import {BerichtenConfigurationComponent} from "./components/berichten-configuration/berichten-configuration.component";
import {BERICHTEN_LOGO_BASE64} from "./assets";
import {CreateBerichtConfigurationComponent} from "./components/create-bericht-configuration/create-bericht-configuration.component";

const berichtenPluginSpecification: PluginSpecification = {
  pluginId: "berichten",
  pluginConfigurationComponent: BerichtenConfigurationComponent,
  pluginLogoBase64: BERICHTEN_LOGO_BASE64,
  functionConfigurationComponents: {
    "create-bericht": CreateBerichtConfigurationComponent,
  },
  pluginTranslations: {
    nl: {
      title: "Berichten API",
      description: "Registreer en raadpleeg berichten via de Berichten API.",
      configurationTitle: "Configuratienaam",
      baseUrl: "API URL",
      token: "API token",
      "create-bericht": "Maak bericht aan",
      actionDescription: "Registreert een nieuw bericht via de Berichten API en slaat de UUID en URL op als procesvariabele.",
      onderwerp: "Onderwerp",
      berichtTekst: "Berichttekst",
      ontvanger: "Ontvanger",
      mijnOverheidBerichtenbox: "MijnOverheid Berichtenbox",
      publicatiedatum: "Publicatiedatum",
      referentie: "Referentie",
      geopendOp: "Geopend op",
      berichtType: "Berichttype",
      handelingsPerspectief: "Handelingsperspectief",
      einddatumHandelingsTermijn: "Einddatum handelingstermijn",
      dateTimeFormat: "Datum/tijd in ISO-8601-formaat met tijdzone-offset, bijv. 2026-07-13T14:30:00+02:00 of 2026-07-13T12:30:00Z.",
      isGerelateerdAan: "Is gerelateerd aan",
      bijlagen: "Bijlagen",
      resultingVariable: "Procesvariabele voor URN",
      errorVariable: "Procesvariabele voor foutmelding",
    },
    en: {
      title: "Berichten API",
      description: "Register and consult messages through the Berichten API.",
      configurationTitle: "Configuration Name",
      baseUrl: "API URL",
      token: "API token",
      "create-bericht": "Create message",
      actionDescription: "Registers a new message through the Berichten API and stores the UUID and URL as process variables.",
      onderwerp: "Subject",
      berichtTekst: "Message text",
      ontvanger: "Recipient",
      mijnOverheidBerichtenbox: "MijnOverheid Berichtenbox",
      publicatiedatum: "Publication date",
      referentie: "Reference",
      geopendOp: "Opened on",
      berichtType: "Message type",
      handelingsPerspectief: "Action perspective",
      einddatumHandelingsTermijn: "Action deadline",
      dateTimeFormat: "Date/time in ISO-8601 format with timezone offset, e.g. 2026-07-13T14:30:00+02:00 or 2026-07-13T12:30:00Z.",
      isGerelateerdAan: "Is related to",
      bijlagen: "Attachments",
      resultingVariable: "Process variable for URN",
      errorVariable: "Process variable for error message",
    },
  },
};

export {berichtenPluginSpecification};