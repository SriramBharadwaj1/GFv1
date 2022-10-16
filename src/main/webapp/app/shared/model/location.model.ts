import dayjs from 'dayjs';
import { IUserMast } from 'app/shared/model/user-mast.model';
import { IOrganisation } from 'app/shared/model/organisation.model';

export interface ILocation {
  id?: number;
  code?: string | null;
  name?: string | null;
  desc?: string | null;
  parentId?: number | null;
  moderator1Id?: number | null;
  moderator2Id?: number | null;
  moderator1Code?: string | null;
  moderator2Code?: string | null;
  parentTableKy?: number | null;
  type?: number | null;
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
  approvedByUser?: IUserMast | null;
  moderator1IdUser?: IUserMast | null;
  moderator2IdUser?: IUserMast | null;
  parentIdObj?: ILocation | null;
  orgIdObj?: IOrganisation | null;
}

export const defaultValue: Readonly<ILocation> = {};
