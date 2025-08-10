-- Script de inicialización de la base de datos Auditor Web
-- Crear base de datos si no existe
CREATE DATABASE IF NOT EXISTS auditor_web;

-- Usar la base de datos
\c auditor_web;

-- Crear usuario si no existe
DO
$do$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_catalog.pg_roles
      WHERE  rolname = 'auditor_user') THEN

      CREATE ROLE auditor_user LOGIN PASSWORD 'auditor_pass';
   END IF;
END
$do$;

-- Otorgar permisos
GRANT ALL PRIVILEGES ON DATABASE auditor_web TO auditor_user;
GRANT ALL ON SCHEMA public TO auditor_user;

-- Datos iniciales para unidades de medida
INSERT INTO measure_units (name, abbreviation, description, active, created_at, updated_at) VALUES
('Unidad', 'ud', 'Unidad individual', true, NOW(), NOW()),
('Kilogramo', 'kg', 'Kilogramo', true, NOW(), NOW()),
('Gramo', 'g', 'Gramo', true, NOW(), NOW()),
('Litro', 'l', 'Litro', true, NOW(), NOW()),
('Mililitro', 'ml', 'Mililitro', true, NOW(), NOW()),
('Metro', 'm', 'Metro', true, NOW(), NOW()),
('Centímetro', 'cm', 'Centímetro', true, NOW(), NOW()),
('Caja', 'caja', 'Caja o paquete', true, NOW(), NOW()),
('Docena', 'doc', 'Docena (12 unidades)', true, NOW(), NOW()),
('Paquete', 'paq', 'Paquete', true, NOW(), NOW())
ON CONFLICT (name) DO NOTHING;

-- Entidad de ejemplo
INSERT INTO entities (name, description, code, address, phone, email, active, created_at, updated_at) VALUES
('La Cumbre Restaurant', 'Restaurante principal de La Cumbre', 'LC001', 'Calle Principal 123', '+1234567890', 'info@lacumbre.cu', true, NOW(), NOW())
ON CONFLICT (code) DO NOTHING;

-- Categorías TPV de ejemplo
INSERT INTO tpv_categories (name, description, color, sort_order, entity_id, active, created_at, updated_at) VALUES
('Bebidas', 'Bebidas y refrescos', '#3498db', 1, 1, true, NOW(), NOW()),
('Comidas', 'Platos principales', '#e74c3c', 2, 1, true, NOW(), NOW()),
('Postres', 'Postres y dulces', '#f39c12', 3, 1, true, NOW(), NOW()),
('Aperitivos', 'Entradas y aperitivos', '#2ecc71', 4, 1, true, NOW(), NOW())
ON CONFLICT DO NOTHING;

-- Productos de ejemplo
INSERT INTO products (name, description, code, sale_price, cost_price, stock, min_stock, measure_unit_id, category_id, entity_id, is_buildable, is_sellable, active, created_at, updated_at) VALUES
('Coca Cola', 'Refresco de cola 350ml', 'CC350', 2.50, 1.50, 100.000, 10.000, 1, 1, 1, false, true, true, NOW(), NOW()),
('Hamburguesa Clásica', 'Hamburguesa con carne, lechuga, tomate', 'HB001', 8.50, 5.00, 50.000, 5.000, 1, 2, 1, true, true, true, NOW(), NOW()),
('Helado de Vainilla', 'Helado artesanal de vainilla', 'HV001', 3.75, 2.00, 25.000, 5.000, 1, 3, 1, false, true, true, NOW(), NOW()),
('Nachos con Queso', 'Nachos crujientes con salsa de queso', 'NC001', 4.25, 2.50, 30.000, 5.000, 1, 4, 1, true, true, true, NOW(), NOW())
ON CONFLICT (code) DO NOTHING;