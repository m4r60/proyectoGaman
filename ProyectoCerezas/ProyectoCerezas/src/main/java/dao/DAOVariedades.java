package dao;

import java.util.List;

import modelos.Variedades;

public interface DAOVariedades {

	public boolean create(Variedades v);
	public Variedades read(String tipo);
	public boolean update(Variedades v);
	public List<Variedades> listar();
	public boolean delete(String tipo);
	public List<Variedades> listar(String busqueda);
	
}
