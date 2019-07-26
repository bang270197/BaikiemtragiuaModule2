package com.codegym.controller;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import com.codegym.model.EmployeeForm;
import com.codegym.service.DepartmentService;
import com.codegym.service.EmployeeService;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

@Controller
public class EmployeeController {
    @Autowired
    Environment env;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;

    @ModelAttribute("departments")
    public Iterable<Department> departments(){
        return departmentService.findAll();
    }

    //search
    @GetMapping("/search-department")
    public ModelAndView serachDepartment(@RequestParam String name){
        Department departments=departmentService.findByName(name);
       Iterable<Employee> employees=employeeService.findAllByDepartment(departments);
        ModelAndView modelAndView=new ModelAndView("employee/search");
        modelAndView.addObject("departmentSearch",departments);
        modelAndView.addObject("employeeSearch",employees);
        return modelAndView;
    }

    @GetMapping("/index")
    public ModelAndView showList(@PageableDefault(4) Pageable pageable){
        Page<Employee> employees=employeeService.findAll(pageable);
        ModelAndView modelAndView=new ModelAndView("employee/list");
        modelAndView.addObject("employeess",employees);
        return modelAndView;
    }






//    //Phan trang
//    @GetMapping("/page-employee")
//    public ModelAndView pageEmployee( Pageable pageable){
//        pageable.hasPrevious();
//        pageable.first();
//        Page<Employee> employees=employeeService.findAll(pageable);
//        ModelAndView modelAndView=new ModelAndView("employee/list");
//        modelAndView.addObject("employ",employees);
//        return modelAndView;
//    }





    @GetMapping("/show-createEmployee")
    public String showCreate(Model model){
        model.addAttribute("employee",new EmployeeForm());
        return "employee/create";
    }


    @PostMapping("/create-employee")
    public ModelAndView createEmployee(@ModelAttribute EmployeeForm employeeForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            System.out.printf("Result Error Occured"+bindingResult.getAllErrors());
        }
        MultipartFile multipartFile=employeeForm.getImages();
        String fileName=multipartFile.getOriginalFilename();
        String fileUpload=env.getProperty("file_upload").toString();


        try {
            //multipartFile.transferTo(imageFile);
            FileCopyUtils.copy(employeeForm.getImages().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Employee employee=new Employee(employeeForm.getId(),employeeForm.getName(),employeeForm.getBirthDate(),employeeForm.getAddress(),employeeForm.getSalary(),fileName,employeeForm.getDepartment());
        employeeService.save(employee);
        ModelAndView modelAndView=new ModelAndView("employee/create");
        modelAndView.addObject("employee",new EmployeeForm());
        modelAndView.addObject("message","Them thanh cong");
        return modelAndView;

    }

    @GetMapping("/show-edit/{id}")
    public ModelAndView showEdit(@PathVariable Long id)
    {
        Employee employee=employeeService.findById(id);
        EmployeeForm employeeForm=new EmployeeForm(employee.getId(),employee.getName(),employee.getBirthDate(),employee.getAddress(),null,employee.getSalary(),employee.getDepartment());
        ModelAndView modelAndView=new ModelAndView("employee/edit");
        modelAndView.addObject("employee",employee);
        modelAndView.addObject("employeeForm",employeeForm);
        return modelAndView;

    }
    @PostMapping("/edit-employee")
    public ModelAndView editEmployee(@ModelAttribute EmployeeForm employeeForm,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            System.out.println("Result Error Occured" + bindingResult.getAllErrors());

        }

        MultipartFile multipartFile=employeeForm.getImages();
        String fileName=multipartFile.getOriginalFilename();

        try {
            FileCopyUtils.copy(employeeForm.getImages().getBytes(), new File(env.getProperty("file_upload") + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Employee employeeOld = employeeService.findById(employeeForm.getId());
        if (fileName== ""){
            fileName = employeeOld.getAvatar();
        }

        Employee employee=new Employee(employeeForm.getId(),employeeForm.getName(),employeeForm.getBirthDate(),employeeForm.getAddress(),employeeForm.getSalary(),fileName,employeeForm.getDepartment());
        employeeService.save(employee);

        ModelAndView modelAndView=new ModelAndView("employee/edit");
        modelAndView.addObject("employee",employee);
        modelAndView.addObject("message","Sua thanh cong");
        return modelAndView;
    }

    @GetMapping("/show-delete/{id}")
    public ModelAndView showDelete(@PathVariable Long id){
        ModelAndView modelAndView=new ModelAndView("employee/delete");
        Employee employee=employeeService.findById(id);
        EmployeeForm employeeForm=new EmployeeForm(employee.getId(),employee.getName(),employee.getBirthDate(),employee.getAddress(),null,employee.getSalary(),employee.getDepartment());
        modelAndView.addObject("employee",employee);
        modelAndView.addObject("employeeForm",employeeForm);
        return modelAndView;
    }

    @PostMapping("/delete-employee")
    public ModelAndView deleteEmployee(@ModelAttribute EmployeeForm employeeForm){
        Long id=employeeForm.getId();
        employeeService.remove(id);
        ModelAndView modelAndView=new ModelAndView("employee/delete");
        modelAndView.addObject("message","Xoa thanh cong");
        return  modelAndView;
    }

    @GetMapping("/show-view/{id}")
    public ModelAndView showDetail(@PathVariable Long id){
        Employee employee=employeeService.findById(id);
        EmployeeForm employeeForm=new EmployeeForm(employee.getId(),employee.getName(),employee.getBirthDate(),employee.getAddress(),null,employee.getSalary(),employee.getDepartment());
        ModelAndView modelAndView=new ModelAndView("employee/detail");
        modelAndView.addObject("emoloyee",employee);
        modelAndView.addObject("employeeForm",employeeForm);
        return modelAndView;
    }
}
