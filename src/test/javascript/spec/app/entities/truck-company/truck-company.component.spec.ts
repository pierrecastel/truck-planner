/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TruckplannerTestModule } from '../../../test.module';
import { TruckCompanyComponent } from 'app/entities/truck-company/truck-company.component';
import { TruckCompanyService } from 'app/entities/truck-company/truck-company.service';
import { TruckCompany } from 'app/shared/model/truck-company.model';

describe('Component Tests', () => {
    describe('TruckCompany Management Component', () => {
        let comp: TruckCompanyComponent;
        let fixture: ComponentFixture<TruckCompanyComponent>;
        let service: TruckCompanyService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TruckplannerTestModule],
                declarations: [TruckCompanyComponent],
                providers: []
            })
                .overrideTemplate(TruckCompanyComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TruckCompanyComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TruckCompanyService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TruckCompany(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.truckCompanies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
