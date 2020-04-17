export interface IBeacon {
  id?: number;
  contentName?: string;
  contentType?: string;
  content?: string;
  contentDescription?: string;
}

export class Beacon implements IBeacon {
  constructor(
    public id?: number,
    public contentName?: string,
    public contentType?: string,
    public content?: string,
    public contentDescription?: string
  ) {}
}
