import dayjs from 'dayjs';
import { IOrganisation } from 'app/shared/model/organisation.model';

export interface IOrgDetails {
  id?: number;
  orgId?: number | null;
  name?: string | null;
  type?: number | null;
  keyName?: string | null;
  keyValue?: string | null;
  isActive?: number | null;
  isEnable?: number | null;
  addedBy?: number | null;
  addedOn?: string | null;
  updatedBy?: number | null;
  updatedOn?: string | null;
  approvedBy?: number | null;
  approvedOn?: string | null;
  extraFields?: string | null;
  orgIdObj?: IOrganisation | null;
}

export const defaultValue: Readonly<IOrgDetails> = {};
