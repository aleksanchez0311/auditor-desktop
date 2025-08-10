import { AlertTriangle } from 'lucide-react'

interface ConfirmDialogProps {
  title: string
  message: string
  confirmText?: string
  cancelText?: string
  onConfirm: () => void
  onCancel: () => void
  isLoading?: boolean
}

export default function ConfirmDialog({
  title,
  message,
  confirmText = 'Confirmar',
  cancelText = 'Cancelar',
  onConfirm,
  onCancel,
  isLoading = false,
}: ConfirmDialogProps) {
  return (
    <div className="fixed inset-0 z-50 overflow-y-auto">
      <div className="flex min-h-screen items-center justify-center p-4">
        <div className="fixed inset-0 bg-gray-500 bg-opacity-75" onClick={onCancel} />
        
        <div className="relative bg-white rounded-lg shadow-xl max-w-md w-full">
          <div className="p-6">
            <div className="flex items-center space-x-3 mb-4">
              <div className="flex-shrink-0">
                <AlertTriangle className="h-6 w-6 text-red-600" />
              </div>
              <h3 className="text-lg font-medium text-gray-900">
                {title}
              </h3>
            </div>
            
            <p className="text-sm text-gray-600 mb-6">
              {message}
            </p>
            
            <div className="flex justify-end space-x-3">
              <button
                type="button"
                onClick={onCancel}
                className="btn-secondary"
                disabled={isLoading}
              >
                {cancelText}
              </button>
              <button
                type="button"
                onClick={onConfirm}
                className="bg-red-600 text-white hover:bg-red-700 btn"
                disabled={isLoading}
              >
                {isLoading ? 'Procesando...' : confirmText}
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}