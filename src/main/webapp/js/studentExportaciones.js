
///////////////////////////////////// Exportar a PDF
	//Obtén una referencia al botón
	const botonDescargarPDF = document.getElementById('descargarPDF');

	//Agrega el evento click al botón
	botonDescargarPDF.addEventListener('click', () => {
	    const tablaEstudiantes = document.getElementById('tablaStudent');
	    
	    // Configura las opciones de html2pdf
	    const options = {
	      filename: 'Listado_tabla_student.pdf',
	      image: { type: 'jpeg', quality: 0.98 },
	      html2canvas: { scale: 2 },
	      jsPDF: { unit: 'pt', format: 'a3', orientation: 'landscape' },
	    };
	    
	    // Crea el documento PDF con html2pdf
	    html2pdf().set(options).from(tablaEstudiantes).save();
	  });
////////////////////////////////////// Exportar a Excel
function exportToExcel(data, filename, sheetname) {
	var workbook = XLSX.utils.book_new();
	var worksheet = XLSX.utils.json_to_sheet(data);
	XLSX.utils.book_append_sheet(workbook, worksheet, sheetname);
	var excelData = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
	var blob = new Blob([excelData], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
	var link = document.createElement('a');
	link.href = URL.createObjectURL(blob);
	link.download = filename;
	link.click();
	URL.revokeObjectURL(link.href);
}

// Conectar al boton 
var descargarExcelBtn = document.getElementById('descargarEXCEL');
descargarExcelBtn.addEventListener('click', function() {

	// Llamamos a la tabla y colocamos los datos
	var table = document.getElementById('tablaStudent');
	var data = Array.from(table.rows).slice(1).map(function(row) {
		return {
			'N°': row.cells[0].textContent,
			'Nombres': row.cells[1].textContent,
			'Apellidos': row.cells[2].textContent,
			'Tipo Documento': row.cells[3].textContent,
			'N° Documento': row.cells[4].textContent,
			'Estado': row.cells[5].textContent
		};
	});

	var filename = 'Student.xlsx';
	var sheetname = 'Hoja 1';
	exportToExcel(data, filename, sheetname);
});

///////////////////////////////////// Exportar a CSV
function exportarCSV() {
	const tabla = document.getElementById("tablaStudent");
	const filas = tabla.getElementsByTagName("tr");

	let csv = "";

	for (let i = 1; i < filas.length; i++) {
		const celdas = filas[i].getElementsByTagName("td");
		const filaDatos = Array.from(celdas).map(celda => celda.textContent);
		//Eliminado ultimo valor (botones)
		filaDatos.pop();
		csv += filaDatos.join(",") + "\n";
	}

	const blob = new Blob([csv], { type: "text/csv;charset=utf-8;" });
	const link = document.createElement("a");
	link.href = URL.createObjectURL(blob);
	link.download = "estudiantes.csv";
	link.style.display = "none";
	document.body.appendChild(link);
	link.click();
	document.body.removeChild(link);
}

document.getElementById("descargarCSV").addEventListener("click", exportarCSV);