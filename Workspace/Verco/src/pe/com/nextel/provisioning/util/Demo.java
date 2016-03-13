package pe.com.nextel.provisioning.util;

import org.hibernate.Session;

import pe.com.nextel.provisioning.model.vo.UsuarioVO;

public class Demo {


	public static void main(String[] args) {
		
		System.out.println("Maven + Hibernate + Oracle");
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		UsuarioVO usuarioVO = new UsuarioVO();
		
		usuarioVO.setCodUsuario(new Integer(5));
		usuarioVO.setUsuario("lfalla");
		usuarioVO.setPassword("lpassword");
		usuarioVO.setNombre("Luis Fallas Vega");
		usuarioVO.setFecha_registro(DateFormat.stringToDate());
		usuarioVO.setUsuario_registro(new Integer(1));
		usuarioVO.setEstado("S");
		
		session.save(usuarioVO);
		
		session.getTransaction().commit();
	}

}
