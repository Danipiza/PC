# PC
**Programacion Concurrente,** asignatura del Departamento de Sistemas Informaticos y ́Computación, perteneciente al tercer curso del grado de ingeniería informática en la Universidad Complutense de Madrid.

En esta asignatura vemos diferentes formas de programar de manera concurrente en java, usando hilos. Creamos clases extendiendo a Thread, para customizar y moldear a nuestro gusto, y poder resolver programas de manera mas rápida y eficiente. Usamos cerrojos, semaforos y monitores, para poder controlar el flujo de los hilos asi como distintos algoritmos como pueden ser RompeEmpates, Ticket o Bakery, teniendo en cuenta los problemas que esta programacion conlleva, Seccion critica, exclusión mutua o sincronización condicional.

---


## PR1. Introduccion

- [**EJ1**](https://github.com/Danipiza/PC/blob/main/PC%20PR1/src/EJ1.java)
Crear varios hilos. Cada hilo imprime un mensaje de inicialización, duerme 1s e imprime otro mensaje de finalización.

- [**EJ2**](https://github.com/Danipiza/PC/blob/main/PC%20PR1/src/EJ2.java)
Crear 2*M hilos. La mitad incrementa y la otra mitad decrementa. Comprobar la salida al terminar todos los hilos.

- [**EJ3**](https://github.com/Danipiza/PC/blob/main/PC%20PR1/src/EJ3.java)
Multiplicación de matrices. Crear varios hilos para optimizar el cálculo.

---

## PR2. Cerrojos
Aplicar los algoritmo de RompeEmpates, Ticket y Bakery. Cada uno de ellos tiene un método *takeLock()* y un metodo *releaseLock()*, para garantizar la **exclusion mutua** en el ejemplo de M incrementadores y M decrementadores. 

- [**EJ1**](https://github.com/Danipiza/PC/tree/main/PC%20PR2/src/EJ1)
Incrementadores y Decrementadores, solo para el algoritmo de Rompe Empates.

- [**EJ2**](https://github.com/Danipiza/PC/tree/main/PC%20PR2/src/EJ2)
Generalizar la parte 1 para que funcione con 2M procesos (M incrementandores y M decrementadores), y los tres algoritmos. 

---

## PR3. Semaforos y Productor Consumidor

```public volatile Semaphore empty = new Semaphore(1), full = new Semaphore(0);```

- [**EJ1**](https://github.com/Danipiza/PC/tree/main/PC%20PR3/src/EJ1)
Incrementadores y Decrementadores garantizando la exlusion mutua usando semáforos. *acquire()* y *release()*

- [**EJ2**](https://github.com/Danipiza/PC/tree/main/PC%20PR3/src/EJ2)
Productor-Consumidor usando semáforos. Almacen con 1 producto. Los productores almacenan el producto (*almacena()*) y los consumidores extraen (*extraer()*).
 
- [**EJ3**](https://github.com/Danipiza/PC/tree/main/PC%20PR3/src/EJ3)
Productor-Consumidor usando semáforos. Almacen con muchos productos. Los productores almacenan los producto (*almacena()*) y los consumidores extraen (*extraer()*). Los consumidores cogen ls productos en orden, es decir, no puede extraer un producto sin antes sacar los anteriores.
```
// Variables a compartir:

// Array con los productos
public volatile Producto productos[] = new Producto[k];

// Punteros para saber el inicio y el fin de los productos disponibles.
public volatile Entero ini = new Entero(0), fin = new Entero(0);
```

---

## PR4. Monitores
```
1: Monitor usando synchronized. Se añade a las funciones de la clase Monitor "synchronized"
2: Monitor usando lock. En la clase MonitorLock tenemos un cerrojo
  private final Lock l; // Con el que se coge l.lock(); o suelta l.unlock();
```
- [**EJ1**](https://github.com/Danipiza/PC/tree/main/PC%20PR4/src/EJ1)
Incrementadores y Decrementadores garantizando la exlusion mutua usando monitores. *acquire()* y *release()*

- [**EJ3**](https://github.com/Danipiza/PC/tree/main/PC%20PR4/src/EJ2)
Productor-Consumidor usando los dos Monitores. Almacen con muchos productos en un entero. El productor rellena producto (*producir()*) y los consumidores consumen (*consumir()*), estas funciones enstan en las clases Monitores. 

---

## [PR FINAL.](https://github.com/Danipiza/PC/tree/main/PRFINAL%20PC/src/ENTREGA)
Práctica final Peer-to-peer. hay que implementar todo lo que hemos visto en clase, en un solo proyecto. Hay que usar como minimo una vez un algoritmo para garantizar el orden, los tres métodos de sincronización, y aplicar la solucion a la Sección Critica vista en clase.

Tenemos un Servidor y varios Clientes se conectan al servidor para almacenar y ver peliculas. Un cliente puede almacenar pelicula, ver pelicula o conectarse con otro cliente para ver una pelicula que tenga este cliente. Para ello el cliente puede pedir el catálogo de peliculas del servidor, añadir peliculas nuevas o conectarse con un cliente del servidor, y finalizar conexion con el cliente conectado cuando quiera.

Todo se hace mediante Sockets



