import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TruckplannerSharedModule } from 'app/shared';
import {
    DeliveryComponent,
    DeliveryDetailComponent,
    DeliveryUpdateComponent,
    DeliveryDeletePopupComponent,
    DeliveryDeleteDialogComponent,
    deliveryRoute,
    deliveryPopupRoute
} from './';

const ENTITY_STATES = [...deliveryRoute, ...deliveryPopupRoute];

@NgModule({
    imports: [TruckplannerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DeliveryComponent,
        DeliveryDetailComponent,
        DeliveryUpdateComponent,
        DeliveryDeleteDialogComponent,
        DeliveryDeletePopupComponent
    ],
    entryComponents: [DeliveryComponent, DeliveryUpdateComponent, DeliveryDeleteDialogComponent, DeliveryDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TruckplannerDeliveryModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
