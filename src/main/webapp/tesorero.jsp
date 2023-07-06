<!doctype html>
<html lang="en">
<head>
<%@include file="component/allcss.jsp"%>
<link rel="stylesheet" href="css/student.css">
</head>
<body onload="fnBuscarEstudiante()">
	<div id="alertContainer"></div>
	<jsp:include page="component/header.jsp"></jsp:include>

	<jsp:include page="component/navbar.jsp"></jsp:include>


	<div class="container">

		<!-- Card de datos de entrada -->
		<div class="my-3 p-3 bg-body rounded shadow-sm">
			<h5 class="border-bottom pb-2 mb-0">Estudiante</h5>
			<div class="row mt-3">

				<div class="col-md-5">
					<div class="input-group mb-3">
						<span class="input-group-text" id="btnBuscar"><i
							class='bx bx-search-alt-2'></i></span> <input type="search"
							class="form-control" placeholder="Buscar..." id="inputBuscar">
					</div>
				</div>
				<div class="col">
					<select class="form-select" id="searchField" name="searchField">
						<option value="">Tipo Documento</option>
						<option value="DNI">DNI</option>
					</select>
				</div>

				<div class="col-md-2">
					<div class="form-check form-check-inline mt-2 mb-2">
						<input class="form-check-input" type="checkbox" id="chkInactivos"
							checked> <label class="form-check-label"
							for="inlineCheckbox1">Activos</label>
					</div>
				</div>

				<div class="col">
					<div class="dropdown">
						<button class="btn btn-secondary dropdown-toggle" type="button"
							id="dropdownMenuButton1" data-bs-toggle="dropdown"
							aria-expanded="false">Exportar</button>
						<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
							<button class="dropdown-item btn btn-success" type="button"
								id="descargarEXCEL">Excel</button>
							<button class="dropdown-item btn btn-danger" type="button"
								id="descargarPDF">Pdf</button>
							<button class="dropdown-item btn btn-secondary" type="button"
								id="descargarCSV">Csv</button>
						</div>
					</div>
				</div>
				<div class="col">
					<button type="button" class="btn btn-primary mb-3" id="btnNuevo"
						name="btnNuevo">Agregar</button>
				</div>

			</div>
		</div>
		<!-- Card de resultado ,listado Estudiante -->
		<div class="my-3 p-3 bg-body rounded shadow-sm">
			<table class="table table-sm table-hover" id="tablaStudent">
				<thead>
					<tr>
						<th scope="col">N°</th>
						<th scope="col">Nombres</th>
						<th scope="col">Apellidos</th>
						<th scope="col">Correo</th>
						<th scope="col" class="text-center">Tipo Documento</th>
						<th scope="col">N° Documento</th>
						<th scope="col">Semestre</th>
						<th scope="col">Carrera</th>
						<th scope="col">Acciones</th>
					</tr>
				</thead>
				<tbody id="detalleTabla">
				</tbody>
			</table>
		</div>

		<!-- Formulario para crear Estudiante -->
		<div class="modal fade" id="estudianteModal" tabindex="-1"
			aria-labelledby="estudianteModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="estudianteModalLabel">Crear nuevo
							estudiante</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<form class="row g-2" id="formEstudiantes">
							<input type="hidden" id="accion" name="accion"> <input
								type="hidden" class="form-control" id="frmStudent_id">
							<div class="col-md-12 input-group-sm">
								<label for="frmNames" class="form-label">Nombres*</label> <input
									type="text" class="form-control" id="frmNames" required>
								<div class="error-message" id="errorNames"></div>
								<div class="invalid-feedback" id="inputNombresError"></div>
							</div>

							<div class="col-md-12 input-group-sm">
								<label for="frmLastname" class="form-label">Apellidos*</label> <input
									type="text" class="form-control" id="frmLastname" required>
								<div class="error-message" id="errorLastnames"></div>
								<div class="invalid-feedback" id="inputApellidosError"></div>
							</div>
							<div class="col-md-12 input-group-sm">
								<label for="frmEmail" class="form-label">Correo*</label> <input
									type="text" class="form-control" id="frmEmail" required>
								<div class="error-message" id="errorEmail"></div>
								<div class="invalid-feedback" id="inputCorreoError"></div>
							</div>
							<div class="col-md-12 input-group-sm">
								<label for="frmPasswords" class="form-label">Contraseña*</label> <input
									type="text" class="form-control" id="frmPasswords" required>
								<div class="error-message" id="errorPasswords"></div>
								<div class="invalid-feedback" id="inputContraseñaError"></div>
							</div>
							<div class="col-md-4 input-group-sm">
								<label for="frmDocument_type" class="form-label">Tipo
									documento</label> <select class="form-select" aria-label="Seleccionar"
									id="frmDocument_type">
									<option value="DNI">DNI</option>
								</select>
							</div>
							<div class="col-md-8 input-group-sm">
								<label for="frmDocument_number" class="form-label">N°
									Documento*</label> <input type="text" class="form-control"
									id="frmDocument_number" required>
								<div id="errorDocument_number" class="error-message"></div>
								<div id="inputNumeroDocumentoError" class="invalid-feedback"></div>
							</div>
							
						</form>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-bs-dismiss="modal" id="btnCancelar">Cancelar</button>
							<button type="submit" class="btn btn-primary" id="btnGuardar">Guardar</button>
						</div>
					</div>
				</div>
			</div>
		</div>

				<!-- 	Script para gestionar las funciones de esta vista -->
				<script type="text/javascript" src="js/student1.js"></script>
				<script type="text/javascript" src="js/studentExportaciones.js"></script>
				<script type="text/javascript" src="js/studentValidation.js"></script>

				<%@include file="component/allscript.jsp"%>
</body>
</html>