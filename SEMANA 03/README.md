 SEMANA 03: Registro de Producto

Estudiante:**Nikolai Suarez
Curso: Programación de Móviles

 Descripción
Esta aplicación permite registrar un producto ingresando su nombre, precio y cantidad. Al presionar el botón "AGREGAR PRODUCTO", se muestra un resumen con el importe total calculado y un mensaje de confirmación.

 Capturas de Pantalla
(Nota: Por favor, adjunte aquí las capturas del emulador)
Pantalla vacía: ![Pantalla Vacía](vacia.png)
registrado: ![Producto Registrado](registrado.png)

 Pregunta sobre `remember`
¿Qué pasaría si declaras las variables de los campos SIN `remember`?
Si las variables se declaran sin `remember`, el estado no se preservaría entre recomposiciones. Cada vez que el usuario escriba un carácter, el Composable se volvería a ejecutar y las variables se reiniciarían a su valor inicial (cadena vacía). Como resultado, el texto nunca aparecería en los campos y el usuario no podría ingresar información.
