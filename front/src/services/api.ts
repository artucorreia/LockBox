import AsyncStorage from '@react-native-async-storage/async-storage';
import axios, { AxiosInstance, AxiosResponse } from 'axios';
import { router } from 'expo-router';

export default class Api {
  private url: AxiosInstance;

  constructor() {
    this.url = axios.create({
      baseURL: 'http://192.168.2.180:8080/api', // replace ip
      timeout: 20000,
      headers: {
        'Content-Type': 'application/json',
      },
    });

    this.url.interceptors.request.use(async (config) => {
      const url: string = config.url || '';
      if (!url.includes('/auth')) {
        const token = await AsyncStorage.getItem('user.token');
        if (token) {
          config.headers['Authorization'] = `Bearer ${token}`;
        }
      }

      return config;
    });

    this.url.interceptors.response.use(
      (response) => response,
      async (error) => {
        if (error.response?.status === 403) {
          router.push('/(auth)/login');
        }
        return Promise.reject(error);
      },
    );
  }

  public async get<T>(url: string): Promise<T> {
    return await this.url.get<T>(url).then((data) => this.getData(data));
  }

  public async post<T>(url: string, object: any): Promise<T> {
    // TODO: remove
    console.log(object);
    return await this.url.post(url, object).then((data) => this.getData(data));
  }

  private getData<T>(response: AxiosResponse<T>): T {
    return response.data;
  }
}
