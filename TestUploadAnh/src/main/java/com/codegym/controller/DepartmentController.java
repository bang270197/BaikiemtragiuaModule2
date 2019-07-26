package com.codegym.controller;

import com.codegym.model.Department;
import com.codegym.service.DepartmentService;
import com.sun.tracing.dtrace.ProviderAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;

@Controller
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;




    @GetMapping("/index-department")
    public String showDepartment(Model model)
    {
        Iterable<Department> departments=departmentService.findAll();
        model.addAttribute("department",departments);
        return "department/list";
    }

    @GetMapping("/show-create")
    public String showCreate(Model model){
        model.addAttribute("department",new Department());
        return "department/create";
    }

    @PostMapping("/create-department")
    public ModelAndView modelAndView(@ModelAttribute Department department){
        departmentService.save(department);
        ModelAndView modelAndView=new ModelAndView("department/create");
        modelAndView.addObject("department",new Department());
        modelAndView.addObject("message","Them thanh cong");
        return modelAndView;
    }

    @GetMapping("/show-editDepartment/{id}")
    public String showEdit(@PathVariable Long id,Model model)
    {
        Department department=departmentService.findById(id);
        model.addAttribute("departments",department);
        return "department/edit";
    }

    @PostMapping("/edit-department")
    public ModelAndView editDepartment(@ModelAttribute Department department){
        ModelAndView modelAndView=new ModelAndView("department/edit");
        departmentService.save(department);
        modelAndView.addObject("departments",department);
        modelAndView.addObject("message","Sua thanh cong");
        return modelAndView;
    }

   @GetMapping("/show-deleteDepartment/{id}")
    public String showDepartment(@PathVariable Long id,Model model){
        Department departments=departmentService.findById(id);
        model.addAttribute("department",departments);
        return "department/delete";
   }

   @PostMapping("/delete-department")
    public ModelAndView deleteDepartment(@ModelAttribute Department department){
        Long id=department.getId();
        departmentService.remove(id);
        ModelAndView modelAndView=new ModelAndView("department/delete");
        modelAndView.addObject("department",department);
        modelAndView.addObject("message","Xoa thanh cong");
        return modelAndView;
   }

   @GetMapping("/view-department/{id}")
    public String viewDepartment(@PathVariable Long id,Model model){
        Department department=departmentService.findById(id);
        model.addAttribute("department",department);
        return "department/detail";
   }


}
