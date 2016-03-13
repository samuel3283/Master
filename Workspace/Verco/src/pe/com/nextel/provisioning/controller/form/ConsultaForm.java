package pe.com.nextel.provisioning.controller.form;

import java.util.List;

import org.apache.struts.action.ActionForm;

import pe.com.nextel.provisioning.model.vo.Cliente;
import pe.com.nextel.provisioning.model.vo.Consulta1VO;
import pe.com.nextel.provisioning.model.vo.Consulta2VO;
import pe.com.nextel.provisioning.model.vo.JobReporteVO;
import pe.com.nextel.provisioning.model.vo.PortabilidadVO;
import pe.com.nextel.provisioning.model.vo.PreFacturacionVO;
import pe.com.nextel.provisioning.model.vo.Producto;
import pe.com.nextel.provisioning.model.vo.RechazoVO;
import pe.com.nextel.provisioning.model.vo.SolicitudVO;

public class ConsultaForm  extends ActionForm {

	private String method;
	private String strMensaje;
	private Consulta1VO consulta1VO;
	private Consulta2VO consulta2VO;
	private SolicitudVO solicitudVO;
	private PortabilidadVO portabilidadVO;
	private RechazoVO rechazoVO ;
	private String[] listado;
	private List listaFacturacion;
	private List listaOcc;
	private List listaRechazo;
	private List listaNroPortado;
	private List listaMensajes;
	private List listaAlarmas;
	private List listaEstados;
	private JobReporteVO reporteVO; 
	private PreFacturacionVO prefacturacionVO;
	private Cliente clienteBean;
	private Producto productoBean;
	private Producto producto;
	private String codigo;
	
	 public ConsultaForm() {
		 super();
		 clienteBean	= new Cliente();
		 productoBean	= new Producto();
		 producto		= new Producto();
		 consulta1VO    = new Consulta1VO();
		 consulta2VO    = new Consulta2VO();
		 portabilidadVO = new PortabilidadVO();
		 rechazoVO      = new RechazoVO();
		 reporteVO		=new JobReporteVO();
		 prefacturacionVO=new PreFacturacionVO();
		 solicitudVO = new SolicitudVO();
		 strMensaje = "";
	 }
	 
	 
	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	public Producto getProducto() {
		return producto;
	}


	public void setProducto(Producto producto) {
		this.producto = producto;
	}


	public Producto getProductoBean() {
		return productoBean;
	}


	public void setProductoBean(Producto productoBean) {
		this.productoBean = productoBean;
	}


	public Cliente getClienteBean() {
		return clienteBean;
	}


	public void setClienteBean(Cliente clienteBean) {
		this.clienteBean = clienteBean;
	}


	public SolicitudVO getSolicitudVO() {
		return solicitudVO;
	}

	public void setSolicitudVO(SolicitudVO solicitudVO) {
		this.solicitudVO = solicitudVO;
	}

	public List getListaAlarmas() {
		return listaAlarmas;
	}

	public void setListaAlarmas(List listaAlarmas) {
		this.listaAlarmas = listaAlarmas;
	}

	public PreFacturacionVO getPrefacturacionVO() {
			return prefacturacionVO;
		}
		public void setPrefacturacionVO(PreFacturacionVO prefacturacionVO) {
			this.prefacturacionVO = prefacturacionVO;
		}

	public JobReporteVO getReporteVO() {
		return reporteVO;
	}
	public void setReporteVO(JobReporteVO reporteVO) {
		this.reporteVO = reporteVO;
	}
	public List getListaMensajes() {
		return listaMensajes;
	}
	public void setListaMensajes(List listaMensajes) {
		this.listaMensajes = listaMensajes;
	}
	public List getListaNroPortado() {
		return listaNroPortado;
	}
	public void setListaNroPortado(List listaNroPortado) {
		this.listaNroPortado = listaNroPortado;
	}
	public List getListaRechazo() {
		return listaRechazo;
	}
	public void setListaRechazo(List listaRechazo) {
		this.listaRechazo = listaRechazo;
	}
	public List getListaOcc() {
		return listaOcc;
	}
	public void setListaOcc(List listaOcc) {
		this.listaOcc = listaOcc;
	}
	public List getListaFacturacion() {
		return listaFacturacion;
	}
	public void setListaFacturacion(List listaFacturacion) {
		this.listaFacturacion = listaFacturacion;
	}
	public String[] getListado() {
		return listado;
	}
	public void setListado(String[] listado) {
		this.listado = listado;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getStrMensaje() {
		return strMensaje;
	}
	public void setStrMensaje(String strMensaje) {
		this.strMensaje = strMensaje;
	}
	public Consulta1VO getConsulta1VO() {
		return consulta1VO;
	}
	public void setConsulta1VO(Consulta1VO consulta1VO) {
		this.consulta1VO = consulta1VO;
	}
	
	 public void inicializar()  {
		 consulta1VO    = new Consulta1VO();
		 consulta2VO    = new Consulta2VO();
		 portabilidadVO = new PortabilidadVO();
		 rechazoVO      = new RechazoVO();
		 prefacturacionVO=new PreFacturacionVO();
		 strMensaje = "";
	  }
	

	public Consulta2VO getConsulta2VO() {
		return consulta2VO;
	}
	public void setConsulta2VO(Consulta2VO consulta2VO) {
		this.consulta2VO = consulta2VO;
	}
	public PortabilidadVO getPortabilidadVO() {
		return portabilidadVO;
	}
	public void setPortabilidadVO(PortabilidadVO portabilidadVO) {
		this.portabilidadVO = portabilidadVO;
	}
	public RechazoVO getRechazoVO() {
		return rechazoVO;
	}
	public void setRechazoVO(RechazoVO rechazoVO) {
		this.rechazoVO = rechazoVO;
	}
}
