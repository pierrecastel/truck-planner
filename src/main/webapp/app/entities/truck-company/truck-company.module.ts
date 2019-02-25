import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TruckplannerSharedModule } from 'app/shared';
import {
    TruckCompanyComponent,
    TruckCompanyDetailComponent,
    TruckCompanyUpdateComponent,
    TruckCompanyDeletePopupComponent,
    TruckCompanyDeleteDialogComponent,
    truckCompanyRoute,
    truckCompanyPopupRoute
} from './';

const ENTITY_STATES = [...truckCompanyRoute, ...truckCompanyPopupRoute];

@NgModule({
    imports: [TruckplannerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TruckCompanyComponent,
        TruckCompanyDetailComponent,
        TruckCompanyUpdateComponent,
        TruckCompanyDeleteDialogComponent,
        TruckCompanyDeletePopupComponent
    ],
    entryComponents: [
        TruckCompanyComponent,
        TruckCompanyUpdateComponent,
        TruckCompanyDeleteDialogComponent,
        TruckCompanyDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TruckplannerTruckCompanyModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
