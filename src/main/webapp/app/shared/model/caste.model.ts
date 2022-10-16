import dayjs from 'dayjs';
import { IUserMast } from 'app/shared/model/user-mast.model';
import { IOrganisation } from 'app/shared/model/organisation.model';

export interface ICaste {
  id?: number;
  code?: string | null;
  name?: string | null;
  desc?: string | null;
  parentId?: number | null;
  moderatorId?: number | null;
  parentTableKy?: number | null;
  status?: number | null;
  zone?: number | null;
  orgId?: number | null;
  hist?: number | null;
  addedBy?: number | null;
  addedOn?: string | null;
  updatedBy?: number | null;
  updatedOn?: string | null;
  approvedBy?: number | null;
  approvedOn?: string | null;
  otherinfo?: string | null;
  comments?: string | null;
  remarks?: string | null;
  addedByUser?: IUserMast | null;
  updatedByUser?: IUserMast | null;
  approvedByObj?: IUserMast | null;
  parentIdObj?: ICaste | null;
  orgIdObj?: IOrganisation | null;
  ids?: ICaste[] | null;
}

export const defaultValue: Readonly<ICaste> = {};
