function apresentaDS() {
	var cidade = document.getElementById("cidade");
	var estado = document.getElementById("estado");
	var selCidade = cidade.options[cidade.selectedIndex].text;
	var selEstado = estado.options[estado.selectedIndex].text;
	console.log(selCidade + "/" + selEstado);
	alert("Selecionado: " + selCidade + "/" + selEstado);
}

$(document).ready(function () {
	var base = window.location.origin;
	$('#estado').on('change', function () {
		var estadoId = $(this).val();
		$.ajax({
			type: 'GET',
			url: base + '/cidades/estados/' + estadoId,
			headers: {
              "accept": "application/json",
              "Access-Control-Allow-Origin":"*"
			},
			success: function (result) {
				var s = '<option value="">Selecione</option>';
				for (var i = 0; i < result.length; i++) {
					s += '<option value="' + result[i].id + '">'
						+ result[i].nome
						+ '</option>';
				}
				$('#cidade').html(s);
			},
			error: function (request, status, error) {
				alert(request.responseText);
			}
		});
	})

	$.ajax({
		type: 'GET',
		url: base + '/estados',
		crossDomain: true,
		headers: {
			"accept": "application/json",
			"Access-Control-Allow-Origin":"*"
		  },
		success: function (result) {
			var s = '<option value="">Selecione</option>';
			for (var i = 0; i < result.length; i++) {
				s += '<option value="' + result[i].id + '">'
					+ result[i].sigla
					+ '</option>';
			}
			$('#estado').html(s);
		},
		error: function (request, status, error) {
			console.log('URL: ' + base + '/estados');
			alert(request.responseText);
		}
	});
});

