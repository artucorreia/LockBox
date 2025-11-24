export default interface Response<T> {
  success: boolean;
  code: number;
  data?: T[];
  message?: string;
}