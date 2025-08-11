# La Cumbre Auditor Web - Sistema de Gestión Empresarial

## 🚀 Descripción

**La Cumbre Auditor Web** es la versión moderna y web del sistema de gestión empresarial La Cumbre. Esta aplicación full-stack proporciona una solución completa para la gestión de operaciones comerciales, inventario, productos y reportes financieros.

### ✨ Características Principales

- **Dashboard Interactivo**: Resumen ejecutivo con métricas clave y gráficos
- **Gestión de Productos**: CRUD completo con control de inventario y stock
- **Operaciones Comerciales**: Ventas, compras, ingresos, gastos y transferencias
- **Gestión de Entidades**: Administración de empresas y entidades comerciales
- **Reportes Avanzados**: Análisis financiero con gráficos y exportación
- **Interfaz Moderna**: UI/UX responsive con Tailwind CSS
- **API REST**: Backend robusto con Spring Boot
- **Base de Datos**: PostgreSQL para almacenamiento confiable

## 🏗️ Arquitectura

### Backend (Spring Boot)
- **Framework**: Spring Boot 3.2.0
- **Base de Datos**: PostgreSQL
- **Seguridad**: Spring Security (configuración básica)
- **API**: REST con OpenAPI/Swagger
- **Validación**: Bean Validation
- **Mapeo**: MapStruct para DTOs

### Frontend (React + TypeScript)
- **Framework**: React 18 con TypeScript
- **Build Tool**: Vite
- **Styling**: Tailwind CSS
- **Estado**: TanStack Query (React Query)
- **Routing**: React Router DOM
- **Forms**: React Hook Form con Zod
- **Charts**: Recharts
- **Icons**: Lucide React

## 📋 Requisitos Previos

- **Java 17** o superior
- **Node.js 18** o superior
- **PostgreSQL 12** o superior
- **Maven 3.8** o superior

## 🛠️ Instalación y Configuración

### 1. Configurar la Base de Datos

```sql
-- Crear base de datos
CREATE DATABASE auditor_web;

-- Crear usuario
CREATE USER auditor_user WITH PASSWORD 'auditor_pass';

-- Otorgar permisos
GRANT ALL PRIVILEGES ON DATABASE auditor_web TO auditor_user;
```

### 2. Configurar el Backend

```bash
# Navegar al directorio del backend
cd auditor-web

# Configurar variables de entorno (opcional)
export DB_USERNAME=auditor_user
export DB_PASSWORD=auditor_pass
export JWT_SECRET=mySecretKey123456789012345678901234567890

# Compilar y ejecutar
mvn clean install
mvn spring-boot:run
```

El backend estará disponible en: `http://localhost:8080`
- API: `http://localhost:8080/api`
- Swagger UI: `http://localhost:8080/swagger-ui.html`

### 3. Configurar el Frontend

```bash
# Navegar al directorio del frontend
cd auditor-web-frontend

# Instalar dependencias
npm install

# Ejecutar en modo desarrollo
npm run dev
```

El frontend estará disponible en: `http://localhost:3000`

## 📁 Estructura del Proyecto

```
auditor-web/
├── backend/
│   ├── src/main/java/cu/lacumbre/auditor/
│   │   ├── config/          # Configuraciones
│   │   ├── controller/      # Controladores REST
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── exception/      # Manejo de excepciones
│   │   ├── mapper/         # Mappers (MapStruct)
│   │   ├── model/          # Entidades JPA
│   │   ├── repository/     # Repositorios JPA
│   │   └── service/        # Servicios de negocio
│   └── src/main/resources/
│       ├── application.yml # Configuración
│       └── db/init.sql    # Script de inicialización
└── frontend/
    ├── src/
    │   ├── components/     # Componentes reutilizables
    │   ├── lib/           # Utilidades y API client
    │   ├── pages/         # Páginas principales
    │   └── main.tsx       # Punto de entrada
    ├── package.json
    └── vite.config.ts
```

## 🔧 Configuración Avanzada

### Variables de Entorno Backend

```yaml
# application.yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/auditor_web
    username: ${DB_USERNAME:auditor_user}
    password: ${DB_PASSWORD:auditor_pass}
  
  security:
    jwt:
      secret: ${JWT_SECRET:mySecretKey123456789012345678901234567890}
      expiration: 86400000
```

### Variables de Entorno Frontend

```bash
# .env (opcional)
VITE_API_URL=http://localhost:8080/api
```

## 🚀 Despliegue

### Backend (JAR)

```bash
# Compilar para producción
mvn clean package -DskipTests

# Ejecutar JAR
java -jar target/auditor-web-2.0.0-SNAPSHOT.jar
```

### Frontend (Build)

```bash
# Compilar para producción
npm run build

# Los archivos estáticos estarán en dist/
```

## 📊 Funcionalidades Principales

### 1. Dashboard
- Métricas de ventas, compras, ingresos y gastos
- Operaciones recientes
- Productos con stock bajo
- Gráficos interactivos

### 2. Gestión de Productos
- CRUD completo de productos
- Control de inventario y stock
- Categorización y unidades de medida
- Alertas de stock bajo

### 3. Operaciones Comerciales
- Registro de ventas, compras, ingresos y gastos
- Gestión de detalles por operación
- Estados: Pendiente, Completada, Cancelada
- Actualización automática de inventario

### 4. Reportes
- Análisis financiero por períodos
- Gráficos de barras y circulares
- Cálculo de ganancias netas
- Exportación de datos

## 🔌 API Endpoints

### Entidades
- `GET /api/entities` - Listar entidades
- `POST /api/entities` - Crear entidad
- `PUT /api/entities/{id}` - Actualizar entidad
- `DELETE /api/entities/{id}` - Eliminar entidad

### Productos
- `GET /api/products/entity/{entityId}` - Listar productos
- `POST /api/products` - Crear producto
- `PUT /api/products/{id}` - Actualizar producto
- `PUT /api/products/{id}/stock` - Actualizar stock

### Operaciones
- `GET /api/operations/entity/{entityId}` - Listar operaciones
- `POST /api/operations` - Crear operación
- `PUT /api/operations/{id}/complete` - Completar operación
- `GET /api/operations/entity/{entityId}/total/{type}` - Calcular totales

## 🧪 Testing

```bash
# Backend - Ejecutar tests
mvn test

# Frontend - Ejecutar tests
npm test
```

## 🤝 Contribución

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

## 📝 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 🆘 Soporte

Para soporte técnico o preguntas:
- Email: soporte@lacumbre.cu
- Issues: GitHub Issues del proyecto

## 🔄 Migración desde la Versión Desktop

La nueva versión web mantiene compatibilidad conceptual con la versión desktop original, pero con las siguientes mejoras:

- **Arquitectura moderna**: Separación clara entre frontend y backend
- **Escalabilidad**: Soporte para múltiples usuarios concurrentes
- **Accesibilidad**: Acceso desde cualquier dispositivo con navegador
- **Mantenimiento**: Código más limpio y mantenible
- **Integración**: APIs REST para integraciones futuras

---

**La Cumbre Auditor Web** - Sistema de Gestión Empresarial Moderno 🚀