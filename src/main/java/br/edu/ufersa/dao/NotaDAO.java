package br.edu.ufersa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import br.edu.ufersa.config.BDConfig;
import br.edu.ufersa.entidade.Nota;

public class NotaDAO {

	public List<Nota> listarNotas() throws Exception{
		List<Nota> lista = new ArrayList<>();
		Connection conexao = BDConfig.getConnection();
		String sql = "SELECT * FROM tb_nota";
		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();
		while(rs.next()) {
			Nota nota = new Nota();
			nota.setId(rs.getInt("id_note"));
			nota.setTitulo(rs.getString("titulo"));
			nota.setDescricao(rs.getString("descricao"));
			lista.add(nota);
		}
		return lista;
 	}
	
	public Nota buscarNotaPorId(int idNota) throws Exception{
		Nota nota = null;
		Connection conexao = BDConfig.getConnection();
		String sql = "SELECT * FROM tb_nota WHERE id_note=?";
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, idNota);
		ResultSet rs = statement.executeQuery();
		if(rs.next()) {
			nota = new Nota();
			nota.setId(rs.getInt("id_note"));
			nota.setTitulo(rs.getString("titulo"));
			nota.setDescricao(rs.getString("descricao"));
		}
		return nota; 
	}
	
	public int addNota(Nota nota) throws Exception{
		int idGerado = 0;
		Connection conexao = BDConfig.getConnection();
		String sql = "INSERT INTO tb_nota(titulo,descricao) VALUES (?,?)";
		PreparedStatement statement = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, nota.getTitulo());
		statement.setString(2, nota.getDescricao());
		statement.execute();
		ResultSet rs = statement.getGeneratedKeys();
		if(rs.next()) {
			idGerado = rs.getInt(1);
		}
		return idGerado;
	}
	
	public void editarNota(Nota nota, int idNota) throws Exception{
		Connection conexao = BDConfig.getConnection();
		String sql = "UPDATE tb_nota SET titulo=?, descricao=? WHERE id_note=?";
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, nota.getTitulo());
		statement.setString(2, nota.getDescricao());
		statement.setInt(3, idNota);
		statement.execute();		
	}
	
	public void removeNota(int idNota) throws Exception{
		Connection conexao = BDConfig.getConnection();
		String sql = "DELETE FROM tb_nota WHERE id_note=?";
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, idNota);
		statement.execute();		
	}
	
}
