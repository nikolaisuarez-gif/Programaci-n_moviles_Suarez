# Prompts Utilizados

## Prompt 1

**Texto enviado a la IA:**
Necesito que trabajes directamente en mi proyecto y agregues imágenes reales a los productos del apartado Populares hoy. Revisa el código del proyecto para identificar dónde se muestran las tarjetas de productos y reemplaza los íconos actuales por las imágenes correspondientes. Descarga las imágenes desde fuentes accesibles y guárdalas dentro de la carpeta de recursos estáticos del proyecto en res/drawable. Conserva el fondo oscuro, las tarjetas, los bordes, los textos blancos, los precios naranjas y el botón de agregar.

**Qué generó la IA:**
La IA identificó la estructura de componentes en Jetpack Compose, creó el directorio res/drawable e incorporó seis fotografías reales en alta resolución para los productos del menú. Además, actualizó el componente ProductoIcono con la función obtenerDrawableProducto para vincular automáticamente cada producto con su imagen mediante painterResource y ContentScale.Crop.

**Qué tuve que corregir yo:**
Se ajustó el tamaño y la proporción del contenedor en ProductoCard a una altura de 110dp con esquinas redondeadas de 8dp para garantizar que la cuadrícula de dos columnas se mostrara alineada, limpia y responsive. También se verificó que la pantalla de detalle adaptara la imagen hero a 200dp de altura de forma consistente.

**¿Respeta MVVM?:**
Sí, respeta plenamente el patrón MVVM. La interfaz únicamente consume los datos del estado sin modificar las reglas de negocio, y los datos siguen fluyendo desde las entidades de Room hacia los ViewModels y Composables sin mezclar responsabilidades.

## Reflexión

El uso de la inteligencia artificial aceleró enormemente la búsqueda, descarga e integración de las fotografías locales en la estructura de Jetpack Compose. El único inconveniente presentado fue un fallo inicial de descarga en uno de los enlaces externos por restricciones del servidor, el cual se solucionó cambiando la fuente por un enlace directo. Este ejercicio me permitió comprender cómo un componente desacoplado como ProductoIcono facilita actualizar la apariencia visual sin alterar los ViewModels ni las entidades de Room. Aprendí que la arquitectura MVVM junto a Room permite escalar el diseño gráfico manteniendo una base de código limpia, organizada y fácil de mantener.
