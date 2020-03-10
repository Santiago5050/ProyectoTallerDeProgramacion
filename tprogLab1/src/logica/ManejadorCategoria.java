package logica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import excepciones.CategoriaNoExisteException;
import excepciones.CategoriaYaExisteException;

public class ManejadorCategoria {
	
	private Map<String, Categoria> categoriasMap;
	private static ManejadorCategoria instance= null;
	
	public static ManejadorCategoria getInstance() {
		if (instance==null)
			instance = new ManejadorCategoria();
		return instance;
	}
	
	private ManejadorCategoria() {
		categoriasMap = new HashMap<String, Categoria>();
	}
	
	
	public Categoria getCategoria(String nom) throws CategoriaNoExisteException {
		if (categoriasMap.containsKey(nom))
			return categoriasMap.get(nom);
		else {
			throw new CategoriaNoExisteException("La categoria no existe");
		}
	}
	
	public void agregarCategoria(String nom) throws CategoriaYaExisteException {
		if (categoriasMap.containsKey(nom))
			throw new CategoriaYaExisteException("La categoria " + nom +" ya existe.");
		else 
			categoriasMap.put(nom, new Categoria(nom));
	}
	
	public List<String> getListaCategorias() {
		ArrayList<String> categorias = new ArrayList<String>();
		this.categoriasMap.forEach((k, v)->{
			categorias.add(v.getNombre());
		});
		return categorias;
	}
	

}
