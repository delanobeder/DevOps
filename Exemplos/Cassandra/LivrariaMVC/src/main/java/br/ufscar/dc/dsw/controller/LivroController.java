package br.ufscar.dc.dsw.controller;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.f4b6a3.uuid.UuidCreator;

import br.ufscar.dc.dsw.domain.Livro;
import br.ufscar.dc.dsw.service.spec.ILivroService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/livro")
public class LivroController {

	@Autowired
	private ILivroService livroService;

	@GetMapping("/cadastrar")
	public String cadastrar(Livro livro) {
		return "livro/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("livros", livroService.buscarTodos());
		return "livro/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Livro livro, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "livro/cadastro";
		}
		livro.setId(UuidCreator.getTimeOrdered());
		livroService.salvar(livro);
		attr.addFlashAttribute("sucess", "Livro inserido com sucesso");
		return "redirect:/livro/listar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") UUID id, ModelMap model) {
		model.addAttribute("livro", livroService.buscarPorId(id));
		return "livro/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Livro livro, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "livro/cadastro";
		}

		livroService.salvar(livro);
		attr.addFlashAttribute("sucess", "Livro editado com sucesso.");
		return "redirect:/livro/listar";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") UUID id, RedirectAttributes attr) {
		livroService.excluir(id);
		attr.addFlashAttribute("sucess", "Livro excluído com sucesso.");
		return "redirect:/livro/listar";
	}

	@ModelAttribute("editoras")
	public List<String> listaEditoras() {
		return Arrays.asList(new String[] {"Companhia das Letras", "Record", "Objetiva"});
	}
}
