import axios from 'axios'

const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api'

export const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
})

// Request interceptor
api.interceptors.request.use(
  (config) => {
    // Add auth token if available
    const token = localStorage.getItem('auth_token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// Response interceptor
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      // Handle unauthorized access
      localStorage.removeItem('auth_token')
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

// Types
export interface Entity {
  id: number
  name: string
  description?: string
  code?: string
  address?: string
  phone?: string
  email?: string
  active: boolean
  createdAt: string
  updatedAt: string
}

export interface Product {
  id: number
  name: string
  description?: string
  code?: string
  salePrice: number
  costPrice?: number
  stock: number
  minStock: number
  measureUnitId?: number
  measureUnitName?: string
  measureUnitAbbreviation?: string
  categoryId?: number
  categoryName?: string
  entityId: number
  entityName?: string
  isBuildable: boolean
  isSellable: boolean
  active: boolean
  createdAt: string
  updatedAt: string
}

export interface OperationDetail {
  id?: number
  operationId?: number
  productId: number
  productName?: string
  productCode?: string
  quantity: number
  unitPrice: number
  subtotal: number
  discountPercentage?: number
  discountAmount?: number
}

export interface Operation {
  id?: number
  operationType: 'SALE' | 'PURCHASE' | 'INCOME' | 'EXPENSE' | 'TRANSFER'
  operationDate: string
  total: number
  observations?: string
  documentNumber?: string
  entityId: number
  entityName?: string
  status: 'PENDING' | 'COMPLETED' | 'CANCELLED'
  details?: OperationDetail[]
  active: boolean
  createdAt?: string
  updatedAt?: string
}

// API functions
export const entitiesApi = {
  getAll: () => api.get<Entity[]>('/entities'),
  getById: (id: number) => api.get<Entity>(`/entities/${id}`),
  getByCode: (code: string) => api.get<Entity>(`/entities/code/${code}`),
  search: (name: string) => api.get<Entity[]>(`/entities/search?name=${name}`),
  create: (entity: Omit<Entity, 'id' | 'active' | 'createdAt' | 'updatedAt'>) => 
    api.post<Entity>('/entities', entity),
  update: (id: number, entity: Partial<Entity>) => 
    api.put<Entity>(`/entities/${id}`, entity),
  delete: (id: number) => api.delete(`/entities/${id}`),
  existsByCode: (code: string) => api.get<boolean>(`/entities/exists/code/${code}`),
}

export const productsApi = {
  getByEntity: (entityId: number) => api.get<Product[]>(`/products/entity/${entityId}`),
  getByEntityPaginated: (entityId: number, page: number, size: number) => 
    api.get<{content: Product[], totalElements: number, totalPages: number}>(`/products/entity/${entityId}/paginated?page=${page}&size=${size}`),
  getById: (id: number) => api.get<Product>(`/products/${id}`),
  getByCodeAndEntity: (code: string, entityId: number) => 
    api.get<Product>(`/products/entity/${entityId}/code/${code}`),
  search: (entityId: number, name: string) => 
    api.get<Product[]>(`/products/entity/${entityId}/search?name=${name}`),
  getSellable: (entityId: number) => api.get<Product[]>(`/products/entity/${entityId}/sellable`),
  getLowStock: (entityId: number) => api.get<Product[]>(`/products/entity/${entityId}/low-stock`),
  create: (product: Omit<Product, 'id' | 'active' | 'createdAt' | 'updatedAt'>) => 
    api.post<Product>('/products', product),
  update: (id: number, product: Partial<Product>) => 
    api.put<Product>(`/products/${id}`, product),
  delete: (id: number) => api.delete(`/products/${id}`),
  updateStock: (id: number, stock: number) => 
    api.put<Product>(`/products/${id}/stock?stock=${stock}`),
  adjustStock: (id: number, adjustment: number) => 
    api.put<Product>(`/products/${id}/stock/adjust?adjustment=${adjustment}`),
  count: (entityId: number) => api.get<number>(`/products/entity/${entityId}/count`),
}

export const operationsApi = {
  getByEntity: (entityId: number, page: number, size: number) => 
    api.get<{content: Operation[], totalElements: number, totalPages: number}>(`/operations/entity/${entityId}?page=${page}&size=${size}`),
  getById: (id: number) => api.get<Operation>(`/operations/${id}`),
  getByEntityAndType: (entityId: number, operationType: string) => 
    api.get<Operation[]>(`/operations/entity/${entityId}/type/${operationType}`),
  getByDateRange: (entityId: number, startDate: string, endDate: string) => 
    api.get<Operation[]>(`/operations/entity/${entityId}/date-range?startDate=${startDate}&endDate=${endDate}`),
  getRecent: (entityId: number, limit: number = 10) => 
    api.get<Operation[]>(`/operations/entity/${entityId}/recent?limit=${limit}`),
  create: (operation: Omit<Operation, 'id' | 'active' | 'createdAt' | 'updatedAt'>) => 
    api.post<Operation>('/operations', operation),
  update: (id: number, operation: Partial<Operation>) => 
    api.put<Operation>(`/operations/${id}`, operation),
  complete: (id: number) => api.put<Operation>(`/operations/${id}/complete`),
  cancel: (id: number) => api.put<Operation>(`/operations/${id}/cancel`),
  delete: (id: number) => api.delete(`/operations/${id}`),
  calculateTotal: (entityId: number, operationType: string, startDate: string, endDate: string) => 
    api.get<number>(`/operations/entity/${entityId}/total/${operationType}?startDate=${startDate}&endDate=${endDate}`),
  count: (entityId: number, operationType: string) => 
    api.get<number>(`/operations/entity/${entityId}/count/${operationType}`),
}