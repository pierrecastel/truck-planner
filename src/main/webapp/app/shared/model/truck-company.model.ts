import { ITruck } from 'app/shared/model/truck.model';

export const enum CommunicationMode {
    API = 'API',
    MAIL = 'MAIL',
    XML = 'XML'
}

export interface ITruckCompany {
    id?: number;
    email?: string;
    comunicationMode?: CommunicationMode;
    trucks?: ITruck[];
}

export class TruckCompany implements ITruckCompany {
    constructor(public id?: number, public email?: string, public comunicationMode?: CommunicationMode, public trucks?: ITruck[]) {}
}
