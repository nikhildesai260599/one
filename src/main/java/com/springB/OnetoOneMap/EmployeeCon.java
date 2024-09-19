package com.springB.OnetoOneMap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class EmployeeCon {
	@Autowired 
	EmployeeRes er;
	
	@PostMapping 
	public Employee create(@RequestBody Employee e) {
		return er.save(e);
	}
	
	@GetMapping("/{id}")
	public Employee getId(@PathVariable int id) {
		return er.findById(id).orElse(null);	
	}
	
	@PutMapping("/{id}")
	public Employee update(@PathVariable int id, @RequestBody Employee e) {
		Employee em = er.findById(id).orElseThrow();
		em.setId(e.getId());
		em.setName(e.getName());
		return er.save(em);
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable int id) {
		if(er.existsById(id)) {
			er.deleteById(id);
			return "deleted Successfully";
		}else {
			return "not found";
		}
	}
	
	@GetMapping("/page/{no}/{size}")
	public List<Employee> pageination(@PathVariable int no, @PathVariable int size) {
		Pageable pa = PageRequest.of(no, size);
		Page p = er.findAll(pa);
		return p.hasContent() ? p.getContent() : new ArrayList<>();
	}
	
	@GetMapping("/sort")
	public List<Employee> sorting(@RequestParam String f, @RequestParam String d) {
		Sort.Direction sd = d.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
		return er.findAll(Sort.by(sd, f));
	}
	
	@GetMapping("/page/{no}/{size}/sort")
	public List<Employee> pageination(@PathVariable int no, @PathVariable int size, @RequestParam String f, @RequestParam String d) {
		Sort s = d.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(f).ascending() : Sort.by(f).descending();
		Pageable pa = PageRequest.of(no, size, s);
		Page p = er.findAll(pa);
		return p.hasContent() ? p.getContent() : new ArrayList<>();
	}
}
