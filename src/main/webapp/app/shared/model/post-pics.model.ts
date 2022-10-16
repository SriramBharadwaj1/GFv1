import dayjs from 'dayjs';
import { IUserMast } from 'app/shared/model/user-mast.model';
import { IPost } from 'app/shared/model/post.model';
import { IOrganisation } from 'app/shared/model/organisation.model';

export interface IPostPics {
  id?: number;
  postid?: number | null;
  pic?: string | null;
  picpath?: string | null;
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
  status?: number | null;
  addedByUser?: IUserMast | null;
  updatedByUser?: IUserMast | null;
  approvedByUser?: IUserMast | null;
  postObj?: IPost | null;
  orgIdObj?: IOrganisation | null;
}

export const defaultValue: Readonly<IPostPics> = {};
