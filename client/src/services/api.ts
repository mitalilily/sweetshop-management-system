import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add token to requests
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface AuthResponse {
  token: string;
}

export interface Sweet {
  id: string;
  name: string;
  category: string;
  price: number;
  quantity: number;
}

export interface AdminRegisterRequest {
  username: string;
  email: string;
  password: string;
  adminSecret: string;
}

export const authAPI = {
  register: (data: RegisterRequest) => api.post<AuthResponse>('/auth/register', data),
  login: (data: LoginRequest) => api.post<AuthResponse>('/auth/login', data),
  registerAdmin: (data: AdminRegisterRequest) => api.post<AuthResponse>('/auth/register/admin', data),
};

export const sweetsAPI = {
  getAll: () => api.get<Sweet[]>('/sweets'),
  getById: (id: string) => api.get<Sweet>(`/sweets/${id}`),
  create: (data: Omit<Sweet, 'id'>) => api.post<Sweet>('/sweets', data),
  update: (id: string, data: Partial<Sweet>) => api.put<Sweet>(`/sweets/${id}`, data),
  delete: (id: string) => api.delete(`/sweets/${id}`),
  search: (params: { name?: string; category?: string; minPrice?: number; maxPrice?: number }) => 
    api.get<Sweet[]>('/sweets/search', { params }),
};

export const inventoryAPI = {
  purchase: (id: string, quantity: number) => 
    api.post<Sweet>(`/sweets/${id}/purchase`, { quantity }),
  restock: (id: string, quantity: number) => 
    api.post<Sweet>(`/sweets/${id}/restock`, { quantity }),
};

export default api;

