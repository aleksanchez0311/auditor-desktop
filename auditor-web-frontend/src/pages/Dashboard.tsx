import { entitiesApi, operationsApi, productsApi } from '@/lib/api'
import { useQuery } from '@tanstack/react-query'
import { format } from 'date-fns'
import { es } from 'date-fns/locale'
import {
    AlertTriangle,
    DollarSign,
    Package,
    ShoppingCart,
    TrendingDown,
    TrendingUp
} from 'lucide-react'

export default function Dashboard() {
  // Get first entity (for demo purposes)
  const { data: entities } = useQuery({
    queryKey: ['entities'],
    queryFn: () => entitiesApi.getAll().then(res => res.data),
  })

  const entityId = entities?.[0]?.id || 1

  // Get recent operations
  const { data: recentOperations } = useQuery({
    queryKey: ['operations', 'recent', entityId],
    queryFn: () => operationsApi.getRecent(entityId, 5).then(res => res.data),
    enabled: !!entityId,
  })

  // Get low stock products
  const { data: lowStockProducts } = useQuery({
    queryKey: ['products', 'low-stock', entityId],
    queryFn: () => productsApi.getLowStock(entityId).then(res => res.data),
    enabled: !!entityId,
  })

  // Get totals for current month
  const startOfMonth = format(new Date(new Date().getFullYear(), new Date().getMonth(), 1), 'yyyy-MM-dd\'T\'HH:mm:ss')
  const endOfMonth = format(new Date(), 'yyyy-MM-dd\'T\'HH:mm:ss')

  const { data: salesTotal } = useQuery({
    queryKey: ['operations', 'total', 'SALE', entityId, startOfMonth, endOfMonth],
    queryFn: () => operationsApi.calculateTotal(entityId, 'SALE', startOfMonth, endOfMonth).then(res => res.data),
    enabled: !!entityId,
  })

  const { data: purchasesTotal } = useQuery({
    queryKey: ['operations', 'total', 'PURCHASE', entityId, startOfMonth, endOfMonth],
    queryFn: () => operationsApi.calculateTotal(entityId, 'PURCHASE', startOfMonth, endOfMonth).then(res => res.data),
    enabled: !!entityId,
  })

  const { data: expensesTotal } = useQuery({
    queryKey: ['operations', 'total', 'EXPENSE', entityId, startOfMonth, endOfMonth],
    queryFn: () => operationsApi.calculateTotal(entityId, 'EXPENSE', startOfMonth, endOfMonth).then(res => res.data),
    enabled: !!entityId,
  })

  const { data: productsCount } = useQuery({
    queryKey: ['products', 'count', entityId],
    queryFn: () => productsApi.count(entityId).then(res => res.data),
    enabled: !!entityId,
  })

  const stats = [
    {
      name: 'Ventas del Mes',
      value: `$${(salesTotal || 0).toLocaleString()}`,
      change: '+12%',
      changeType: 'increase',
      icon: TrendingUp,
    },
    {
      name: 'Compras del Mes',
      value: `$${(purchasesTotal || 0).toLocaleString()}`,
      change: '+8%',
      changeType: 'increase',
      icon: ShoppingCart,
    },
    {
      name: 'Gastos del Mes',
      value: `$${(expensesTotal || 0).toLocaleString()}`,
      change: '-3%',
      changeType: 'decrease',
      icon: TrendingDown,
    },
    {
      name: 'Total Productos',
      value: (productsCount || 0).toString(),
      change: '+2',
      changeType: 'increase',
      icon: Package,
    },
  ]

  return (
    <div className="space-y-6">
      {/* Header */}
      <div>
        <h1 className="text-2xl font-bold text-gray-900">Dashboard</h1>
        <p className="text-gray-600">
          Resumen general del sistema - {entities?.[0]?.name || 'Cargando...'}
        </p>
      </div>

      {/* Stats */}
      <div className="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4">
        {stats.map((stat) => (
          <div key={stat.name} className="card">
            <div className="card-content p-5">
              <div className="flex items-center">
                <div className="flex-shrink-0">
                  <stat.icon className="h-6 w-6 text-gray-400" />
                </div>
                <div className="ml-5 w-0 flex-1">
                  <dl>
                    <dt className="text-sm font-medium text-gray-500 truncate">
                      {stat.name}
                    </dt>
                    <dd className="flex items-baseline">
                      <div className="text-2xl font-semibold text-gray-900">
                        {stat.value}
                      </div>
                      <div className={`ml-2 flex items-baseline text-sm font-semibold ${
                        stat.changeType === 'increase' ? 'text-green-600' : 'text-red-600'
                      }`}>
                        {stat.change}
                      </div>
                    </dd>
                  </dl>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* Recent Operations */}
        <div className="card">
          <div className="card-header">
            <h3 className="card-title">Operaciones Recientes</h3>
            <p className="card-description">
              Últimas 5 operaciones registradas
            </p>
          </div>
          <div className="card-content">
            {recentOperations && recentOperations.length > 0 ? (
              <div className="space-y-3">
                {recentOperations.map((operation) => (
                  <div key={operation.id} className="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
                    <div className="flex items-center space-x-3">
                      <div className={`p-2 rounded-full ${
                        operation.operationType === 'SALE' ? 'bg-green-100 text-green-600' :
                        operation.operationType === 'PURCHASE' ? 'bg-blue-100 text-blue-600' :
                        operation.operationType === 'EXPENSE' ? 'bg-red-100 text-red-600' :
                        'bg-gray-100 text-gray-600'
                      }`}>
                        {operation.operationType === 'SALE' ? <TrendingUp className="h-4 w-4" /> :
                         operation.operationType === 'PURCHASE' ? <ShoppingCart className="h-4 w-4" /> :
                         <DollarSign className="h-4 w-4" />}
                      </div>
                      <div>
                        <p className="text-sm font-medium text-gray-900">
                          {operation.operationType === 'SALE' ? 'Venta' :
                           operation.operationType === 'PURCHASE' ? 'Compra' :
                           operation.operationType === 'EXPENSE' ? 'Gasto' :
                           operation.operationType === 'INCOME' ? 'Ingreso' : 'Transferencia'}
                        </p>
                        <p className="text-xs text-gray-500">
                          {format(new Date(operation.operationDate), 'dd MMM yyyy HH:mm', { locale: es })}
                        </p>
                      </div>
                    </div>
                    <div className="text-right">
                      <p className="text-sm font-semibold text-gray-900">
                        ${operation.total.toLocaleString()}
                      </p>
                      <p className={`text-xs ${
                        operation.status === 'COMPLETED' ? 'text-green-600' :
                        operation.status === 'PENDING' ? 'text-yellow-600' :
                        'text-red-600'
                      }`}>
                        {operation.status === 'COMPLETED' ? 'Completada' :
                         operation.status === 'PENDING' ? 'Pendiente' : 'Cancelada'}
                      </p>
                    </div>
                  </div>
                ))}
              </div>
            ) : (
              <p className="text-gray-500 text-center py-4">
                No hay operaciones recientes
              </p>
            )}
          </div>
        </div>

        {/* Low Stock Products */}
        <div className="card">
          <div className="card-header">
            <h3 className="card-title">Productos con Stock Bajo</h3>
            <p className="card-description">
              Productos que requieren reposición
            </p>
          </div>
          <div className="card-content">
            {lowStockProducts && lowStockProducts.length > 0 ? (
              <div className="space-y-3">
                {lowStockProducts.slice(0, 5).map((product) => (
                  <div key={product.id} className="flex items-center justify-between p-3 bg-red-50 rounded-lg border border-red-200">
                    <div className="flex items-center space-x-3">
                      <AlertTriangle className="h-5 w-5 text-red-500" />
                      <div>
                        <p className="text-sm font-medium text-gray-900">
                          {product.name}
                        </p>
                        <p className="text-xs text-gray-500">
                          Código: {product.code || 'N/A'}
                        </p>
                      </div>
                    </div>
                    <div className="text-right">
                      <p className="text-sm font-semibold text-red-600">
                        {product.stock} {product.measureUnitAbbreviation || 'ud'}
                      </p>
                      <p className="text-xs text-gray-500">
                        Mín: {product.minStock}
                      </p>
                    </div>
                  </div>
                ))}
              </div>
            ) : (
              <p className="text-gray-500 text-center py-4">
                No hay productos con stock bajo
              </p>
            )}
          </div>
        </div>
      </div>
    </div>
  )
}