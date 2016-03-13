package pe.com.nextel.provisioning.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.framework.conexion.JdbcUtilitario;
import pe.com.nextel.provisioning.framework.exception.DAOException;
import pe.com.nextel.provisioning.model.vo.PortadosVO;
import pe.com.nextel.provisioning.model.vo.Producto;

public class ConsultaDAO {

	private static final Log log = LogFactory.getLog(ConsultaDAO.class);    

	public ConsultaDAO(){}

	

	public static Map portadosMesAnterior(Producto producto) throws Exception{

		List rList = new ArrayList () ;
		Map parametros = new HashMap();
		PortadosVO bean = new PortadosVO();
		Connection con = null;
		log.info("DAO portadosMesAnterior:::==>getTipoproducto:"+producto.getTipoproducto()+"==>getNombre:"+producto.getNombre());
		
		String sql = 
		" SELECT CANTIDAD, " +
		//" (select to_char(add_months(to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') ,-1),'Month yyyy') from dual) " +
		" FROM (SELECT count(*) AS CANTIDAD " +
		" FROM NUMERACION_PORTADA " +
		" WHERE ESTADO='01' " +
		" AND INICIO_VENTANA " +
		//" BETWEEN TO_DATE('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') " +
		//" AND TO_DATE('"+fechaF+"','dd/mm/yyyy HH24:mi:ss') " +
		" AND to_char(INICIO_PROCESO,'mmyyyy') = " +
		//" (select to_char(add_months(to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') ,-1),'mmyyyy') from dual))temp" +
		" "; 

		log.info(" sql portadosMesAnterior " + sql ) ;

		try {
			con = JdbcUtilitario.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {

				bean = new PortadosVO();
				parametros.put("cantidadPortados", resultSet.getString(1));
				parametros.put("valorPortados", resultSet.getString(2));
				rList.add(bean) ;

			}
		} catch (Exception e) { 
			throw new DAOException(e.getMessage(), e);
		}
		finally
		{
			try
			{
				con.close();
			}catch(SQLException e)
			{
				e.printStackTrace();
				log.error(e);
			}
		}
		return parametros ;
	}
	
	public static List buscarProductos(Producto producto) throws Exception{

		List rList = new ArrayList () ;
		Producto bean = new Producto();
		Connection con = null;
		log.info("DAO buscarProductos:::==>getTipoproducto:"+producto.getTipoproducto()+"==>getNombre:"+producto.getNombre());
		
		
		String sql = "";
		
		if(producto.getTipoproducto().equals("0")) {
			sql =" select p.codigo,p.nombre,p.descripcion, t.destipo as tipoproducto,p.stock,p.precio,p.ruta " +
					" from tproducto p, ttipo_producto t " +
					" where p.tipoproducto = t.codtipo ";	
			
			if(producto.getNombre()!="")
				sql = sql + " and upper(nombre) like '%"+producto.getNombre().toUpperCase()+"%'" ;
			
			sql = sql + " order by nombre, tipoproducto";
			
		} else	if(!producto.getTipoproducto().equals("0")) {
			sql =" select p.codigo,p.nombre,p.descripcion, t.destipo as tipoproducto,p.stock,p.precio,p.ruta " +
					" from tproducto p, ttipo_producto t " +
					" where p.tipoproducto = t.codtipo and p.tipoproducto='"+producto.getTipoproducto()+" '";	
					
					if(producto.getNombre()!="")
						sql = sql + " and upper(nombre) like '%"+producto.getNombre().toUpperCase()+"%'" ; 
					
			sql = sql + " order by nombre, tipoproducto";		
		}

		log.info(" sql:" + sql ) ;

		try {
			con = JdbcUtilitario.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {

				bean = new Producto();
				bean.setCodigo(resultSet.getInt(1)) ;
				bean.setNombre(resultSet.getString(2)) ;
				bean.setDescripcion(resultSet.getString(3)) ;
				bean.setTipoproducto(resultSet.getString(4)) ;
				bean.setStock(resultSet.getInt(5)) ;
				bean.setPrecio(resultSet.getBigDecimal(6)) ;
				bean.setRuta(resultSet.getString(7)) ;
				
				rList.add(bean) ;

			}
		} catch (Exception e) { 
			throw new DAOException(e.getMessage(), e);
		}
		finally
		{
			try
			{
				con.close();
			}catch(SQLException e)
			{
				e.printStackTrace();
				log.error(e);
			}
		}
		return rList ;
	}

	
	
	public static Producto obtenerProducto(String codigo) throws Exception{

		List rList = new ArrayList () ;
		Producto bean = new Producto();
		Connection con = null;
		log.info("DAO obtenerProducto:::==>codigo:"+codigo);
				
		String sql = "select p.codigo, p.nombre, p.descripcion, t.destipo as tipoproducto, p.stock, p.precio,p.ruta " +
		" from tproducto p, ttipo_producto t " +
		" where p.tipoproducto = t.codtipo and p.codigo="+codigo+" " +
		" order by p.nombre";
		
		log.info(" sql:" + sql ) ;

		try {
			con = JdbcUtilitario.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {

				bean = new Producto();
				bean.setCodigo(resultSet.getInt(1)) ;
				bean.setNombre(resultSet.getString(2)) ;
				bean.setDescripcion(resultSet.getString(3)) ;
				bean.setTipoproducto(resultSet.getString(4)) ;
				bean.setStock(resultSet.getInt(5)) ;
				bean.setPrecio(resultSet.getBigDecimal(6)) ;
				bean.setRuta(resultSet.getString(7)) ;
				
			}
		} catch (Exception e) { 
			throw new DAOException(e.getMessage(), e);
		}
		finally
		{
			try
			{
				con.close();
			}catch(SQLException e)
			{
				e.printStackTrace();
				log.error(e);
			}
		}
		return bean;
	}

	
	
}
