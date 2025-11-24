import Vault from "./Vault";

export default interface Category {
  id?: number;
  name?: string;
  vaults?: Vault[];
  createdAt?: Date;
  updatedAt?: Date;
}
