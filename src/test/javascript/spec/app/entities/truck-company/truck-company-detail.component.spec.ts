/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TruckplannerTestModule } from '../../../test.module';
import { TruckCompanyDetailComponent } from 'app/entities/truck-company/truck-company-detail.component';
import { TruckCompany } from 'app/shared/model/truck-company.model';

describe('Component Tests', () => {
    describe('TruckCompany Management Detail Component', () => {
        let comp: TruckCompanyDetailComponent;
        let fixture: ComponentFixture<TruckCompanyDetailComponent>;
        const route = ({ data: of({ truckCompany: new TruckCompany(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TruckplannerTestModule],
                declarations: [TruckCompanyDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TruckCompanyDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TruckCompanyDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.truckCompany).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
