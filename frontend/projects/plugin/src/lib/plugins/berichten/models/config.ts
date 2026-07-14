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

import {PluginConfigurationData} from "@valtimo/plugin";

interface BerichtenPluginConfig extends PluginConfigurationData {
  baseUrl: string;
  token: string;
}

/**
 * Every field is a string because a plugin action configuration holds either a literal
 * value or a process-variable reference (e.g. `pv:isGerelateerdAan`) that Valtimo resolves
 * to the actual typed value at runtime. `mijnOverheidBerichtenbox` is the exception; it is
 * bound to a checkbox in the configuration UI.
 */
interface CreateBerichtConfig {
  onderwerp: string;
  berichtTekst: string;
  ontvanger: string;
  mijnOverheidBerichtenbox: boolean;
  publicatiedatum?: string;
  referentie?: string;
  geopendOp?: string;
  berichtType?: string;
  handelingsPerspectief?: string;
  einddatumHandelingsTermijn?: string;
  isGerelateerdAan?: string;
  bijlagen?: string;
  resultingVariable?: string;
  errorVariable?: string;
}

export {BerichtenPluginConfig, CreateBerichtConfig};
