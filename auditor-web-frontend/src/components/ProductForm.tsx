import { Product, productsApi } from '@/lib/api'
import { useMutation, useQueryClient } from '@tanstack/react-query'
import { X } from 'lucide-react'
import { useEffect } from 'react'
import { useForm } from 'react-hook-form'
import toast from 'react-hot-toast'

interface ProductFormProps {
  product?: Product | null
  entityId: number
  onClose: () => void
}

interface ProductFormData {
  name: string
  description?: string
  code?: string
  salePrice: number
  costPrice?: number
  stock: number
  minStock: number
  isSellable: boolean
  isBuildable: boolean
}

export default function ProductForm({ product, entityId, onClose }: ProductFormProps) {
  const queryClient = useQueryClient()
  const isEditing = !!product

  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm<ProductFormData>({
    defaultValues: {
      name: '',
      description: '',
      code: '',
      salePrice: 0,
      costPrice: 0,
      stock: 0,
      minStock: 0,
      isSellable: true,
      isBuildable: false,
    },
  })

  useEffect(() => {
    if (product) {
      reset({
        name: product.name,
        description: product.description || '',
        code: product.code || '',
        salePrice: product.salePrice,
        costPrice: product.costPrice || 0,
        stock: product.stock,
        minStock: product.minStock,
        isSellable: product.isSellable,
        isBuildable: product.isBuildable,
      })
    }
  }, [product, reset])

  const createMutation = useMutation({
    mutationFn: (data: ProductFormData) => 
      productsApi.create({
        ...data,
        entityId,
        active: true,
        createdAt: new Date().toISOString(),
        updatedAt: new Date().toISOString(),
      }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['products'] })
      toast.success('Producto creado exitosamente')
      onClose()
    },
    onError: () => {
      toast.error('Error al crear el producto')
    },
  })

  const updateMutation = useMutation({
    mutationFn: (data: ProductFormData) => 
      productsApi.update(product!.id, { ...data, entityId }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['products'] })
      toast.success('Producto actualizado exitosamente')
      onClose()
    },
    onError: () => {
      toast.error('Error al actualizar el producto')
    },
  })

  const onSubmit = (data: ProductFormData) => {
    if (isEditing) {
      updateMutation.mutate(data)
    } else {
      createMutation.mutate(data)
    }
  }

  const isLoading = createMutation.isPending || updateMutation.isPending

  return (
    <div className="fixed inset-0 z-50 overflow-y-auto">
      <div className="flex min-h-screen items-center justify-center p-4">
        <div className="fixed inset-0 bg-gray-500 bg-opacity-75" onClick={onClose} />
        
        <div className="relative bg-white rounded-lg shadow-xl max-w-md w-full">
          <div className="flex items-center justify-between p-6 border-b">
            <h3 className="text-lg font-medium text-gray-900">
              {isEditing ? 'Editar Producto' : 'Nuevo Producto'}
            </h3>
            <button
              onClick={onClose}
              className="text-gray-400 hover:text-gray-600"
            >
              <X className="h-6 w-6" />
            </button>
          </div>

          <form onSubmit={handleSubmit(onSubmit)} className="p-6 space-y-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Nombre *
              </label>
              <input
                type="text"
                {...register('name', { required: 'El nombre es obligatorio' })}
                className="input"
                placeholder="Nombre del producto"
              />
              {errors.name && (
                <p className="text-red-600 text-sm mt-1">{errors.name.message}</p>
              )}
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Descripción
              </label>
              <textarea
                {...register('description')}
                className="input"
                rows={3}
                placeholder="Descripción del producto"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Código
              </label>
              <input
                type="text"
                {...register('code')}
                className="input"
                placeholder="Código del producto"
              />
            </div>

            <div className="grid grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Precio de Venta *
                </label>
                <input
                  type="number"
                  step="0.01"
                  {...register('salePrice', { 
                    required: 'El precio de venta es obligatorio',
                    min: { value: 0.01, message: 'Debe ser mayor que 0' }
                  })}
                  className="input"
                  placeholder="0.00"
                />
                {errors.salePrice && (
                  <p className="text-red-600 text-sm mt-1">{errors.salePrice.message}</p>
                )}
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Precio de Costo
                </label>
                <input
                  type="number"
                  step="0.01"
                  {...register('costPrice', { min: { value: 0, message: 'No puede ser negativo' } })}
                  className="input"
                  placeholder="0.00"
                />
                {errors.costPrice && (
                  <p className="text-red-600 text-sm mt-1">{errors.costPrice.message}</p>
                )}
              </div>
            </div>

            <div className="grid grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Stock Actual
                </label>
                <input
                  type="number"
                  step="0.001"
                  {...register('stock', { min: { value: 0, message: 'No puede ser negativo' } })}
                  className="input"
                  placeholder="0"
                />
                {errors.stock && (
                  <p className="text-red-600 text-sm mt-1">{errors.stock.message}</p>
                )}
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Stock Mínimo
                </label>
                <input
                  type="number"
                  step="0.001"
                  {...register('minStock', { min: { value: 0, message: 'No puede ser negativo' } })}
                  className="input"
                  placeholder="0"
                />
                {errors.minStock && (
                  <p className="text-red-600 text-sm mt-1">{errors.minStock.message}</p>
                )}
              </div>
            </div>

            <div className="space-y-3">
              <div className="flex items-center">
                <input
                  type="checkbox"
                  {...register('isSellable')}
                  className="h-4 w-4 text-primary-600 focus:ring-primary-500 border-gray-300 rounded"
                />
                <label className="ml-2 block text-sm text-gray-900">
                  Producto vendible
                </label>
              </div>

              <div className="flex items-center">
                <input
                  type="checkbox"
                  {...register('isBuildable')}
                  className="h-4 w-4 text-primary-600 focus:ring-primary-500 border-gray-300 rounded"
                />
                <label className="ml-2 block text-sm text-gray-900">
                  Producto fabricable
                </label>
              </div>
            </div>

            <div className="flex justify-end space-x-3 pt-4 border-t">
              <button
                type="button"
                onClick={onClose}
                className="btn-secondary"
                disabled={isLoading}
              >
                Cancelar
              </button>
              <button
                type="submit"
                className="btn-primary"
                disabled={isLoading}
              >
                {isLoading ? 'Guardando...' : (isEditing ? 'Actualizar' : 'Crear')}
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  )
}