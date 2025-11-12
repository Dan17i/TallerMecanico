# TallerMecanico
Proyecto final de bases de datos taller mecánico

# 📌 Proyecto Final: Sistema de Gestión de Taller Automotriz

## 📝 Enunciado del Proyecto
El Taller **“MotorPlus”** es una empresa dedicada al mantenimiento y reparación de vehículos particulares y de empresa.  
El sistema de gestión busca organizar la información relacionada con clientes, vehículos, órdenes de trabajo, mecánicos, repuestos, proveedores y facturación.

### Contexto
- Los **clientes** deben registrarse para asociar sus vehículos y el historial de servicios recibidos.  
- Cada **vehículo** se identifica por su placa y posee atributos como marca, modelo, año y tipo de servicio requerido.  
- Al ingresar un vehículo, se genera una **orden de trabajo** con:
  - Fecha de ingreso  
  - Diagnóstico inicial  
  - Servicios a realizar  

### Relaciones principales
- Una orden puede involucrar varios **servicios**, y cada servicio puede aplicarse a distintos vehículos.  
- Los **mecánicos** trabajan en diferentes órdenes, especializados en áreas como mecánica general, electricidad, latonería o pintura.  
  - En una misma orden pueden intervenir varios mecánicos con roles específicos.  
  - Los mecánicos con mayor experiencia pueden **supervisar** a otros, dejando registro de quién supervisa y a quién se supervisa.  
- Los **repuestos** provienen de distintos **proveedores**, tienen costo unitario y stock disponible.  
  - Una orden puede requerir varios repuestos, y un repuesto puede usarse en diferentes órdenes.  
- Al finalizar el trabajo, se genera una **factura** asociada a la orden, con detalle de:
  - Mano de obra  
  - Repuestos utilizados  
  - Impuestos  
  - Valor total a pagar  
  - Estado de pago (pendiente, pagada)
 
    # 📌 Enunciado: Sistema de Gestión de Taller Automotriz

  El Taller “MotorPlus” es una empresa dedicada al mantenimiento y reparación de vehículos particulares y de empresa. Los clientes que llegan al taller deben registrarse para poder asociar la información de sus vehículos y el historial de servicios que reciben.  

  Cada cliente puede llevar uno o varios vehículos, los cuales se identifican por su placa y poseen características propias como marca, modelo, año y tipo de servicio requerido.  
  
  Cuando un vehículo ingresa al taller, se genera una orden de trabajo que contiene la información de la fecha de ingreso, el diagnóstico inicial y los servicios que deberán realizarse. Una orden puede involucrar varios servicios y a su vez cada servicio puede ser realizado en diferentes vehículos.  
  
  Los mecánicos del taller trabajan en diferentes órdenes de trabajo y están especializados en distintas áreas (mecánica general, electricidad, latonería, pintura, etc.). En una misma orden pueden intervenir varios mecánicos, cada uno con un rol específico. Además, los mecánicos con mayor experiencia suelen supervisar a otros mecánicos en el desarrollo de las órdenes de trabajo, dejando registro de quién supervisa y a quién se supervisa. (hay que preguntar)  
  
  Para la realización de los servicios, con frecuencia se utilizan repuestos. Estos provienen de distintos proveedores con los que el taller mantiene relaciones comerciales. Los repuestos tienen un costo unitario y un stock disponible. Una misma orden de trabajo puede requerir varios repuestos, y un repuesto puede utilizarse en diferentes órdenes.  
  
  Al finalizar el trabajo, se genera una factura asociada a la orden, en la cual se detallan los costos de mano de obra, repuestos utilizados, impuestos y el valor total a pagar. La factura queda registrada con su fecha de emisión y estado de pago (pendiente, pagada).  
  
  El taller desea llevar un sistema de gestión que le permita organizar toda esta información para consultar el historial de cada vehículo, el rendimiento de los mecánicos, la trazabilidad de los repuestos, las relaciones con proveedores y la facturación asociada a los clientes.  


### Objetivo del sistema
El sistema permitirá:
- Consultar el historial de cada vehículo  
- Evaluar el rendimiento de los mecánicos  
- Gestionar la trazabilidad de los repuestos  
- Administrar las relaciones con proveedores  
- Controlar la facturación asociada a los clientes  

---

## 📋 Especificaciones del Proyecto Final

### Conformación de grupos
- Cada grupo estará conformado por **3 estudiantes**.  
- En caso de no poder, se permite trabajar en **parejas o individualmente**.  

### Requisitos técnicos
- Desarrollo en lenguaje de preferencia (**web o escritorio**).  
- Uso de bases de datos: **MySQL, SQLServer u Oracle**.  
- Mínimo **10 entidades fuertes** en el modelo entidad-relación.  
- Requerimientos funcionales:  
  - Registro  
  - Modificación  
  - Eliminación  
  - Consulta de información  
- Generación de **reportes completos y estéticos**:  
  - Mínimo 10 reportes:  
    - 3 simples  
    - 4 intermedios  
    - 3 complejos  
  - 3 reportes deben incluir **gráficos estadísticos**.  
- Ingreso validado por **usuario y contraseña**.  
- Manejo de **plantillas** para documentos (factura, permisos, comprobantes de pago).  
- Datos de la base deben ser **ejemplos reales y coherentes**.  

### Forma de entrega
La entrega debe ser una carpeta con el nombre de los integrantes, que contenga:  
- 📂 **info** → Documento con requerimientos funcionales aprobados y modelo entidad-relación normalizado.  
- 📂 **src** → Código fuente de la aplicación.  
- 📂 **SQL** → Archivo `.sql` con la base de datos (estructura y datos).  

### Nota importante
La **sustentación** del proyecto es obligatoria.  
- Factor multiplicador de **1 sobre la nota**.  
- Si un estudiante no sustenta o no se presenta, la nota será **0**.  
