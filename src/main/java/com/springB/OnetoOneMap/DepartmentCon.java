package com.springB.OnetoOneMap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
public class DepartmentCon {
	@Autowired 
	DepartmentRes dr;
	
	@PostMapping 
	public Department save(@RequestBody Department d) {
		return dr.save(d);
	}

	@GetMapping("/{id}")
	public Department getId(@PathVariable int id) {
		return dr.findById(id).orElse(null);	
	}
	
	@PutMapping("/{id}")
	public Department updateById(@PathVariable int id, @RequestBody Department d) {
		Department dp = dr.findById(id).orElseThrow();
		dp.setId(d.getId());
		dp.setDname(d.getDname());
		return dr.save(dp);
	}
	
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable int id) {
		dr.deleteById(id);
	}
	
	@GetMapping("/page/{no}/{size}")
	public List<Department> pageination(@PathVariable int no, @PathVariable int size) {
		Pageable pa = PageRequest.of(no, size);
		Page p = dr.findAll(pa);
		return p.hasContent() ? p.getContent() : new ArrayList<>();
	}
	
	@GetMapping("/sort")
	public List<Department> sorting(@RequestParam String f, @RequestParam String d) {
		Sort.Direction sd = d.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
		return dr.findAll(Sort.by(sd, f));
	}
	
	@GetMapping("/page/{no}/{size}/sort")
	public List<Department> pageination(@PathVariable int no, @PathVariable int size, @RequestParam String f, @RequestParam String d) {
		Sort s = d.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(f).ascending() : Sort.by(f).descending();
		Pageable pa = PageRequest.of(no, size, s);
		Page p = dr.findAll(pa);
		return p.hasContent() ? p.getContent() : new ArrayList<>();
	}
}
