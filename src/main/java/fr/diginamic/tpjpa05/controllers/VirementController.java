package fr.diginamic.tpjpa05.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.tpjpa05.entities.Compte;
import fr.diginamic.tpjpa05.entities.Virement;
import fr.diginamic.tpjpa05.exceptions.OperationNotFoundException;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/virement")
public class VirementController extends OperationAbstractController<Virement> {

    // Create
    @Override
    public Virement addOperation(@Valid @RequestBody Virement virement, BindingResult result) throws OperationNotFoundException {
    	if(result.hasErrors()) {
    		String s = result.toString();
			throw new OperationNotFoundException(s);
		}
    	return crudOperation.save(virement);
    }
    
    // Read ALL
    @Override
    public Iterable<Virement> getOperations() {
        return crudOperation.getAllVirement();
    }
    
    // Read SINGLE
    @Override
    public Virement getOperation(@PathVariable Long id) throws OperationNotFoundException {
        if (crudOperation.findById(id).isEmpty()) {
            throw new OperationNotFoundException("L'virement avec l'id " + id + " n'existe pas");
        }
        return (Virement) crudOperation.findById(id).get();
    }
    
    // Read COMPTE
   	@Override
	public Compte getCompte(@PathVariable Long id) throws OperationNotFoundException {
   		if (crudOperation.findById(id).isEmpty()) {
            throw new OperationNotFoundException("Le virement avec l'id " + id + " n'existe pas");
        }
   		Compte compte = crudOperation.findById(id).get().getCompte();
        return compte;
	}
    
    // Update
    @Override
    public Virement updateOperation(@PathVariable Long id, @Valid @RequestBody Virement virement, BindingResult result) throws OperationNotFoundException {
    	if(result.hasErrors()) {
    		String s = result.toString();
			throw new OperationNotFoundException(s);
		}
    	if (crudOperation.findById(id).isEmpty()) {
            throw new OperationNotFoundException("L'virement avec l'id " + id + " n'existe pas");
        }
        if (id != virement.getId()) {
            throw new OperationNotFoundException("La variable d'URL id = " + id + " est différente de l'id de l'virement JSON (id = " + virement.getId() + ")");
        }
        return crudOperation.save(virement);
    }
    
    // Delete
    @Override
    public ResponseEntity<String> deleteOperation(@PathVariable Long id) throws OperationNotFoundException {
        if (crudOperation.findById(id).isEmpty()) {
            throw new OperationNotFoundException("L'virement avec l'id " + id + " n'existe pas");
        }
        crudOperation.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Virement supprimée");
    }


    
}