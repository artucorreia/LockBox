import axios from 'axios';

const api = axios.create({
  baseURL: 'http://111.111.1.111:8080/api/v1', // replace ip 
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

export default api;
