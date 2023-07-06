from random import shuffle

class RuletaRusa(object):
    
    def __init__(self) -> None:
        self.__TAM_RULETA:int = 6 
        self.__NUM_JUGADORES:int = 2
        self.__ruleta:list[int] = list(range(0, self.__TAM_RULETA))
        self.__huecosBalas:list[int] = []
        self.__jugadores:list[int] = list(range(0, self.__NUM_JUGADORES))
        
    def girar(self) -> None:
        shuffle(self.__ruleta)
        
    def annadirNBalas(self, numBalas:int) -> None:
        copiaRuleta:list[int] = self.__ruleta.copy()
        shuffle(copiaRuleta)
        self.__huecosBalas = copiaRuleta[0: numBalas]
        
    def disparar(self) -> int:
        return self.__ruleta.pop()
    
    def play(self) -> None:
        print("Diga cuantas balas dese annadir:")
        numBalas:int = int(input())
        print(f"Se annadiran {numBalas} balas")
        self.annadirNBalas(numBalas)
        i:int = 0
        while(len(self.__huecosBalas) == 0):
           print(f"\nDisparar jugador {i}") 
           balaDisparada: int = self.disparar()
           if(self.__huecosBalas.__contains__(balaDisparada)):
               print(f"BANG!!!, muere jugador {i}")
               self.__jugadores.remove(i)
               break
           print("Click! Tuvo suerte")
           i = (i+1)%len(self.__jugadores)
        print(f"Gana el jugador {self.__jugadores[0]}")
        
        
if __name__ == "__main__":
    game: RuletaRusa = RuletaRusa()
    game.play()