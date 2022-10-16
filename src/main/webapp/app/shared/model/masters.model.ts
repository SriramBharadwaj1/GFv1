import { IUserMast } from 'app/shared/model/user-mast.model';
import { IOrganisation } from 'app/shared/model/organisation.model';

export interface IMasters {
  id?: number;
  key?: number | null;
  val?: string | null;
  code?: number | null;
  codeval?: string | null;
  status?: number | null;
  orgId?: number | null;
  addedByUser?: IUserMast | null;
  updatedByUser?: IUserMast | null;
  approvedByUser?: IUserMast | null;
  orgIdObj?: IOrganisation | null;
}

export const defaultValue: Readonly<IMasters> = {};
