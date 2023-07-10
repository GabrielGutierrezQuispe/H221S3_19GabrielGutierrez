// Constantes del CRUD
const ACCION_NUEVO = "NUEVO";
const ACCION_EDITAR = "EDITAR";
let soloActivos = 'A';

// Acceder a control de boton buscar
let btnBuscarEstudiante = document.getElementById('btnBuscar');
btnBuscarEstudiante.addEventListener('click', fnBuscarEstudiante);


let timeoutId;
let searchValue = document.getElementById('inputBuscar');
let searchCriteria = document.getElementById('searchField');

searchValue.addEventListener('input', function() {
	clearTimeout(timeoutId);
});

searchValue.addEventListener('keyup', function() {
	clearTimeout(timeoutId);

	timeoutId = setTimeout(function() {
		console.log('Han pasado 0.5 segundos');
		fnBuscarEstudiante();
	}, 500);
});

searchValue.addEventListener('search', fnBuscarEstudiante);

searchCriteria.addEventListener('change', fnBuscarEstudiante);

// Acceder al control de botón de activar y desactivar
let btnInactivoEstudiante = document.getElementById('chkInactivos');
btnInactivoEstudiante.addEventListener('change', function() {
	if (btnInactivoEstudiante.checked) {
		soloActivos = 'A';
	} else {
		soloActivos = 'I';
	}
	fnBuscarEstudiante();
});

let btnNuevo = document.getElementById('btnNuevo');
btnNuevo.addEventListener('click', fnCrearEstudiante);

let listaEstudiantes = [];

// Funcion para crear Estudiante
function fnCrearEstudiante() {
	let estudianteModal = document.querySelector('#estudianteModal');
	let modal = bootstrap.Modal.getOrCreateInstance(estudianteModal);
	modal.show();

	document.getElementById("accion").value = ACCION_NUEVO;
	document.getElementById('estudianteModalLabel').innerHTML = 'Crear nuevo estudiante';
}

// Acceder a control de boton guardar
let btnGuardar = document.getElementById('btnGuardar');
btnGuardar.addEventListener('click', fnProcesarEstudiante);

// Funcion para limpiar el formulario
function limpiarFormulario() {
	document.getElementById("frmStudent_id").value = "";
	document.getElementById("frmNames").value = "";
	document.getElementById("frmLastname").value = "";
	document.getElementById("frmEmail").value = "";
	document.getElementById("frmDocument_type").value = "";
	document.getElementById("frmDocument_number").value = "";
	document.getElementById("frmSemester").value = "";
	document.getElementById("frmCareer").value = "";

}

// Funcion para crear o editar un estudiante.
function fnProcesarEstudiante() {
	console.log('Guardando.......');
	// Obtener los datos del formulario de estudiante
	let datos = "accion=" + document.getElementById("accion").value;
	datos += "&student_id=" + document.getElementById("frmStudent_id").value;
	datos += "&names=" + document.getElementById("frmNames").value;
	datos += "&lastname=" + document.getElementById("frmLastname").value;
	datos += "&email=" + document.getElementById("frmEmail").value;
	datos += "&document_type=" + document.getElementById("frmDocument_type").value;
	datos += "&document_number=" + document.getElementById("frmDocument_number").value;
	datos += "&semester=" + document.getElementById("frmSemester").value;
	datos += "&career=" + document.getElementById("frmCareer").value;

	let xhr = new XMLHttpRequest();
	xhr.open('POST', 'StudentProcesar', true);
	xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			// La solicitud se completó correctamente
			console.log(xhr.responseText);

			let estudianteModal = document.getElementById('estudianteModal');
			let modal = bootstrap.Modal.getInstance(estudianteModal); // Returns a Bootstrap modal instance

			modal.hide();
			fnBuscarEstudiante();
			limpiarFormulario(); // Limpiar el formulario
		
		// Mostrar la alerta flotante al eliminar con éxito
			showFloatingAlert('Guardado con exito');
		}
	};
	xhr.send(datos);

	console.log('Datos: ', datos);
}

// Funcion para buscar estudiantes
function fnBuscarEstudiante() {
	console.log('Buscando.......');

	// Preparando la URL
	//	let url = 'StudentBuscar?search_criteria=' + searchCriteria + '&search_value=' + searchValue;
	let url = 'StudentBuscar?filter=' + searchValue.value + '&documentType=' + searchCriteria.value + '&active=' + soloActivos;

	// Llamada AJAX
	let xhttp = new XMLHttpRequest();
	xhttp.open('GET', url, true);
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			let respuesta = xhttp.responseText;
			listaEstudiantes = JSON.parse(respuesta); // Actualizar la lista de estudiantes

			let detalleTabla = '';
			listaEstudiantes.forEach(function(item) {
				detalleTabla += `
					<tr>
						<td>${item.student_id}</td>
						<td>${item.names}</td>
						<td>${item.lastname}</td>
						<td>${item.email}</td>
						<td class="text-center">${item.document_type}</td>
						<td>${item.document_number}</td>
						<td class="text-center">${item.semester}</td>
						<td>${item.career}</td>
						<td>
							<div class='d-flex gap-2'>
									${obtenerBtnEditar(item.student_id)}
									${obtenerTipoBtnActivo(item.active, item.student_id)}
								</div>
							</td>
						</tr>
					`;
			});

			document.getElementById("detalleTabla").innerHTML = detalleTabla;
		}
	};
	xhttp.send();
}

function obtenerBtnEditar(student_id) {
	if (soloActivos === 'A') {
		return `
			<button type='button' class='btn btn-light' onclick='fnEditar(${student_id})'>
				<i class='bx bxs-edit'></i>
			</button>		
		`;
	}

	if (soloActivos === 'I') {
		return '';
	}
}

function obtenerTipoBtnActivo(estado, student_id) {
	if (estado === 'A') {
		return `<button type='button' class='btn btn-light' onclick='fnEliminar(${student_id})'>
						<i class='bx bxs-trash' style='color: #dc3545;'></i>
					</button>`;
	} else {
		return `<button type='button' class='btn btn-light' onclick='fnActivar(${student_id})'>
					<i class='bx bxs-share' style='color: #20c997;'></i>

				</button>`;
	}
}


function abrirModalConfirmar(student_id) {
	personaIdSeleccionada = student_id;
	let confirmacionModal = document.querySelector('#confirmacionModal');
	let modal = bootstrap.Modal.getOrCreateInstance(confirmacionModal);
	modal.show();
}

// Funcion para eliminar estudiante
function fnEliminar(student_id) {
	console.log('student_id: ', student_id);

	let datos = 'student_id=' + student_id;

	let xhr = new XMLHttpRequest();
	xhr.open('POST', 'StudentEliminar', true);
	xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			console.log(xhr.responseText);
			fnBuscarEstudiante();

			// Mostrar la alerta flotante al eliminar con éxito
			showFloatingAlert('El estudiante ha sido eliminado correctamente');
		}
	};
	xhr.send(datos);
}
// Funcion para Activar estudiante
function fnActivar(student_id) {
	console.log('student_id: ', student_id);

	let datos = 'student_id=' + student_id;

	let xhr = new XMLHttpRequest();
	xhr.open('POST', 'StudentActivar', true);
	xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			// La solicitud se completó correctamente
			console.log(xhr.responseText);
			actualizarEstudianteActivo(student_id);

			// Mostrar la alerta flotante al eliminar con éxito
			showFloatingAlert('Estudiante activado correctamente');
		}
	};
	xhr.send(datos);
}

// Funcion para actualizar estudiante activo
function actualizarEstudianteActivo(student_id) {

	let detalleTablaElement = document.getElementById('detalleTabla');
	let estudiantesActivos = detalleTablaElement.getElementsByTagName('tr');

	for (let i = 0; i < estudiantesActivos.length; i++) {
		let estudiante = estudiantesActivos[i];
		let idEstudiante = estudiante.getElementsByTagName('td')[0].innerText;
		if (idEstudiante == student_id) {
			estudiante.parentNode.removeChild(estudiante);
			break;
		}
	}
}

// Funcion para editar o actualizar Estudiante
function fnEditar(student_id) {
	console.log('student_id: ', student_id);
	document.getElementById("accion").value = ACCION_EDITAR;
	document.getElementById('estudianteModalLabel').innerHTML = 'Editar estudiante';

	let estudianteModal = document.querySelector('#estudianteModal');
	let modal = bootstrap.Modal.getOrCreateInstance(estudianteModal);
	modal.show();

	fnCargarForm(student_id);

	estudianteModal.addEventListener("hidden.bs.modal", fnLimpiarForm);
}

// Funcion para cargar los datos del estudiante en el formulario de edicion
function fnCargarForm(student_id) {
	// Buscar el estudiante en la lista de estudiantes
	let estudiante = listaEstudiantes.find(function(item) {
		return item.student_id === student_id;
	});

	if (estudiante) {
		// Asignar los valores del estudiante al formulario de edicion
		document.getElementById("frmStudent_id").value = estudiante.student_id;
		document.getElementById("frmNames").value = estudiante.names;
		document.getElementById("frmLastname").value = estudiante.lastname;
		document.getElementById("frmEmail").value = estudiante.email;
		document.getElementById("frmDocument_type").value = estudiante.document_type;
		document.getElementById("frmDocument_number").value = estudiante.document_number;

		// Obtener el campo de selección de semestre
		let frmSemesterSelect = document.getElementById("frmSemester");
		// Recorrer las opciones del campo de selección
		for (let i = 0; i < frmSemesterSelect.options.length; i++) {
			let option = frmSemesterSelect.options[i];
			// Comparar el valor de la opción con el semestre del estudiante
			if (option.value === estudiante.semester) {
				// Establecer el atributo 'selected' en 'true' para seleccionar la opción correspondiente
				option.selected = true;
				break;
			}
		}

		document.getElementById("frmCareer").value = estudiante.career;
	} else {
		console.log("Estudiante no encontrado");
	}
}
// Funcion para limpiar el formulario
function fnLimpiarForm() {
	document.getElementById("frmStudent_id").value = "";
	document.getElementById("frmNames").value = "";
	document.getElementById("frmLastname").value = "";
	document.getElementById("frmEmail").value = "";
	document.getElementById("frmDocument_number").value = "";
}

// Obtener referencia al formulario
  var formEstudiantes = document.getElementById('formEstudiantes');
  // Función para limpiar las validaciones del formulario
  function limpiarValidacionesFormulario() {
    // Resetear el formulario
    formEstudiantes.reset();
    // Obtener todos los elementos del formulario con clases de validación
    var elementosValidados = formEstudiantes.querySelectorAll('.is-invalid, .is-valid');
    // Eliminar las clases de validación de los elementos
    elementosValidados.forEach(function(elemento) {
      elemento.classList.remove('is-invalid', 'is-valid');
    });
    // Limpiar los mensajes de error
    var mensajesError = formEstudiantes.querySelectorAll('.error-message');
    mensajesError.forEach(function(mensaje) {
      mensaje.textContent = '';
    });
  }
  // Asociar la función de limpiar validaciones al evento 'cancelar' del modal
  var modalCancelarBtn = document.getElementById('btnCancelar');
  modalCancelarBtn.addEventListener('click', limpiarValidacionesFormulario);
  // Asociar la función de limpiar validaciones al evento 'guardar' del modal
  var modalGuardarBtn = document.getElementById('btnGuardar');
  modalGuardarBtn.addEventListener('click', limpiarValidacionesFormulario);