export interface IGuest {
  id?: number;
  phoneId?: string;
  name?: string;
  email?: string;
}

export class Guest implements IGuest {
  constructor(public id?: number, public phoneId?: string, public name?: string, public email?: string) {}
}
