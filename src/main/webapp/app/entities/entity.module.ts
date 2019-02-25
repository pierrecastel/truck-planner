import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'order',
                loadChildren: './order/order.module#TruckplannerOrderModule'
            },
            {
                path: 'delivery',
                loadChildren: './delivery/delivery.module#TruckplannerDeliveryModule'
            },
            {
                path: 'truck-company',
                loadChildren: './truck-company/truck-company.module#TruckplannerTruckCompanyModule'
            },
            {
                path: 'truck',
                loadChildren: './truck/truck.module#TruckplannerTruckModule'
            },
            {
                path: 'driver',
                loadChildren: './driver/driver.module#TruckplannerDriverModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TruckplannerEntityModule {}
