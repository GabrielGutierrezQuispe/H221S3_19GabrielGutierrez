///////////////////////////para solo numeros segun el tipo de documento/////////////////////////////
const documentTypeSelect = document.getElementById('frmDocument_type');
const documentNumberInput = document.getElementById('frmDocument_number');
const errorDocumentNumber = document.getElementById('errorDocument_number');
const inputNumeroDocumentoError = document.getElementById('inputNumeroDocumentoError');

// Eventos
documentNumberInput.addEventListener('input', validateDocumentNumber);
documentNumberInput.addEventListener('keydown', checkInput);

// Validación de número de documento
function validateDocumentNumber() {
	let documentNumberValue = documentNumberInput.value.replace(/\D/g, '');
	const documentTypeValue = documentTypeSelect.value;
	let isValid = false;
	let errorMessage = '';

	// Validar el número de documento según el tipo seleccionado
	if (documentTypeValue === 'DNI') {
		documentNumberValue = documentNumberValue.slice(0, 8); // Limitar a 8 dígitos para DNI
		if (/^\d{8}$/.test(documentNumberValue)) {
			isValid = true;
		} else {
			errorMessage = 'Cantidad no valida. Debe contener 8 n\u00FAmeros.';
		}
	} else if (documentTypeValue === 'CNE') {
		documentNumberValue = documentNumberValue.slice(0, 9); // Limitar a 9 dígitos para CNE
		if (/^\d{9}$/.test(documentNumberValue)) {
			isValid = true;
		} else {
			errorMessage = 'Cantidad no valida. Debe contener 9 numeros.';
		}
	}

	// Validación de solo ingreso de números
	if (!/^\d*$/.test(documentNumberValue)) {
		errorMessage = 'Solo se aceptan numeros.';
	}

	// Estilos y mensajes de error
	if (documentNumberValue === '') {
		errorMessage = 'No se admiten campos vacios.';
	}

	errorDocumentNumber.textContent = errorMessage;
	inputNumeroDocumentoError.textContent = '';
	documentNumberInput.classList.toggle('is-valid', isValid && documentNumberValue !== '');
	documentNumberInput.classList.toggle('is-invalid', !isValid || errorMessage !== '');
	documentNumberInput.value = documentNumberValue;
}
// Función para verificar la entrada del teclado en el campo de número de documento
function checkInput(event) {
	const keyCode = event.keyCode || event.which;
	const key = String.fromCharCode(keyCode);
	const regex = /[0-9]|\./;

	// Validación de tecla correcta
	if (!regex.test(key) && keyCode !== 8 && keyCode !== 46) {
		event.preventDefault();
		showErrorMessage('Solo se aceptan numeros.');
	} else if (keyCode === 8 || keyCode === 46) {
		// Se permite borrar
		hideErrorMessage();
	} else {
		const currentValue = documentNumberInput.value.replace(/\D/g, '');
		const updatedValue = currentValue + key;
		const maxLength = documentTypeSelect.value === 'DNI' ? 8 : 9;

		// Cantidad máxima de números
		if (updatedValue.length > maxLength) {
			event.preventDefault();
		}
		hideErrorMessage();
	}
}

// Función para mostrar el mensaje de error
function showErrorMessage(message) {
	errorDocumentNumber.textContent = message;
	inputNumeroDocumentoError.textContent = '';
	documentNumberInput.classList.remove('is-valid');
	documentNumberInput.classList.add('is-invalid');
}

// Función para ocultar el mensaje de error
function hideErrorMessage() {
	errorDocumentNumber.textContent = '';
	inputNumeroDocumentoError.textContent = '';
	documentNumberInput.classList.remove('is-invalid');
	documentNumberInput.classList.add('is-valid');
}

/////////////////////////Para nombres y apellidos ///////////////////////////////////////

/// Validamos Nombres
// Obtén el campo de entrada de nombres por su ID
var inputNombres = document.getElementById('frmNames');

// Agrega un event listener para el evento 'input'
inputNombres.addEventListener('input', function(event) {
	// Valida el contenido del campo de nombres utilizando una expresión regular
	var regex = /^[a-zA-Z\s]+$/; // Solo letras mayúsculas, minúsculas y espacios

	var nombres = inputNombres.value;

	if (nombres.trim() === '') {
		// Si el campo de nombres está vacío, muestra la alerta de campo vacío
		inputNombres.classList.remove('is-valid');
		inputNombres.classList.add('is-invalid');
		document.getElementById('inputNombresError').textContent = 'No se admiten campos vac\u00EDos.';
	} else if (regex.test(nombres)) {
		// Si los nombres son válidos, elimina cualquier mensaje de error, establece el estilo correcto y muestra el campo en verde con el símbolo de verificación
		inputNombres.classList.remove('is-invalid');
		inputNombres.classList.add('is-valid');
		document.getElementById('inputNombresError').textContent = '';
	} else {
		// Si los nombres no son válidos, muestra el mensaje de error, establece el estilo incorrecto y borra los caracteres no permitidos
		inputNombres.classList.remove('is-valid');
		inputNombres.classList.add('is-invalid');
		document.getElementById('inputNombresError').textContent = 'Solo se admiten letras.';
		inputNombres.value = nombres.replace(/[^a-zA-Z\s]/g, '');
	}
});

// Permitir la tecla Tabulador
inputNombres.addEventListener('keydown', function(event) {
	if (event.code === 'Tab') {
		return;
	}
});
/// Validamos apellido

// Obtén el campo de entrada de apellidos por su ID
var inputApellidos = document.getElementById('frmLastname');

// Agrega un event listener para el evento 'input'
inputApellidos.addEventListener('input', (event) => {
	// Valida el contenido del campo de apellidos utilizando una expresión regular
	var regex = /^[a-zA-Z\s]+$/; // Solo letras mayúsculas, minúsculas y espacios

	var apellidos = inputApellidos.value;

	if (apellidos.trim() === '') {
		// Si el campo de apellidos está vacío, muestra la alerta de campo vacío
		inputApellidos.classList.remove('is-valid');
		inputApellidos.classList.add('is-invalid');
		document.getElementById('inputApellidosError').textContent = 'No se admiten campos vac\u00EDos.';
	} else if (regex.test(apellidos)) {
		// Si los apellidos son válidos, elimina cualquier mensaje de error, establece el estilo correcto y muestra el campo en verde con el símbolo de verificación
		inputApellidos.classList.remove('is-invalid');
		inputApellidos.classList.add('is-valid');
		document.getElementById('inputApellidosError').textContent = '';
	} else {
		// Si los apellidos no son válidos, muestra el mensaje de error, establece el estilo incorrecto y borra los caracteres no permitidos
		inputApellidos.classList.remove('is-valid');
		inputApellidos.classList.add('is-invalid');
		document.getElementById('inputApellidosError').textContent = 'Solo se admiten letras.';
		inputApellidos.value = apellidos.replace(/[^a-zA-Z\s]/g, '');
	}
});

// Permitir la tecla Tabulador
inputApellidos.addEventListener('keydown', function(event) {
	if (event.code === 'Tab') {
		return;
	}
});


// para cacelar y guardar
function showFloatingAlert(message, type) {
	const alertContainer = document.getElementById("alertContainer");

	// Crea un elemento de alerta
	const alertElement = document.createElement("div");
	alertElement.className = "alert";

	// Aplica una clase de estilo según el tipo de alerta
	if (type === "success") {
		alertElement.classList.add("alert-success");
	} else if (type === "error") {
		alertElement.classList.add("alert-error");
	}

	// Establece el mensaje de la alerta
	alertElement.textContent = message;

	// Agrega la alerta al contenedor
	alertContainer.appendChild(alertElement);

	// Elimina la alerta después de 3 segundos
	setTimeout(function() {
		alertElement.remove();
	}, 3000);
}

// Obtén los botones "Cancelar" y "Guardar"
const btnCancel = document.getElementById("btnCancelar");
const btnSave = document.getElementById("btnGuardar");

// Agrega controladores de eventos a los botones
btnCancel.addEventListener("click", function() {
	showFloatingAlert("Cancelado con \u00c9xito", "success");
});

btnSave.addEventListener("click", function() {
	// Verifica si todos los campos están completos antes de mostrar el mensaje de éxito
	const names = document.getElementById("frmNames").value;
	const lastname = document.getElementById("frmLastname").value;
	const documentNumber = document.getElementById("frmDocument_number").value;

	if (names && lastname && documentNumber) {
		showFloatingAlert("Guardado con \u00E9xito", "success");
	} else {
		showFloatingAlert("No se pudo guardar. Todos los campos son obligatorios", "error");
	}
});

// Validacion para correo 
// Obtener el elemento del input de correo electrónico
var emailInput = document.getElementById('frmEmail');
var checkIcon = document.createElement('i'); // Crear elemento i para el icono de verificación

// Agregar un evento de escucha para validar el correo electrónico cuando el usuario cambie el valor del input
emailInput.addEventListener('input', validarCorreo);
emailInput.addEventListener('blur', validarCorreo); // Validar también al perder el foco del input

// Función de validación del correo electrónico
function validarCorreo() {
	// Obtener el valor del correo electrónico ingresado por el usuario
	var correo = emailInput.value.trim(); // Eliminar espacios en blanco al inicio y al final

	// Verificar si el campo está vacío
	if (correo === '') {
		// El campo está vacío
		emailInput.setCustomValidity('No se aceptan campos vac\u00EDos');
		document.getElementById('errorEmail').textContent = 'No se aceptan campos vac\u00EDos';
		quitarIconoCheck();
		return;
	}

	// Expresión regular para validar el formato del correo electrónico
	var expresionRegular = /^[\w.-]+@(gmail|hotmail|outlook|yahoo)\.com$/;

	// Verificar si el correo electrónico cumple con el formato esperado
	if (expresionRegular.test(correo)) {
		// El correo electrónico es válido
		emailInput.setCustomValidity('');
		document.getElementById('errorEmail').textContent = '';
		agregarIconoCheck();
		emailInput.classList.add('is-valid');
		emailInput.classList.remove('is-invalid');
	} else {
		// El correo electrónico es inválido
		emailInput.setCustomValidity('El formato del correo electr\u00F3nico es inv\u00E1lido');
		document.getElementById('errorEmail').textContent = 'El formato del correo electr\u00F3nico es inv\u00E1lido';
		quitarIconoCheck();
		emailInput.classList.add('is-invalid');
		emailInput.classList.remove('is-valid');
	}
}

// Función para agregar el icono de verificación
function agregarIconoCheck() {
	checkIcon.className = 'bi bi-check-circle text-success';
	emailInput.parentNode.appendChild(checkIcon);
}

// Función para quitar el icono de verificación
function quitarIconoCheck() {
	if (checkIcon.parentNode) {
		checkIcon.parentNode.removeChild(checkIcon);
	}
}