/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TruckplannerTestModule } from '../../../test.module';
import { TruckComponent } from 'app/entities/truck/truck.component';
import { TruckService } from 'app/entities/truck/truck.service';
import { Truck } from 'app/shared/model/truck.model';

describe('Component Tests', () => {
    describe('Truck Management Component', () => {
        let comp: TruckComponent;
        let fixture: ComponentFixture<TruckComponent>;
        let service: TruckService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TruckplannerTestModule],
                declarations: [TruckComponent],
                providers: []
            })
                .overrideTemplate(TruckComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TruckComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TruckService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Truck(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.trucks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
