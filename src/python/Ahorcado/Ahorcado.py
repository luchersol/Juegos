import random

class Ahorcado(object):
    MAX_ATTEMPS:int = 6
    
    def __init__(self) -> None:
        with open("info/Ahorcado/palabras", "r") as archivo:
            self.__words:list[str] = [palabra.strip() for palabra in archivo.read().split(",")]
            archivo.close()
        self.__randomWord:str = self.__words[random.randint(0, len(self.__words) - 1)]
        self.__choisedWords:list[str] = []
        self.__isCompletedWord:bool = False
        self.__attemps:int = self.MAX_ATTEMPS
    
    def chooseLetter(self, letter:str) -> None:
        self.__choisedWords.append(letter.upper[0])
        if not self.__randomWord.__contains__(letter.upper):
            self.__attemps -= 1
        
if __name__ == "__main__":
    game:Ahorcado = Ahorcado()

        