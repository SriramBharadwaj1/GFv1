import dayjs from 'dayjs';
import { IUserMast } from 'app/shared/model/user-mast.model';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { IRating } from 'app/shared/model/rating.model';
import { IRemarks } from 'app/shared/model/remarks.model';
import { IComment } from 'app/shared/model/comment.model';
import { IPostPics } from 'app/shared/model/post-pics.model';

export interface IPost {
  id?: number;
  message?: string | null;
  category?: number | null;
  categoryName?: string | null;
  meta?: string | null;
  tableKy?: number | null;
  isActive?: number | null;
  actRejReason?: number | null;
  isEnable?: number | null;
  addedBy?: number | null;
  addedOn?: string | null;
  updatedBy?: number | null;
  updatedOn?: string | null;
  approvedBy?: number | null;
  approvedOn?: string | null;
  comments?: string | null;
  remarks?: string | null;
  type?: number | null;
  isSalesPost?: number | null;
  salesCategory?: number | null;
  price?: number | null;
  validFrom?: string | null;
  validTill?: string | null;
  phoneArea?: string | null;
  phno?: string | null;
  videoGrp?: number | null;
  orgId?: number | null;
  otherInfo?: string | null;
  addedByUser?: IUserMast | null;
  updatedByUser?: IUserMast | null;
  approvedByUser?: IUserMast | null;
  orgIdObj?: IOrganisation | null;
  ratposts?: IRating[] | null;
  remposts?: IRemarks[] | null;
  composts?: IComment[] | null;
  ids?: IPostPics[] | null;
}

export const defaultValue: Readonly<IPost> = {};
