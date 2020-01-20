package com.dev.springcar.controllers;

import com.dev.springcar.entities.Brand;
import com.dev.springcar.entities.User;
import com.dev.springcar.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("marcas")
public class BrandController {

    @Autowired
    private BrandService service;

    @GetMapping
    public String marcas(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("usuario");

        if (user != null) {
            model.addAttribute("title", "Marcas Disponibles");
            List<Brand> brandList = service.listBrands();
            model.addAttribute("marcas", brandList);
            model.addAttribute("usuario", user);
            return "marcas";
        }
        model.addAttribute("title", "Ingrese con su cuenta");
        return "login";
    }

    @GetMapping("nueva")
    public String nueva(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("usuario");
        if (user != null) {
            model.addAttribute("title", "Registrar Marca");
            model.addAttribute("usuario", user);
            return "nuevaMarca";
        }
        model.addAttribute("title", "Ingrese con su cuenta");
        return "login";
    }

    @GetMapping("eliminar")
    public String eliminar(Model model, HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        String message;
        if (service.deleteBrand(id)) {
            message = "Marca eliminada";
        }else {
            message = "No se pudo realizar la operacion, intente de nuevo";
        }

        model.addAttribute("message", message);
        return marcas(model, request);
    }

    @PostMapping("guardar")
    public String guardar(Model model, HttpServletRequest request) {
        String nombre = request.getParameter("nombre");
        Brand brand = new Brand();
        brand.setName(nombre);
        String message;

        if (service.addBrand(brand)) {
            message = "Marca guardada exitosamente";
        }else {
            message = "No se pudo realizar la operacion";
        }
        model.addAttribute("message", message);

        return marcas(model, request);
    }
}
