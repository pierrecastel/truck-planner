import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TruckplannerSharedModule } from 'app/shared';
import {
    TruckComponent,
    TruckDetailComponent,
    TruckUpdateComponent,
    TruckDeletePopupComponent,
    TruckDeleteDialogComponent,
    truckRoute,
    truckPopupRoute
} from './';

const ENTITY_STATES = [...truckRoute, ...truckPopupRoute];

@NgModule({
    imports: [TruckplannerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [TruckComponent, TruckDetailComponent, TruckUpdateComponent, TruckDeleteDialogComponent, TruckDeletePopupComponent],
    entryComponents: [TruckComponent, TruckUpdateComponent, TruckDeleteDialogComponent, TruckDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TruckplannerTruckModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
