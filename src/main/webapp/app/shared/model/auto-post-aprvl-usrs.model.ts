import dayjs from 'dayjs';
import { IUserMast } from 'app/shared/model/user-mast.model';
import { IOrganisation } from 'app/shared/model/organisation.model';

export interface IAutoPostAprvlUsrs {
  id?: number;
  apUserId?: number | null;
  tableKy?: number | null;
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
  extraFields?: string | null;
  user?: IUserMast | null;
  addedByUser?: IUserMast | null;
  updatedByUser?: IUserMast | null;
  approvedByUser?: IUserMast | null;
  orgIdObj?: IOrganisation | null;
}

export const defaultValue: Readonly<IAutoPostAprvlUsrs> = {};
