package pe.com.nextel.provisioning.controller.form;

import org.apache.struts.action.ActionForm;

import pe.com.nextel.provisioning.model.vo.BandejaMensajesVO;

public class ReenvioForm  extends ActionForm{

	private BandejaMensajesVO bandejaMensajesVO ;
	
	private String method;
	private String strMensaje;
	private String hidIdBandeja;
	
	public String getHidIdBandeja() {
		return hidIdBandeja;
	}

	public void setHidIdBandeja(String hidIdBandeja) {
		this.hidIdBandeja = hidIdBandeja;
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

	public ReenvioForm(){
		super();
		bandejaMensajesVO =new  BandejaMensajesVO();
	}
	 public void inicializar()  {
		 bandejaMensajesVO = new BandejaMensajesVO();
		  strMensaje = "";
	  }
	 
	 public BandejaMensajesVO getBandejaMensajesVO() {
			return bandejaMensajesVO;
		}

		public void setBandejaMensajesVO(BandejaMensajesVO bandejaMensajesVO) {
			this.bandejaMensajesVO = bandejaMensajesVO;
		} 
}
