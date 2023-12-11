package springboot.articulos.serviciosweb;

import java.util.*;

public class InfoArticulos {

	private List<Map<String, Object>> articulos;
	private int totalArticulos;
	
	public List<Map<String, Object>> getArticulos() {
		return articulos;
	}
	public void setArticulos(List<Map<String, Object>> articulos) {
		this.articulos = articulos;
	}
	public int getTotalArticulos() {
		return totalArticulos;
	}
	public void setTotalArticulos(int totalArticulos) {
		this.totalArticulos = totalArticulos;
	}
	
	
}
