import Category from "./Category";

export default interface Vault {
  id?: number;
  url?: string;
  username?: string;
  password?: string;
  category?: Category;
  createdAt?: Date;
  updatedAt?: Date;
}
