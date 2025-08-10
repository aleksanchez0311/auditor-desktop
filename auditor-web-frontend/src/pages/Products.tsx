import ConfirmDialog from '@/components/ConfirmDialog'
import ProductForm from '@/components/ProductForm'
import { entitiesApi, Product, productsApi } from '@/lib/api'
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query'
import { Edit, Package, Plus, Search, Trash2 } from 'lucide-react'
import { useState } from 'react'
import toast from 'react-hot-toast'

export default function Products() {
  const [searchTerm, setSearchTerm] = useState('')
  const [showForm, setShowForm] = useState(false)
  const [editingProduct, setEditingProduct] = useState<Product | null>(null)
  const [deletingProduct, setDeletingProduct] = useState<Product | null>(null)
  const queryClient = useQueryClient()

  // Get first entity (for demo purposes)
  const { data: entities } = useQuery({
    queryKey: ['entities'],
    queryFn: () => entitiesApi.getAll().then(res => res.data),
  })

  const entityId = entities?.[0]?.id || 1

  // Get products
  const { data: products, isLoading } = useQuery({
    queryKey: ['products', entityId, searchTerm],
    queryFn: () => {
      if (searchTerm) {
        return productsApi.search(entityId, searchTerm).then(res => res.data)
      }
      return productsApi.getByEntity(entityId).then(res => res.data)
    },
    enabled: !!entityId,
  })

  // Delete mutation
  const deleteMutation = useMutation({
    mutationFn: (id: number) => productsApi.delete(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['products'] })
      toast.success('Producto eliminado exitosamente')
      setDeletingProduct(null)
    },
    onError: () => {
      toast.error('Error al eliminar el producto')
    },
  })

  const handleEdit = (product: Product) => {
    setEditingProduct(product)
    setShowForm(true)
  }

  const handleDelete = (product: Product) => {
    setDeletingProduct(product)
  }

  const confirmDelete = () => {
    if (deletingProduct) {
      deleteMutation.mutate(deletingProduct.id)
    }
  }

  const handleFormClose = () => {
    setShowForm(false)
    setEditingProduct(null)
  }

  if (isLoading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-primary-600"></div>
      </div>
    )
  }

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex justify-between items-center">
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Productos</h1>
          <p className="text-gray-600">
            Gestión de productos - {entities?.[0]?.name || 'Cargando...'}
          </p>
        </div>
        <button
          onClick={() => setShowForm(true)}
          className="btn-primary"
        >
          <Plus className="h-4 w-4 mr-2" />
          Nuevo Producto
        </button>
      </div>

      {/* Search */}
      <div className="relative">
        <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 h-4 w-4" />
        <input
          type="text"
          placeholder="Buscar productos..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="input pl-10"
        />
      </div>

      {/* Products Grid */}
      {products && products.length > 0 ? (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
          {products.map((product) => (
            <div key={product.id} className="card">
              <div className="card-content p-4">
                <div className="flex items-start justify-between mb-3">
                  <div className="flex items-center space-x-2">
                    <Package className="h-5 w-5 text-gray-400" />
                    <span className="text-xs text-gray-500">
                      {product.code || 'Sin código'}
                    </span>
                  </div>
                  <div className="flex space-x-1">
                    <button
                      onClick={() => handleEdit(product)}
                      className="p-1 text-gray-400 hover:text-primary-600"
                    >
                      <Edit className="h-4 w-4" />
                    </button>
                    <button
                      onClick={() => handleDelete(product)}
                      className="p-1 text-gray-400 hover:text-red-600"
                    >
                      <Trash2 className="h-4 w-4" />
                    </button>
                  </div>
                </div>

                <h3 className="font-semibold text-gray-900 mb-2 line-clamp-2">
                  {product.name}
                </h3>

                {product.description && (
                  <p className="text-sm text-gray-600 mb-3 line-clamp-2">
                    {product.description}
                  </p>
                )}

                <div className="space-y-2">
                  <div className="flex justify-between items-center">
                    <span className="text-sm text-gray-500">Precio:</span>
                    <span className="font-semibold text-green-600">
                      ${product.salePrice.toLocaleString()}
                    </span>
                  </div>

                  <div className="flex justify-between items-center">
                    <span className="text-sm text-gray-500">Stock:</span>
                    <span className={`font-semibold ${
                      product.stock <= product.minStock ? 'text-red-600' : 'text-gray-900'
                    }`}>
                      {product.stock} {product.measureUnitAbbreviation || 'ud'}
                    </span>
                  </div>

                  {product.categoryName && (
                    <div className="flex justify-between items-center">
                      <span className="text-sm text-gray-500">Categoría:</span>
                      <span className="text-sm text-gray-900">
                        {product.categoryName}
                      </span>
                    </div>
                  )}

                  <div className="flex justify-between items-center pt-2 border-t">
                    <div className="flex space-x-2">
                      {product.isSellable && (
                        <span className="inline-flex items-center px-2 py-1 rounded-full text-xs font-medium bg-green-100 text-green-800">
                          Vendible
                        </span>
                      )}
                      {product.isBuildable && (
                        <span className="inline-flex items-center px-2 py-1 rounded-full text-xs font-medium bg-blue-100 text-blue-800">
                          Fabricable
                        </span>
                      )}
                    </div>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      ) : (
        <div className="text-center py-12">
          <Package className="mx-auto h-12 w-12 text-gray-400" />
          <h3 className="mt-2 text-sm font-medium text-gray-900">
            No hay productos
          </h3>
          <p className="mt-1 text-sm text-gray-500">
            {searchTerm ? 'No se encontraron productos con ese término de búsqueda.' : 'Comienza creando tu primer producto.'}
          </p>
          {!searchTerm && (
            <div className="mt-6">
              <button
                onClick={() => setShowForm(true)}
                className="btn-primary"
              >
                <Plus className="h-4 w-4 mr-2" />
                Nuevo Producto
              </button>
            </div>
          )}
        </div>
      )}

      {/* Product Form Modal */}
      {showForm && (
        <ProductForm
          product={editingProduct}
          entityId={entityId}
          onClose={handleFormClose}
        />
      )}

      {/* Delete Confirmation */}
      {deletingProduct && (
        <ConfirmDialog
          title="Eliminar Producto"
          message={`¿Estás seguro de que deseas eliminar el producto "${deletingProduct.name}"?`}
          confirmText="Eliminar"
          onConfirm={confirmDelete}
          onCancel={() => setDeletingProduct(null)}
          isLoading={deleteMutation.isPending}
        />
      )}
    </div>
  )
}