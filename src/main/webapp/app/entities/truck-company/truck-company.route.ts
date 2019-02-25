import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TruckCompany } from 'app/shared/model/truck-company.model';
import { TruckCompanyService } from './truck-company.service';
import { TruckCompanyComponent } from './truck-company.component';
import { TruckCompanyDetailComponent } from './truck-company-detail.component';
import { TruckCompanyUpdateComponent } from './truck-company-update.component';
import { TruckCompanyDeletePopupComponent } from './truck-company-delete-dialog.component';
import { ITruckCompany } from 'app/shared/model/truck-company.model';

@Injectable({ providedIn: 'root' })
export class TruckCompanyResolve implements Resolve<ITruckCompany> {
    constructor(private service: TruckCompanyService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITruckCompany> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TruckCompany>) => response.ok),
                map((truckCompany: HttpResponse<TruckCompany>) => truckCompany.body)
            );
        }
        return of(new TruckCompany());
    }
}

export const truckCompanyRoute: Routes = [
    {
        path: '',
        component: TruckCompanyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truckplannerApp.truckCompany.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TruckCompanyDetailComponent,
        resolve: {
            truckCompany: TruckCompanyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truckplannerApp.truckCompany.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TruckCompanyUpdateComponent,
        resolve: {
            truckCompany: TruckCompanyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truckplannerApp.truckCompany.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TruckCompanyUpdateComponent,
        resolve: {
            truckCompany: TruckCompanyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truckplannerApp.truckCompany.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const truckCompanyPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TruckCompanyDeletePopupComponent,
        resolve: {
            truckCompany: TruckCompanyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truckplannerApp.truckCompany.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
