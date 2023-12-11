let regexp_nombre = /^[a-z áéíóúñ]{2,100}$/i;
let regexp_email = /^([a-z0-9_\.-]+)@([0-9a-z\.-]+)\.([a-z\.]{2,100})$/i;
let regexp_pass = /^[a-z0-9áéíóúñ]{3,10}$/i;
let regexp_alfa_num = /^[a-zA-Z0-9 áéíóúñÑÁÉÍÓÚ]{3,255}$/i;
let regexp_num = /^[0-9]{3,100}$/i;
let regexp_fecha = /^(0[1-9]|1[0-2])\/[0-9]{2}$/;

function validarNombre(nombre){
	if(regexp_nombre.test(nombre)){
		return true;
	}else{
		alert("nombre incorrecto, solo letras")
		return false;
	}
}

function validarEmail(email){
	if(regexp_email.test(email)){
		return true;
	}else{
		alert("email incorrecto, necesario @ . letrao y/o numeros")
		return false;
	}
}

function validarPass(pass){
	if(regexp_pass.test(pass)){
		return true;
	}else{
		alert("contraseña incorrecto, solo letras y numeros y/o espacios")
		return false;
	}
}

function validarAlfaNum(direccion){
	if(regexp_alfa_num.test(direccion)){
		return true;
	}else{
		alert("parámetro incorrecto, solo letras y numeros")
		return false;
	}
}

function validarNum(codigo){
	if(regexp_num.test(codigo)){
		return true;
	}else{
		alert("parámetro incorrecto, solo numeros")
		return false;
	}
}

function validarFecha(expiracion){
	if(regexp_fecha.test(expiracion)){
		return true;
	}else{
		alert("parámetro incorrecto, formato fecha 00/00")
		return false;
	}
}