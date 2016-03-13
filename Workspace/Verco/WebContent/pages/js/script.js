function validacorreo(myString) {
	return myString.match(/\b(^(\S+@).+((\.gob)|(\.com)|(\.net)|(\.edu)|(\.mil)|(\.gov)|(\.org)|(\..{2,2}))$)\b/gi)
}

function validarHora(strHora){
	if (longitudcorrecta(strHora, 8)) {
		strHora += ":00";
	}
	return !(!(/[0-2][0-9]:[0-5][0-9]:[0-5][0-9]/.test(strHora)) || (strHora.substring(0,2)<0 || strHora.substring(0,2)>23));
}

function longitudcorrecta( campo, len ){
  if ( campo != null ) return ( campo.length == len );
  else return false;
}

function doPostMethod(accion, method, index){
   // alert(accion+" "+method+" "+index);
    document.forms[index].method.value=method;    
    document.forms[index].action=accion;
    document.forms[index].submit();
}

function doPost(accion,index){
    document.forms[index].action=accion;
    document.forms[index].submit();
}

function goPage(url){

	document.forms[1].action = url;
	document.forms[1].submit();
}
//wsilvan 160408
function goPageIndex(url,index){
	document.forms[index].action = url;
	document.forms[index].submit();
}
//fin wsilvan
function celdaOn(elemento) {
  if (elemento.className.substr(elemento.className.length - 3) == "Off") {
     elemento.className = elemento.className.substr(0,elemento.className.length - 3) + "On";
  }
}

function celdaOff(elemento) {
  if (elemento.className.substr(elemento.className.length - 2) == "On") {
     elemento.className = elemento.className.substr(0,elemento.className.length - 2) + "Off";
  }
}