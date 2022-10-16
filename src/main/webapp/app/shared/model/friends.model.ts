import dayjs from 'dayjs';
import { IUserMast } from 'app/shared/model/user-mast.model';
import { IOrganisation } from 'app/shared/model/organisation.model';

export interface IFriends {
  id?: number;
  userId?: number | null;
  friendId?: number | null;
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
  type?: number | null;
  addedByUser?: IUserMast | null;
  updatedByUser?: IUserMast | null;
  approvedByUser?: IUserMast | null;
  userIdObj?: IUserMast | null;
  friendIdObj?: IUserMast | null;
  orgIdObj?: IOrganisation | null;
}

export const defaultValue: Readonly<IFriends> = {};
