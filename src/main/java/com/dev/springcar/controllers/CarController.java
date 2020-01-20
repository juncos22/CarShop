package com.dev.springcar.controllers;

import com.dev.springcar.entities.Brand;
import com.dev.springcar.entities.Car;
import com.dev.springcar.entities.User;
import com.dev.springcar.services.BrandService;
import com.dev.springcar.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("vehiculos")
public class CarController {
    @Autowired
    private CarService service;
    @Autowired
    private BrandService brandService;

    @GetMapping
    public String vehiculos(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("usuario");
        if (user != null) {
            model.addAttribute("usuario", user);
        }
        model.addAttribute("title", "Vehiculos Disponibles");
        List<Car> carList = service.listCars();
        model.addAttribute("vehiculos", carList);

        return "vehiculos";
    }

    @GetMapping("nuevo")
    public String nuevo(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("usuario");
        if (user != null) {
            model.addAttribute("usuario", user);
        }else {
            model.addAttribute("title", "Ingrese con su cuenta");
            return "login";
        }

        model.addAttribute("title", "Registrar un Vehiculo");
        List<Brand> brandList = brandService.listBrands();
        model.addAttribute("marcas", brandList);

        return "registrarVehiculo";
    }

    @PostMapping("guardar")
    public String guardar(Model model, HttpServletRequest request) {
        String modelo, foto;
        int stock, marcaId;
        double precio;
        Brand marca;

        marcaId = Integer.parseInt(request.getParameter("marca"));
        marca = brandService.getBrand(marcaId);
        if (marca != null) {
            modelo = request.getParameter("modelo");
            stock = Integer.parseInt(request.getParameter("stock"));
            precio = Double.parseDouble(request.getParameter("precio"));
            foto = request.getParameter("foto");

            Car nuevo = new Car();
            nuevo.setBrand(marca);
            nuevo.setModel(modelo);
            nuevo.setPrice(precio);
            nuevo.setStock(stock);
            nuevo.setPhoto(foto);
            String message;

            if (service.addCar(nuevo)) {
                message = "Vehiculo guardado exitosamente";
            }else {
                message = "No se pudo realizar la operacion";
            }

            model.addAttribute("message", message);
        }
        return vehiculos(model, request);
    }

    @GetMapping("detalles")
    public String detalles(Model model, HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Car car = service.findCarById(id);
        User user = (User) request.getSession().getAttribute("usuario");
        if (car != null && user != null) {
            model.addAttribute("vehiculo", car);
            List<Brand> brandList = brandService.listBrands();
            model.addAttribute("marcas", brandList);
            model.addAttribute("usuario", user);
        }
        model.addAttribute("title", "Detalles del vehiculo");
        return "detalles";
    }

    @PostMapping("actualizar")
    public String actualizar(Model model, HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        int marcaId = Integer.parseInt(request.getParameter("marca"));
        Brand marca = brandService.getBrand(marcaId);
        if (marca != null) {
            String modelo = request.getParameter("modelo");
            double precio = Double.parseDouble(request.getParameter("precio"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            String foto = request.getParameter("foto");

            Car car = service.findCarById(id);
            car.setBrand(marca);
            car.setModel(modelo);
            car.setPrice(precio);
            car.setStock(stock);
            car.setPhoto(foto);

            if (service.updateCar(car)) {
                model.addAttribute("message", "Cambios guardados exitosamente");
            }else {
                model.addAttribute("message", "No se pudo realizar la operacion");
            }
        }
        return vehiculos(model, request);
    }

    @GetMapping("eliminar")
    public String eliminar(Model model, HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        if (service.deleteCar(id)) {
            model.addAttribute("message", "Vehiculo eliminado");
        }else {
            model.addAttribute("message", "No se pudo realizar la operacion");
        }
        return vehiculos(model, request);
    }
}
