# Juegos
En este proyecto se encuentran diferentes pequeños paquetes de código donde se implementas juegos sencillos en java que se muestran por consola.

## Ajedrez
Las piezas son representadas por:
- R: Torre
- N: Caballo
- B: Alfin
- Q: Reina
- K: Rey
- P: Peón
Las piezas blancas son representadas por mayúsculas y las negras con minúsculas.
Los movimientos se realizan introduciendo a través del formato estilo "a2 a4", la letra indica la coordenada columna y el número la coordenada fila.
El código de los movimientos que se pueden hace con cada pieza es el básico, por lo que no existen movimientos especiales como el enroque.
Por el momento no se puede detectar cuando se está en posición de jaque mate ni cuando la partida está terminada, esto será solo observable por las propias personas que estén jugando. Tampoco existe una forma de volver a un estado anterior del juego, por lo tanto debe de haber cuidado

## Cartas
Juegos de cartas, disponible usar baraja española e inglesa.
Existe únicamente el BlackJack 21 por el momento y no está implementada la función de apostar.

## Mini Juego RPG
Evento de combate típico de juegos RPG.
Está incompleto y no es funcional por el momento.

## Ruleta Rusa
Juego simple de ruleta rusa.
El número de jugadores y tamaño de la ruleta puede ser customizable a través de las variables TAM_RULETA y NUM_JUGADORES, inicialmente se pedirá insertar un número de balas en la ruleta, el juego terminará cuando se terminen todas las balas.

## Tres En Raya
Existen dos juegos de tres en raya a los que se pueden jugar en esta carpeta.
### Tipo 1
Se accede a través de la clase "Tablero".
Un tres en raya sencillo en el que eliges con que tipo de traza empezar.
La posición a introducir será un número del 0 al 8 que representará el hueco seleccionado en el tablero .
### Tipo 2
Se accede a través de la clase "TresEnRayaCustomizado".
Una versión del juego en el que se puede modificar el tamaño de la cuadricula a través de la variable BOARD_SIZE y también el número de trazas en línea que se deben encontrar para ganar a través de la variable TRACES_TO_WIN.
La posición a introducir tendrá un formato estilo "1 2", "1,2" o "1:2", cuyo primer número indica la columna a elegir y el segundo número indica la fila.