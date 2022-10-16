import dayjs from 'dayjs';
import { IUserMast } from 'app/shared/model/user-mast.model';
import { IOrganisation } from 'app/shared/model/organisation.model';

export interface IUserActivity {
  id?: number;
  userId?: number | null;
  loggedon?: string | null;
  activeduration?: number | null;
  ipAdr?: string | null;
  usrLocation?: string | null;
  lat?: number | null;
  longi?: number | null;
  pin?: string | null;
  addedBy?: number | null;
  orgId?: number | null;
  addedOn?: string | null;
  addedByUser?: IUserMast | null;
  userObj?: IUserMast | null;
  addedByUseract?: IUserMast | null;
  orgIdObj?: IOrganisation | null;
  useractObj?: IUserMast | null;
}

export const defaultValue: Readonly<IUserActivity> = {};
