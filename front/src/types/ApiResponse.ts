import Category from './Category';
import Vault from './Vault';

export default interface ApiResponse {
  success: boolean;
  code: number;
  message?: string;
  data?: Vault | Vault[] | Category | Category[];
}
