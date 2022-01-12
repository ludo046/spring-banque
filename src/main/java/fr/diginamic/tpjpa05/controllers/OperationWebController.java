package fr.diginamic.tpjpa05.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.diginamic.tpjpa05.entities.Client;
import fr.diginamic.tpjpa05.entities.Operation;
import fr.diginamic.tpjpa05.exceptions.ClientNotFoundException;
import fr.diginamic.tpjpa05.exceptions.OperationNotFoundException;
import fr.diginamic.tpjpa05.repositories.CrudBanque;
import fr.diginamic.tpjpa05.repositories.CrudClient;
import fr.diginamic.tpjpa05.repositories.CrudOperation;

@Controller
@RequestMapping("/operation")
public class OperationWebController extends OperationWebAbstractController<Operation>{

	@Autowired
	private CrudOperation crudOperation;
	
	@Autowired
	private CrudClient crudClient;
	
	public OperationWebController() {
		// TODO Auto-generated constructor stub
	}
	
	//affichage liste
	@Override
	public String getOperations(Model model) {
		model.addAttribute("operations", (List<Operation>) crudOperation.findAll());
		model.addAttribute("titre","Liste des operations");
		model.addAttribute("crudClient", crudClient);
		return "operations/Liste";
	}
	
	//affichage du form
	@Override
	public String addOperation(Model model) {
		model.addAttribute("operationForm", new Operation() );
		model.addAttribute("crudClient", crudClient);
		model.addAttribute("titre","Ajout operation");
		return "operations/add";
	}
	
	//affichage du form update
	@Override
	public String updateOperation(Model model, @PathVariable("id") Long id) {
		model.addAttribute("operationForm", crudOperation.findById(id) );
		model.addAttribute("titre","Modification operation");
		model.addAttribute("crudClient", crudClient);
		return "operations/update";
	}
	
	//update operation
	@Override
	public String update(Model model, @Valid @ModelAttribute("operationForm") Operation operationForm, BindingResult result) throws OperationNotFoundException{
		if(result.hasErrors()) {
			String s = result.toString();
			throw new OperationNotFoundException(s);
		}
		crudOperation.save(operationForm);
		return "redirect:/operation/operations";
	}

	//ajout operation
	@Override
	public String add(Model model, @Valid @ModelAttribute("operationForm") Operation operationForm, BindingResult result) throws OperationNotFoundException{
		if(result.hasErrors()) {
			String s = result.toString();
			throw new OperationNotFoundException(s);
		}
		crudOperation.save(operationForm);
		return "redirect:/operation/operations";
	}
	
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) throws OperationNotFoundException {
		Optional<Operation> operation = crudOperation.findById(id);
		if(operation.isEmpty()) {
			throw new OperationNotFoundException("Operation id :"+id+" non trouvé !");
		}
		crudOperation.deleteById(id);
		return "redirect:/operation/operations";
	}
}
