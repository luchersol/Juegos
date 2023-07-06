package MiniJuegoRPG;

public enum Tipo {
	FUEGO, AGUA, PLANTA, LUZ, OSCURIDAD, NEUTRAL;
	
	public static Tipo debilidad(Tipo tipo) {
		Tipo debilidad = null;
		if(!tipo.equals(NEUTRAL)) {
			if(tipo.equals(FUEGO)) 
				debilidad = AGUA;
			else if(tipo.equals(AGUA))
				debilidad = PLANTA;
			else if(tipo.equals(PLANTA))
				debilidad = FUEGO;
			else if(tipo.equals(LUZ))
				debilidad = OSCURIDAD;
			else
				debilidad = LUZ;
		}
		return debilidad;
			
	}
}
