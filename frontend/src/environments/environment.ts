/*
 * Copyright 2015-2025 Ritense BV, the Netherlands.
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

import {CaseListTab, IncludeFunction, Language, ROLE_ADMIN, ROLE_USER, TaskListTab, UploadProvider, ValtimoConfig,} from '@valtimo/shared';
import {NgxLoggerLevel} from 'ngx-logger';
import {authenticationKeycloak} from './auth/keycloak-config';
import {defaultDefinitionColumns} from './columns';
import {LOGO_SVG_BASE_64} from './logo';

export const environment: ValtimoConfig = {
  logoSvgBase64: LOGO_SVG_BASE_64,
  darkModeLogoSvgBase64: LOGO_SVG_BASE_64,
  applicationTitle: '',
  production: true,
  authentication: authenticationKeycloak,
  menu: {
    menuItems: [
      {
        roles: [ROLE_USER],
        link: ['/'],
        title: 'Dashboard',
        iconClass: 'icon mdi mdi-view-dashboard',
      },
      {
        roles: [ROLE_USER],
        title: 'Cases',
        iconClass: 'icon mdi mdi-layers',
        children: [],
      },
      {
        roles: [ROLE_USER],
        link: ['/tasks'],
        title: 'Tasks',
        iconClass: 'icon mdi mdi-check-all',
      },
      {
        roles: [ROLE_ADMIN],
        title: 'Objects',
        iconClass: 'icon mdi mdi-archive',
        includeFunction: IncludeFunction.ObjectManagementEnabled,
      },
      {
        roles: [ROLE_USER],
        link: ['/analysis'],
        title: 'Analysis',
        iconClass: 'icon mdi mdi-chart-bar',
      },
      {
        roles: [ROLE_ADMIN],
        link: ['/teams'],
        title: 'teams.title',
        iconClass: 'icon mdi mdi-account-group',
      },
      {
        roles: [ROLE_ADMIN],
        title: 'Admin',
        iconClass: 'icon mdi mdi-tune',
        children: [
          {title: 'Configuration',textClass: 'text-dark font-weight-bold c-default'},
          {link: ['/admin-settings'], title: 'adminSettings.title'},
          {link: ['/building-block-management'], title: 'buildingBlockManagement.title'},
          {link: ['/case-management'], title: 'Cases'},
          {link: ['/plugins'], title: 'Plugins'},
          {link: ['/dashboard-management'], title: 'Dashboard'},
          {link: ['/access-control'], title: 'Access Control'},
          {link: ['/translation-management'], title: 'Translations'},
          {link: ['/choice-fields'], title: 'Choice fields'},
          {title: 'Object management', textClass: 'text-dark font-weight-bold c-default'},
          {link: ['/object-management'], title: 'Objects'},
          {link: ['/form-management'], title: 'Forms'},
          {link: ['/notifications-api/notifications/failed'], title: 'Notifications'},
          {title: 'System processes', textClass: 'text-dark font-weight-bold c-default'},
          {link: ['/processes'], title: 'Processes'},
          {link: ['/decision-tables'], title: 'Decision tables'},
          {title: 'Other',textClass: 'text-dark font-weight-bold c-default'},
          {link: ['/logging'], title: 'Logs'},
          {link: ['/case-migration'], title: 'Case migration (beta)'},
          {link: ['/process-migration'], title: 'Process migration'},
        ],
      },
    ],
  },
  whitelistedDomains: ['localhost:4200'],
  langKey: Language.NL,
  mockApi: {
    endpointUri: window['env']['mockApiUri'] || '/mock-api/'
  },
  valtimoApi: {
    endpointUri: window['env']['apiUri'] || '/api/'
  },
  changePasswordUrl: {
    endpointUri: '/placeholder',
  },
  swagger: {
    endpointUri: window['env']['swaggerUri'] || '/v3/api-docs'
  },
  logger: {
    level: NgxLoggerLevel.TRACE,
  },
  definitions: {
    cases: [],
  },
  openZaak: {
    catalogus: window['env']['openZaakCatalogusId'] || ''
  },
  uploadProvider: UploadProvider.DOCUMENTEN_API,
  caseFileSizeUploadLimitMB: 100,
  supportedDocumentFileTypesToViewInBrowser: ['pdf', 'jpg', 'png', 'svg'],
  defaultDefinitionTable: defaultDefinitionColumns,
  caseFileUploadAcceptedFiles:
    'image/png, image/jpeg, text/plain, application/pdf, application/vnd.openxmlformats-officedocument.wordprocessingml.document, application/xml',
  visibleTaskListTabs: [TaskListTab.MINE, TaskListTab.OPEN, TaskListTab.ALL],
  visibleCaseListTabs: [CaseListTab.ALL, CaseListTab.MINE, CaseListTab.TEAM, CaseListTab.OPEN],
  customTaskList: {
    fields: [
      {
        propertyName: 'due',
        translationKey: 'due',
        sortable: true,
      },
      {
        propertyName: 'created',
        translationKey: 'created',
        sortable: true,
      },
      {
        propertyName: 'name',
        translationKey: 'name',
        sortable: true,
      },
      {
        propertyName: 'valtimoAssignee.fullName',
        translationKey: 'valtimoAssignee.fullName',
      },
    ],
    defaultSortedColumn: {
      isSorting: true,
      state: {
        name: 'created',
        direction: 'ASC',
      },
    },
  },
  customLeftSidebar: {
    defaultMenuWidth: 256,
    maxMenuWidth: 550,
    minMenuWidth: 150,
  },
  caseObjectTypes: {},
  featureToggles: {
    showUserNameInTopBar: true,
    experimentalDmnEditing: true,
    largeLogoMargin: false,
    sortFilesByDate: true,
    disableCaseCount: false,
    returnToLastUrlAfterTokenExpiration: true,
    useStartEventNameAsStartFormTitle: true,
    allowUserThemeSwitching: true,
    enableUserNameInTopBarToggle: true,
    enableTabManagement: true,
    enableObjectManagement: true,
    enableFormViewModel: true,
    enableIntermediateSave: true,
    enableFormFlowBreadcrumbs: true,
    enableTaskPanel: true,
    enablePbacDocumentenApiDocuments: true,
    enableSuppressDocumentError: false,
  },
  formioOptions: {
    languageOverride: {
      'en-US': {
        decimalSeparator: ':',
        delimiter: ':',
      },
    },
  },
};

