import dayjs from 'dayjs';
import { IUserMast } from 'app/shared/model/user-mast.model';
import { IGroups } from 'app/shared/model/groups.model';
import { IMasters } from 'app/shared/model/masters.model';
import { IOrganisation } from 'app/shared/model/organisation.model';

export interface IGroupUser {
  id?: number;
  groupId?: number | null;
  grpUser?: number | null;
  isActive?: number | null;
  isEnable?: number | null;
  addedBy?: number | null;
  addedOn?: string | null;
  updatedBy?: number | null;
  updatedOn?: string | null;
  approvedBy?: number | null;
  approvedOn?: string | null;
  comments?: string | null;
  remarks?: string | null;
  orgId?: number | null;
  userType?: number | null;
  addedByUser?: IUserMast | null;
  updatedByUser?: IUserMast | null;
  approvedByUser?: IUserMast | null;
  groupIdObj?: IGroups | null;
  grpUserObj?: IUserMast | null;
  userTypeObj?: IMasters | null;
  orgIdOb?: IOrganisation | null;
}

export const defaultValue: Readonly<IGroupUser> = {};
