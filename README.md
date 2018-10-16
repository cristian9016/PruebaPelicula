# Prueba Peliculas Android Para Rappi
## Capas de la aplicación
### Persistencia
La capa de persistencia de esta aplicacion se encuentra bajo el paquete "data".
Clases que pertenecen a esta capa:
- PeliculaDao.kt : Provee el acceso e interaccion con la base de datos para la entidad de pelicula.
- SerieDao.kt : Provee el acceso e interaccion con la base de datos para la entidad de serie.
- Pelicula.kt : Provee la estructura de los datos de las peliculas y la entidad de Room
- Serie.kt : Provee la estructura de los datos de las series y la entidad de Room
- AppDatabase.kt

### Inyección de dependencias
Para la inyeccion de dependencias se uso KOIN y se encuentra bajo el paquete "di"
archivos que la componen:
-Module.kt : Permite crear las dependencias que deberan ser inyectadas de la misma manera que los objetos unicos que se usaran en la aplicacion (Singleton).

### Red
En la capa de red se implementan los clientes de retrofit para Peliculas y Series.. Esta capa se encuentra bajo el paquete "net"
Clase e interfaces que pertenecen a esta capa:
- PeliculaClient.kt : Permite establecer los metodos de comunicacion de la aplicación con el servidor para los servicios de peliculas.
- SerieClient.kt : Permite establecer los metodos de comunicacion de la aplicación con el servidor para los servicios de series.
- ResponseData.kt : Facilita el manejo de los objetos recibidos basado en la respuesta del servidor.

### Repositorio o Logica de negocio
La clase que compone esta capa es:
- MainRepository.kt : En esta clase se maneja toda la logica de la aplicacion que se encarga de establecer conexion con las fuentes de datos y proveerlos a la vista.

### Vista
En esta capa se manejan las vistas de la aplicación y se relaciona directamente con la arquitectura usada en el desarrollo (MVVM). Se encuentra bajo el paquete "ui"
las clases usadas para esta capa son:
- /adapter/GeneralAdapter.kt : Provee los diferentes contenedores para mostrar la información en una lista.
- /detail/DetailActivity.kt : Muestra el detalle de la pelicula seleccionada.
- /detail/detailViewModel.kt : No posee logica de negocio asociada.
- /main/MainActivity.kt : Muestra la informacion recibida del servidor y recibe las interacciones del usuario para buscar o ver el detalle de un item.
- /main/mainViewModel.kt : Contiene el acceso al repositorio y por lo tanto enlace directo a logica de negocio asociada con esta vista.
- SearchBarActivity.kt : Contiene la informacion para manejar la barra de busqueda.

### Utilidades
Ademas de las capas mencionadas anteriormente, se posee un paquete de utilidades que permite el acceso a funciones y extensiones de kotlin y reactivas que son usadas en el proyecto.

# Respuestas
#### 1 - En qué consiste el principio de responsabilidad única? Cuál es su propósito?
Rta:/
Este principio consiste en que las clases deben controlar unicamente lo necesario relacionado con ellas. Su proposito es hacer las clases menos susceptibles al cambio y las responsabilidades esten claramente definidas y separadas.
#### 2 - Qué características tiene, según su opinión, un “buen” código o código limpio?
Rta:/
- Capas claramente identificadas
- Autodocumentado, es decir, que los nombres de las clases, metodos y variables revelen su intencion con solo leerlo.
- Pruebas unitarias.

Popayán, Octubre de 2018.