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
import {BerichtenPluginConfigurationComponent} from "./components/berichten-plugin-configuration/berichten-plugin-configuration.component";
import {BERICHTEN_LOGO_BASE64} from "./assets";
import {CreateBerichtConfigurationComponent} from "./components/create-bericht-configuration/create-bericht-configuration.component";

const openVtbBerichtenPluginSpecification: PluginSpecification = {
  pluginId: "open-vtb-berichten",
  pluginConfigurationComponent: BerichtenPluginConfigurationComponent,
  pluginLogoBase64: BERICHTEN_LOGO_BASE64,
  functionConfigurationComponents: {
    "create-bericht": CreateBerichtConfigurationComponent,
  },
  pluginTranslations: {
    nl: {
      title: "Open VTB Berichten",
      description: "Registreer en raadpleeg berichten via de Open VTB Berichten API.",
      configurationTitle: "Configuratienaam",
      baseUrl: "API URL",
      token: "API token",
      "create-bericht": "Maak bericht aan",
      actionDescription: "Registreert een nieuw bericht via de Open VTB Berichten API en slaat de URN op als procesvariabele.",
      onderwerp: "Onderwerp",
      berichtTekst: "Berichttekst",
      ontvanger: "Ontvanger",
      mijnOverheidBerichtenbox: "MijnOverheid Berichtenbox",
      publicatiedatum: "Publicatiedatum",
      referentie: "Referentie",
      geopendOp: "Geopend op",
      berichtType: "Berichttype",
      handelingsPerspectief: "Handelingsperspectief",
      handelingsPerspectiefTooltip: "Kies een waarde of geef een pv:/doc:-referentie op. Geldige waarden: betalen, incasso, informatie_geven, informatie_krijgen, reactie_ontvangen, vernieuwing_nodig, uitnodiging_voor_afspraak.",
      einddatumHandelingsTermijn: "Einddatum handelingstermijn",
      dateTimeFormatTooltip: "Datum/tijd in ISO-8601-formaat met tijdzone-offset, bijv. 2026-07-13T14:30:00+02:00 of 2026-07-13T12:30:00Z.",
      isGerelateerdAan: "Is gerelateerd aan",
      isGerelateerdAanTooltip: "Geef een pv:/doc:-referentie op naar een lijst met relaties. Elke rij bevat een urn. Een letterlijke waarde wordt niet ondersteund.",
      bijlagen: "Bijlagen",
      bijlagenTooltip: "Geef een pv:/doc:-referentie op naar een lijst met bijlagen. Elke rij bevat informatieObject, omschrijving en isBerichtTypeBijlage. Een letterlijke waarde wordt niet ondersteund.",
      resultingVariable: "Procesvariabele voor URN",
      errorVariable: "Procesvariabele voor foutmelding",
    },
    en: {
      title: "Open VTB Berichten",
      description: "Register and consult messages through the Open VTB Berichten API.",
      configurationTitle: "Configuration Name",
      baseUrl: "API URL",
      token: "API token",
      "create-bericht": "Create message",
      actionDescription: "Registers a new message through the Open VTB Berichten API and stores the URN as a process variable.",
      onderwerp: "Subject",
      berichtTekst: "Message text",
      ontvanger: "Recipient",
      mijnOverheidBerichtenbox: "MijnOverheid Berichtenbox",
      publicatiedatum: "Publication date",
      referentie: "Reference",
      geopendOp: "Opened on",
      berichtType: "Message type",
      handelingsPerspectief: "Action perspective",
      handelingsPerspectiefTooltip: "Select a value or provide a pv:/doc: reference. Valid values: betalen, incasso, informatie_geven, informatie_krijgen, reactie_ontvangen, vernieuwing_nodig, uitnodiging_voor_afspraak.",
      einddatumHandelingsTermijn: "Action deadline",
      dateTimeFormatTooltip: "Date/time in ISO-8601 format with timezone offset, e.g. 2026-07-13T14:30:00+02:00 or 2026-07-13T12:30:00Z.",
      isGerelateerdAan: "Is related to",
      isGerelateerdAanTooltip: "Provide a pv:/doc: reference to a list of relations. Each row contains a urn. A literal value is not supported.",
      bijlagen: "Attachments",
      bijlagenTooltip: "Provide a pv:/doc: reference to a list of attachments. Each row contains informatieObject, omschrijving and isBerichtTypeBijlage. A literal value is not supported.",
      resultingVariable: "Process variable for URN",
      errorVariable: "Process variable for error message",
    },
  },
};

export {openVtbBerichtenPluginSpecification};
