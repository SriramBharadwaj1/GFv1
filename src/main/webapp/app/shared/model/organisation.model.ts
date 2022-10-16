import dayjs from 'dayjs';

export interface IOrganisation {
  id?: number;
  code?: string | null;
  name?: string | null;
  type?: number | null;
  parentOrgId?: number | null;
  primaryContactId?: number | null;
  orgHead?: number | null;
  locationId?: number | null;
  website?: string | null;
  layout?: string | null;
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
  extraFields?: string | null;
  buildFilePath?: string | null;
  shortName?: string | null;
  vatNo?: string | null;
  moduleKy?: number | null;
  hashCode?: string | null;
  hashCertificate?: string | null;
}

export const defaultValue: Readonly<IOrganisation> = {};
