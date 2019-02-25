import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITruckCompany } from 'app/shared/model/truck-company.model';

@Component({
    selector: 'jhi-truck-company-detail',
    templateUrl: './truck-company-detail.component.html'
})
export class TruckCompanyDetailComponent implements OnInit {
    truckCompany: ITruckCompany;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ truckCompany }) => {
            this.truckCompany = truckCompany;
        });
    }

    previousState() {
        window.history.back();
    }
}
