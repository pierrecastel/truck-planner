/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TruckplannerTestModule } from '../../../test.module';
import { TruckCompanyUpdateComponent } from 'app/entities/truck-company/truck-company-update.component';
import { TruckCompanyService } from 'app/entities/truck-company/truck-company.service';
import { TruckCompany } from 'app/shared/model/truck-company.model';

describe('Component Tests', () => {
    describe('TruckCompany Management Update Component', () => {
        let comp: TruckCompanyUpdateComponent;
        let fixture: ComponentFixture<TruckCompanyUpdateComponent>;
        let service: TruckCompanyService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TruckplannerTestModule],
                declarations: [TruckCompanyUpdateComponent]
            })
                .overrideTemplate(TruckCompanyUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TruckCompanyUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TruckCompanyService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TruckCompany(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.truckCompany = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TruckCompany();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.truckCompany = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
