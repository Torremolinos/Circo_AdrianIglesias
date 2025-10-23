# 🎪 Proyecto Circo

Aplicación en **Java** para la gestión de un circo: espectáculos, usuarios, artistas, coordinadores y países asociados.  
El objetivo es simular un sistema de administración con diferentes perfiles de usuario y funcionalidades específicas.

---

## 🚀 Características principales

- **Gestión de usuarios y credenciales**
  - Inicio de sesión con validación de credenciales.
  - Perfiles disponibles: `INVITADO`, `ADMIN`, `COORDINACION`, `ARTISTA`.

- **Gestión de espectáculos**
  - Crear nuevos espectáculos con validaciones:
    - Nombre único y máximo de 25 caracteres.
    - Fechas de inicio y fin coherentes (máx. 1 año de duración).
  - Modificar espectáculos existentes:
    - Cambiar nombre, fechas y coordinador (según perfil).
  - Persistencia de datos mediante serialización (`ObjectOutputStream`).

- **Gestión de países**
  - Carga de países desde un archivo XML.
  - Selección de país mediante código.
  - Uso de `Collections.unmodifiableMap` para proteger los datos cargados.

- **Menús dinámicos según perfil**
  - `INVITADO`: ver espectáculos, iniciar sesión.
  - `ADMIN`: crear/modificar espectáculos, registrar usuarios, gestionar credenciales.
  - `COORDINACION`: crear y modificar espectáculos asignados.
  - `ARTISTA`: ver espectáculos y su ficha personal.
