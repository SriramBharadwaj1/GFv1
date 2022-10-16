import dayjs from 'dayjs';
import { IPost } from 'app/shared/model/post.model';
import { IUserMast } from 'app/shared/model/user-mast.model';
import { IOrganisation } from 'app/shared/model/organisation.model';

export interface IRemarks {
  id?: number;
  message?: string | null;
  category?: number | null;
  categoryName?: string | null;
  meta?: string | null;
  postId?: number | null;
  isActive?: number | null;
  apprRejReason?: number | null;
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
  otherInfo?: string | null;
  repostIdObj?: IPost | null;
  addedByUser?: IUserMast | null;
  updatedByUser?: IUserMast | null;
  approvedByUser?: IUserMast | null;
  orgIdObj?: IOrganisation | null;
}

export const defaultValue: Readonly<IRemarks> = {};
