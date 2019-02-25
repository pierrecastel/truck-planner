export interface IDriver {
    id?: number;
    permit?: string;
    firstName?: string;
    lastName?: string;
}

export class Driver implements IDriver {
    constructor(public id?: number, public permit?: string, public firstName?: string, public lastName?: string) {}
}
