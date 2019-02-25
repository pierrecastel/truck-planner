import { ITruckCompany } from 'app/shared/model/truck-company.model';
import { IDriver } from 'app/shared/model/driver.model';

export const enum Cool {
    NO = 'NO',
    FNA = 'FNA',
    FRC = 'FRC'
}

export const enum TopType {
    SOFT = 'SOFT',
    HARD = 'HARD'
}

export interface ITruck {
    id?: number;
    number?: string;
    truckCountry?: string;
    trailerCountry?: string;
    truckLicense?: string;
    trailerLicense?: string;
    adr?: boolean;
    big?: boolean;
    rollerBed?: boolean;
    cool?: Cool;
    minTemperature?: number;
    maxTemperature?: number;
    maximumHeight?: number;
    topType?: TopType;
    truckCompany?: ITruckCompany;
    driver?: IDriver;
}

export class Truck implements ITruck {
    constructor(
        public id?: number,
        public number?: string,
        public truckCountry?: string,
        public trailerCountry?: string,
        public truckLicense?: string,
        public trailerLicense?: string,
        public adr?: boolean,
        public big?: boolean,
        public rollerBed?: boolean,
        public cool?: Cool,
        public minTemperature?: number,
        public maxTemperature?: number,
        public maximumHeight?: number,
        public topType?: TopType,
        public truckCompany?: ITruckCompany,
        public driver?: IDriver
    ) {
        this.adr = this.adr || false;
        this.big = this.big || false;
        this.rollerBed = this.rollerBed || false;
    }
}
