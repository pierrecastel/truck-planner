import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Delivery } from 'app/shared/model/delivery.model';
import { DeliveryService } from './delivery.service';
import { DeliveryComponent } from './delivery.component';
import { DeliveryDetailComponent } from './delivery-detail.component';
import { DeliveryUpdateComponent } from './delivery-update.component';
import { DeliveryDeletePopupComponent } from './delivery-delete-dialog.component';
import { IDelivery } from 'app/shared/model/delivery.model';

@Injectable({ providedIn: 'root' })
export class DeliveryResolve implements Resolve<IDelivery> {
    constructor(private service: DeliveryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDelivery> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Delivery>) => response.ok),
                map((delivery: HttpResponse<Delivery>) => delivery.body)
            );
        }
        return of(new Delivery());
    }
}

export const deliveryRoute: Routes = [
    {
        path: '',
        component: DeliveryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truckplannerApp.delivery.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DeliveryDetailComponent,
        resolve: {
            delivery: DeliveryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truckplannerApp.delivery.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DeliveryUpdateComponent,
        resolve: {
            delivery: DeliveryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truckplannerApp.delivery.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DeliveryUpdateComponent,
        resolve: {
            delivery: DeliveryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truckplannerApp.delivery.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const deliveryPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DeliveryDeletePopupComponent,
        resolve: {
            delivery: DeliveryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truckplannerApp.delivery.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
