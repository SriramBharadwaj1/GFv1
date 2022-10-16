import dayjs from 'dayjs';
import { IUserMast } from 'app/shared/model/user-mast.model';
import { IMasters } from 'app/shared/model/masters.model';
import { IOrganisation } from 'app/shared/model/organisation.model';

export interface IEvents {
  id?: number;
  name?: string | null;
  userId?: number | null;
  isActive?: number | null;
  apprRejReason?: number | null;
  eventType?: number | null;
  isEnable?: number | null;
  addedBy?: number | null;
  addedOn?: string | null;
  updatedBy?: number | null;
  updatedOn?: string | null;
  approvedBy?: number | null;
  orgId?: number | null;
  approvedOn?: string | null;
  addedByUsr?: IUserMast | null;
  updatedByUsr?: IUserMast | null;
  approvedByUsr?: IUserMast | null;
  userIdUsr?: IUserMast | null;
  eventTypeObj?: IMasters | null;
  orgIdObj?: IOrganisation | null;
}

export const defaultValue: Readonly<IEvents> = {};
