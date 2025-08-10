import OperationDetails from '@/components/OperationDetails'
import OperationForm from '@/components/OperationForm'
import { entitiesApi, Operation, operationsApi } from '@/lib/api'
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query'
import { format } from 'date-fns'
import { es } from 'date-fns/locale'
import { CheckCircle, Clock, Eye, Plus, Search, XCircle } from 'lucide-react'
import { useState } from 'react'
import toast from 'react-hot-toast'

export default function Operations() {
  const [searchTerm, setSearchTerm] = useState('')
  const [typeFilter, setTypeFilter] = useState<string>('ALL')
  const [statusFilter, setStatusFilter] = useState<string>('ALL')
  const [showForm, setShowForm] = useState(false)
  const [editingOperation, setEditingOperation] = useState<Operation | null>(null)
  const [viewingOperation, setViewingOperation] = useState<Operation | null>(null)
  const [currentPage, setCurrentPage] = useState(0)
  const queryClient = useQueryClient()

  // Get first entity (for demo purposes)
  const { data: entities } = useQuery({
    queryKey: ['entities'],
    queryFn: () => entitiesApi.getAll().then(res => res.data),
  })

  const entityId = entities?.[0]?.id || 1

  // Get operations with pagination
  const { data: operationsData, isLoading } = useQuery({
    queryKey: ['operations', entityId, currentPage],
    queryFn: () => operationsApi.getByEntity(entityId, currentPage, 10).then(res => res.data),
    enabled: !!entityId,
  })

  // Complete operation mutation
  const completeMutation = useMutation({
    mutationFn: (id: number) => operationsApi.complete(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['operations'] })
      toast.success('Operación completada exitosamente')
    },
    onError: () => {
      toast.error('Error al completar la operación')
    },
  })

  // Cancel operation mutation
  const cancelMutation = useMutation({
    mutationFn: (id: number) => operationsApi.cancel(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['operations'] })
      toast.success('Operación cancelada exitosamente')
    },
    onError: () => {
      toast.error('Error al cancelar la operación')
    },
  })

  const handleEdit = (operation: Operation) => {
    setEditingOperation(operation)
    setShowForm(true)
  }

  const handleView = (operation: Operation) => {
    setViewingOperation(operation)
  }

  const handleComplete = (operation: Operation) => {
    if (operation.id) {
      completeMutation.mutate(operation.id)
    }
  }

  const handleCancel = (operation: Operation) => {
    if (operation.id) {
      cancelMutation.mutate(operation.id)
    }
  }

  const handleFormClose = () => {
    setShowForm(false)
    setEditingOperation(null)
  }

  const getOperationTypeLabel = (type: string) => {
    switch (type) {
      case 'SALE': return 'Venta'
      case 'PURCHASE': return 'Compra'
      case 'INCOME': return 'Ingreso'
      case 'EXPENSE': return 'Gasto'
      case 'TRANSFER': return 'Transferencia'
      default: return type
    }
  }

  const getStatusLabel = (status: string) => {
    switch (status) {
      case 'PENDING': return 'Pendiente'
      case 'COMPLETED': return 'Completada'
      case 'CANCELLED': return 'Cancelada'
      default: return status
    }
  }

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'PENDING': return 'text-yellow-600 bg-yellow-100'
      case 'COMPLETED': return 'text-green-600 bg-green-100'
      case 'CANCELLED': return 'text-red-600 bg-red-100'
      default: return 'text-gray-600 bg-gray-100'
    }
  }

  const getTypeColor = (type: string) => {
    switch (type) {
      case 'SALE': return 'text-green-600 bg-green-100'
      case 'PURCHASE': return 'text-blue-600 bg-blue-100'
      case 'INCOME': return 'text-emerald-600 bg-emerald-100'
      case 'EXPENSE': return 'text-red-600 bg-red-100'
      case 'TRANSFER': return 'text-purple-600 bg-purple-100'
      default: return 'text-gray-600 bg-gray-100'
    }
  }

  if (isLoading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-primary-600"></div>
      </div>
    )
  }

  const operations = operationsData?.content || []
  const totalPages = operationsData?.totalPages || 0

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex justify-between items-center">
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Operaciones</h1>
          <p className="text-gray-600">
            Gestión de operaciones comerciales - {entities?.[0]?.name || 'Cargando...'}
          </p>
        </div>
        <button
          onClick={() => setShowForm(true)}
          className="btn-primary"
        >
          <Plus className="h-4 w-4 mr-2" />
          Nueva Operación
        </button>
      </div>

      {/* Filters */}
      <div className="flex flex-col sm:flex-row gap-4">
        <div className="relative flex-1">
          <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 h-4 w-4" />
          <input
            type="text"
            placeholder="Buscar operaciones..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="input pl-10"
          />
        </div>
        
        <select
          value={typeFilter}
          onChange={(e) => setTypeFilter(e.target.value)}
          className="input w-auto"
        >
          <option value="ALL">Todos los tipos</option>
          <option value="SALE">Ventas</option>
          <option value="PURCHASE">Compras</option>
          <option value="INCOME">Ingresos</option>
          <option value="EXPENSE">Gastos</option>
          <option value="TRANSFER">Transferencias</option>
        </select>

        <select
          value={statusFilter}
          onChange={(e) => setStatusFilter(e.target.value)}
          className="input w-auto"
        >
          <option value="ALL">Todos los estados</option>
          <option value="PENDING">Pendientes</option>
          <option value="COMPLETED">Completadas</option>
          <option value="CANCELLED">Canceladas</option>
        </select>
      </div>

      {/* Operations Table */}
      {operations.length > 0 ? (
        <div className="card">
          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Fecha
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Tipo
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Documento
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Total
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Estado
                  </th>
                  <th className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Acciones
                  </th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {operations.map((operation) => (
                  <tr key={operation.id} className="hover:bg-gray-50">
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                      {format(new Date(operation.operationDate), 'dd/MM/yyyy HH:mm', { locale: es })}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${getTypeColor(operation.operationType)}`}>
                        {getOperationTypeLabel(operation.operationType)}
                      </span>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                      {operation.documentNumber || '-'}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                      ${operation.total.toLocaleString()}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${getStatusColor(operation.status)}`}>
                        {getStatusLabel(operation.status)}
                      </span>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                      <div className="flex justify-end space-x-2">
                        <button
                          onClick={() => handleView(operation)}
                          className="text-gray-400 hover:text-primary-600"
                          title="Ver detalles"
                        >
                          <Eye className="h-4 w-4" />
                        </button>
                        
                        {operation.status === 'PENDING' && (
                          <>
                            <button
                              onClick={() => handleComplete(operation)}
                              className="text-gray-400 hover:text-green-600"
                              title="Completar"
                              disabled={completeMutation.isPending}
                            >
                              <CheckCircle className="h-4 w-4" />
                            </button>
                            <button
                              onClick={() => handleCancel(operation)}
                              className="text-gray-400 hover:text-red-600"
                              title="Cancelar"
                              disabled={cancelMutation.isPending}
                            >
                              <XCircle className="h-4 w-4" />
                            </button>
                          </>
                        )}
                        
                        {operation.status === 'PENDING' && (
                          <button
                            onClick={() => handleEdit(operation)}
                            className="text-gray-400 hover:text-primary-600"
                            title="Editar"
                          >
                            <Clock className="h-4 w-4" />
                          </button>
                        )}
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          {/* Pagination */}
          {totalPages > 1 && (
            <div className="px-6 py-3 border-t border-gray-200 flex items-center justify-between">
              <div className="text-sm text-gray-700">
                Página {currentPage + 1} de {totalPages}
              </div>
              <div className="flex space-x-2">
                <button
                  onClick={() => setCurrentPage(Math.max(0, currentPage - 1))}
                  disabled={currentPage === 0}
                  className="btn-secondary btn-sm"
                >
                  Anterior
                </button>
                <button
                  onClick={() => setCurrentPage(Math.min(totalPages - 1, currentPage + 1))}
                  disabled={currentPage === totalPages - 1}
                  className="btn-secondary btn-sm"
                >
                  Siguiente
                </button>
              </div>
            </div>
          )}
        </div>
      ) : (
        <div className="text-center py-12">
          <Clock className="mx-auto h-12 w-12 text-gray-400" />
          <h3 className="mt-2 text-sm font-medium text-gray-900">
            No hay operaciones
          </h3>
          <p className="mt-1 text-sm text-gray-500">
            Comienza creando tu primera operación comercial.
          </p>
          <div className="mt-6">
            <button
              onClick={() => setShowForm(true)}
              className="btn-primary"
            >
              <Plus className="h-4 w-4 mr-2" />
              Nueva Operación
            </button>
          </div>
        </div>
      )}

      {/* Operation Form Modal */}
      {showForm && (
        <OperationForm
          operation={editingOperation}
          entityId={entityId}
          onClose={handleFormClose}
        />
      )}

      {/* Operation Details Modal */}
      {viewingOperation && (
        <OperationDetails
          operation={viewingOperation}
          onClose={() => setViewingOperation(null)}
        />
      )}
    </div>
  )
}