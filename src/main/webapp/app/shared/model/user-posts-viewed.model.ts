import dayjs from 'dayjs';
import { IUserMast } from 'app/shared/model/user-mast.model';
import { IOrganisation } from 'app/shared/model/organisation.model';

export interface IUserPostsViewed {
  id?: number;
  userId?: number | null;
  postid?: number | null;
  viewdate?: string | null;
  status?: number | null;
  orgId?: number | null;
  userIdObj?: IUserMast | null;
  orgIdObj?: IOrganisation | null;
}

export const defaultValue: Readonly<IUserPostsViewed> = {};
